package de.commitmentissues.photoalbum.application;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

class DeletePermanentlyUseCaseTest {

    private static final Instant DELETED = Instant.parse("2026-11-13T12:00:00Z");

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final InMemoryTrashRepository trash = new InMemoryTrashRepository();
    private final InMemoryPhotoStorage photos = new InMemoryPhotoStorage();
    private final DeletePermanentlyUseCase useCase = new DeletePermanentlyUseCase(trash, albums, photos);

    @Test
    @DisplayName("AK-SR01.1: Eintrag und App-Kopie eines Fotos sind danach entfernt")
    void deletesPhotoEntryAndCopy() {
        TrashEntry entry = trashPhoto("hash-1");

        Result<Void> result = useCase.execute(entry.getId().value());

        assertTrue(result.isSuccess());
        assertEquals(List.of(), trash.entries());
        assertEquals(Set.of(), photos.storedIds());
    }

    @Test
    @DisplayName("AK-SR01.2: Scheitert das Löschen, wird der Fehler gemeldet und das Foto bleibt im Papierkorb")
    void keepsPhotoWhenDeletionFails() {
        TrashEntry entry = trashPhoto("hash-1");
        photos.failOnDelete = true;

        Result<Void> result = useCase.execute(entry.getId().value());

        assertEquals("Der Eintrag konnte nicht endgültig gelöscht werden und bleibt im Papierkorb.", result.getMessage());
        assertEquals(1, trash.entries().size());
        assertEquals(1, photos.storedIds().size());
    }

    @Test
    @DisplayName("AK-SR01.4: Scheitert eines von drei Fotos, sind zwei gelöscht und die Kachel behält eines")
    void reportsPartialSuccessForTile() {
        TrashEntry tile = trashAlbum("hash-1", "hash-2", "hash-3");
        Photo stuck = tile.getPhotos().get(1);
        photos.undeletable.add(stuck.getId());

        Result<Void> result = useCase.execute(tile.getId().value());

        assertEquals("2 von 3 Fotos wurden endgültig gelöscht. 1 Foto bleibt im Papierkorb.", result.getMessage());
        assertEquals(1, trash.entries().size());
        assertEquals(List.of(stuck.getId()), trash.entries().get(0).getPhotos().stream().map(Photo::getId).toList());
        assertEquals(Set.of(stuck.getId()), photos.storedIds());
    }

    @Test
    @DisplayName("SR-01: Eine Kachel verschwindet, wenn alle ihre Fotos gelöscht sind")
    void deletesWholeTile() {
        TrashEntry tile = trashAlbum("hash-1", "hash-2");

        Result<Void> result = useCase.execute(tile.getId().value());

        assertTrue(result.isSuccess());
        assertEquals(List.of(), trash.entries());
        assertEquals(Set.of(), photos.storedIds());
    }

    @Test
    @DisplayName("ADR-002: Eine Kopie, auf die noch ein Album verweist, wird nicht gelöscht")
    void keepsCopyReferencedByAlbum() {
        TrashEntry entry = trashPhoto("hash-1");
        Photo photo = entry.getPhotos().get(0);
        albums.save(Album.restore(entry.getOriginAlbumId(), "Urlaub", List.of(photo)));

        Result<Void> result = useCase.execute(entry.getId().value());

        assertTrue(result.isSuccess());
        assertEquals(List.of(), trash.entries());
        assertEquals(Set.of(photo.getId()), photos.storedIds());
    }

    @Test
    @DisplayName("ADR-002: Sind die Alben nicht lesbar, wird nichts gelöscht")
    void deletesNothingWhenAlbumsAreUnreadable() {
        TrashEntry entry = trashPhoto("hash-1");
        albums.failOnLoad = true;

        Result<Void> result = useCase.execute(entry.getId().value());

        assertEquals("Die Alben konnten nicht geladen werden.", result.getMessage());
        assertEquals(1, trash.entries().size());
        assertEquals(1, photos.storedIds().size());
    }

    @Test
    @DisplayName("Scheitert das Speichern des Papierkorbs, wird der Fehler gemeldet")
    void reportsTrashSaveFailure() {
        TrashEntry entry = trashPhoto("hash-1");
        trash.failOnSave = true;

        Result<Void> result = useCase.execute(entry.getId().value());

        assertEquals("Der Papierkorb konnte nicht gespeichert werden.", result.getMessage());
    }

    @Test
    @DisplayName("Ein unbekannter Eintrag wird mit Meldung abgelehnt")
    void reportsUnknownEntry() {
        Result<Void> result = useCase.execute(UUID.randomUUID());

        assertEquals("Der Eintrag wurde nicht gefunden.", result.getMessage());
    }

    @Test
    @DisplayName("Ist der Papierkorb nicht lesbar, wird der Fehler gemeldet")
    void reportsUnreadableTrash() {
        trash.failOnLoad = true;

        Result<Void> result = useCase.execute(UUID.randomUUID());

        assertEquals("Der Papierkorb konnte nicht geladen werden.", result.getMessage());
    }

    private TrashEntry trashPhoto(String hash) {
        Album album = albumWith("Urlaub", hash);
        Trash content = trash.load();
        TrashEntry entry = content.movePhoto(album.getPhotos().get(0), album, DELETED);
        trash.save(content);
        photos.put(entry.getPhotos().get(0).getId(), new byte[] {1});
        return entry;
    }

    private TrashEntry trashAlbum(String... hashes) {
        Trash content = trash.load();
        TrashEntry entry = content.moveAlbum(albumWith("Urlaub", hashes), DELETED);
        trash.save(content);
        entry.getPhotos().forEach(photo -> photos.put(photo.getId(), new byte[] {1}));
        return entry;
    }

    private static Album albumWith(String name, String... hashes) {
        Album album = new Album(name);
        for (String hash : hashes) {
            album.addPhoto(new Photo(hash));
        }
        return album;
    }
}
