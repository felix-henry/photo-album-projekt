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
import de.commitmentissues.photoalbum.domain.PhotoId;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

class RemovePhotoUseCaseTest {

    private static final Instant NOW = Instant.parse("2026-11-14T12:00:00Z");

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final InMemoryTrashRepository trash = new InMemoryTrashRepository();
    private final RemovePhotoUseCase useCase =
            new RemovePhotoUseCase(albums, trash, Clock.fixed(NOW, ZoneOffset.UTC));
    private final Album urlaub = albumWith("Urlaub", "hash-1", "hash-2", "hash-3");
    private final List<Photo> photos = urlaub.getPhotos();

    @BeforeEach
    void setUp() {
        albums.save(urlaub);
    }

    @Test
    @DisplayName("AK-09.1: Das Foto wird ein Papierkorbeintrag mit Ursprungsalbum und Löschzeitpunkt, die anderen bleiben")
    void movesPhotoToTrash() {
        Result<AlbumDetails> result = execute(1);

        assertTrue(result.isSuccess());
        assertEquals(List.of(photos.get(0).getId(), photos.get(2).getId()), storedPhotoIds());
        assertEquals(1, trash.entries().size());
        TrashEntry entry = trash.entries().get(0);
        assertFalse(entry.isAlbum());
        assertEquals(photos.get(1).getId(), entry.getPhotos().get(0).getId());
        assertEquals(urlaub.getId(), entry.getOriginAlbumId());
        assertEquals("Urlaub", entry.getOriginAlbumName());
        assertEquals(NOW, entry.getDeletedAt());
    }

    @Test
    @DisplayName("AK-09.1: Ein vorhandener Eintrag mit bytegleichem Foto aus einem anderen Album bleibt getrennt")
    void keepsExistingEntrySeparate() {
        Album party = albumWith("Party", "hash-2");
        Trash existing = new Trash(List.of());
        existing.movePhoto(party.getPhotos().get(0), party, NOW.minusSeconds(3600));
        trash.save(existing);

        execute(1);

        assertEquals(2, trash.entries().size());
        assertEquals("Party", trash.entries().get(0).getOriginAlbumName());
    }

    @Test
    @DisplayName("AK-09.2: Scheitert die Albumdatei, wird der Papierkorb zurückgenommen und alle drei Fotos bleiben")
    void rollsBackTrashWhenAlbumSaveFails() {
        albums.failOnSave = true;

        Result<AlbumDetails> result = execute(1);

        assertEquals("Das Foto konnte nicht in den Papierkorb verschoben werden.", result.getMessage());
        assertEquals(ids(photos), storedPhotoIds());
        assertEquals(List.of(), trash.entries());
    }

    @Test
    @DisplayName("NFA-01: Scheitert schon trash.json, bleiben Album und Papierkorb unverändert")
    void keepsEverythingWhenTrashSaveFails() {
        trash.failOnSave = true;

        Result<AlbumDetails> result = execute(1);

        assertEquals("Das Foto konnte nicht in den Papierkorb verschoben werden.", result.getMessage());
        assertEquals(ids(photos), storedPhotoIds());
        assertEquals(List.of(), trash.entries());
    }

    @Test
    @DisplayName("A-11: Scheitert auch das Zurücknehmen, kommt die Aufräummeldung und das Foto bleibt im Album")
    void reportsCleanupWhenRollbackFails() {
        albums.failOnSave = true;
        trash.failAfterSaves(1);

        Result<AlbumDetails> result = execute(1);

        assertEquals(AddPhotoUseCase.CLEANUP_ON_NEXT_START, result.getMessage());
        assertEquals(ids(photos), storedPhotoIds());
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("Ein unbekanntes Foto wird mit Meldung abgelehnt, der Papierkorb bleibt leer")
    void reportsUnknownPhoto() {
        Result<AlbumDetails> result = useCase.execute(urlaub.getId().value(), UUID.randomUUID());

        assertEquals("Das Foto wurde nicht gefunden.", result.getMessage());
        assertEquals(List.of(), trash.entries());
    }

    @Test
    @DisplayName("Ein unbekanntes Album wird mit Meldung abgelehnt")
    void reportsUnknownAlbum() {
        Result<AlbumDetails> result = useCase.execute(UUID.randomUUID(), photos.get(1).getId().value());

        assertEquals("Das Album wurde nicht gefunden.", result.getMessage());
    }

    @Test
    @DisplayName("Ist der Papierkorb nicht lesbar, wird nichts verschoben")
    void reportsUnreadableTrash() {
        trash.failOnLoad = true;

        Result<AlbumDetails> result = execute(1);

        assertEquals("Der Papierkorb konnte nicht geladen werden.", result.getMessage());
        assertEquals(ids(photos), storedPhotoIds());
    }

    private Result<AlbumDetails> execute(int photoIndex) {
        return useCase.execute(urlaub.getId().value(), photos.get(photoIndex).getId().value());
    }

    private List<PhotoId> storedPhotoIds() {
        return ids(albums.findById(urlaub.getId()).orElseThrow().getPhotos());
    }

    private static List<PhotoId> ids(List<Photo> photos) {
        return photos.stream().map(Photo::getId).toList();
    }

    private static Album albumWith(String name, String... hashes) {
        Album album = new Album(name);
        for (String hash : hashes) {
            album.addPhoto(new Photo(hash));
        }
        return album;
    }
}
