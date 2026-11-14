package de.commitmentissues.photoalbum.application;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

class DeleteAlbumUseCaseTest {

    private static final Instant NOW = Instant.parse("2026-11-14T12:00:00Z");

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final InMemoryTrashRepository trash = new InMemoryTrashRepository();
    private final DeleteAlbumUseCase useCase =
            new DeleteAlbumUseCase(albums, trash, Clock.fixed(NOW, ZoneOffset.UTC));
    private final Album urlaub = albumWith("Urlaub", "hash-1", "hash-2", "hash-3");

    @BeforeEach
    void setUp() {
        Trash existing = new Trash(List.of());
        existing.movePhoto(urlaub.getPhotos().get(0), urlaub, NOW.minusSeconds(3600));
        trash.save(existing);
        albums.save(urlaub);
    }

    @Test
    @DisplayName("AK-04.1: Das Album wird eine Kachel mit seinen zwei Fotos, das früher entfernte Foto bleibt eigener Eintrag")
    void movesAlbumAsTile() {
        Result<AlbumSummary> result = useCase.execute(urlaub.getId().value());

        assertTrue(result.isSuccess());
        assertEquals(List.of(), albums.names());
        assertEquals(2, trash.entries().size());
        assertFalse(trash.entries().get(0).isAlbum());
        TrashEntry tile = trash.entries().get(1);
        assertTrue(tile.isAlbum());
        assertEquals("Urlaub", tile.getOriginAlbumName());
        assertEquals(2, tile.getPhotos().size());
        assertEquals(NOW, tile.getDeletedAt());
    }

    @Test
    @DisplayName("AK-04.2: Scheitert das Löschen der Albumdatei, sind Album, Fotos und Papierkorb wie vorher")
    void rollsBackTrashWhenDeletingFails() {
        albums.failOnDelete = true;

        Result<AlbumSummary> result = useCase.execute(urlaub.getId().value());

        assertEquals("Das Album konnte nicht in den Papierkorb verschoben werden.", result.getMessage());
        assertEquals(List.of("Urlaub"), albums.names());
        assertEquals(2, albums.findById(urlaub.getId()).orElseThrow().getPhotos().size());
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("NFA-01: Scheitert schon trash.json, bleiben Album und Papierkorb unverändert")
    void keepsEverythingWhenTrashSaveFails() {
        trash.failOnSave = true;

        Result<AlbumSummary> result = useCase.execute(urlaub.getId().value());

        assertEquals("Das Album konnte nicht in den Papierkorb verschoben werden.", result.getMessage());
        assertEquals(List.of("Urlaub"), albums.names());
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("A-11: Scheitert auch das Zurücknehmen, kommt die Aufräummeldung und das Album bleibt bestehen")
    void reportsCleanupWhenRollbackFails() {
        albums.failOnDelete = true;
        trash.failAfterSaves(1);

        Result<AlbumSummary> result = useCase.execute(urlaub.getId().value());

        assertEquals(AddPhotoUseCase.CLEANUP_ON_NEXT_START, result.getMessage());
        assertEquals(List.of("Urlaub"), albums.names());
        assertEquals(2, trash.entries().size());
    }

    @Test
    @DisplayName("Ein unbekanntes Album wird mit Meldung abgelehnt, der Papierkorb bleibt unverändert")
    void reportsUnknownAlbum() {
        Result<AlbumSummary> result = useCase.execute(UUID.randomUUID());

        assertEquals("Das Album wurde nicht gefunden.", result.getMessage());
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("Ist der Papierkorb nicht lesbar, wird nichts gelöscht")
    void reportsUnreadableTrash() {
        trash.failOnLoad = true;

        Result<AlbumSummary> result = useCase.execute(urlaub.getId().value());

        assertEquals("Der Papierkorb konnte nicht geladen werden.", result.getMessage());
        assertEquals(List.of("Urlaub"), albums.names());
    }

    private static Album albumWith(String name, String... hashes) {
        Album album = new Album(name);
        for (String hash : hashes) {
            album.addPhoto(new Photo(hash));
        }
        return album;
    }
}
