package de.commitmentissues.photoalbum.application;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

class ListTrashUseCaseTest {

    private static final Instant NOW = Instant.parse("2026-11-14T12:00:00Z");
    private static final Duration RETENTION = Duration.ofHours(30 * 24);

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final InMemoryTrashRepository trash = new InMemoryTrashRepository();
    private final InMemoryPhotoStorage photos = new InMemoryPhotoStorage();
    private final ListTrashUseCase useCase =
            new ListTrashUseCase(trash, albums, photos, Clock.fixed(NOW, ZoneOffset.UTC));

    @Test
    @DisplayName("AK-04.8: Eine Kachel mit Name, Fotoanzahl und Resttagen und ein einzelnes Foto mit Resttagen")
    void showsTileAndSinglePhoto() {
        trashPhoto("hash-1", NOW.minus(Duration.ofDays(1)));
        trashAlbum(NOW.minus(Duration.ofDays(2)), "hash-2", "hash-3");

        List<TrashEntrySummary> entries = useCase.execute().getValue();

        assertEquals(2, entries.size());
        assertFalse(entries.get(0).album());
        assertEquals(29, entries.get(0).remainingDays());
        assertTrue(entries.get(1).album());
        assertEquals("Urlaub", entries.get(1).originAlbumName());
        assertEquals(2, entries.get(1).photoCount());
        assertEquals(28, entries.get(1).remainingDays());
    }

    @Test
    @DisplayName("Ein leerer Papierkorb zeigt keine Einträge")
    void showsEmptyTrash() {
        Result<List<TrashEntrySummary>> result = useCase.execute();

        assertEquals(List.of(), result.getValue());
    }

    @Test
    @DisplayName("AK-SR01.3: Beim Öffnen werden fällige Einträge samt Kopien gelöscht, der noch nicht fällige bleibt")
    void removesDueEntriesBeforeShowing() {
        trashPhoto("hash-1", NOW.minus(RETENTION));
        trashAlbum(NOW.minus(RETENTION).minusSeconds(60), "hash-2", "hash-3");
        TrashEntry notDue = trashPhoto("hash-4", NOW.minus(RETENTION).plusSeconds(1));

        List<TrashEntrySummary> entries = useCase.execute().getValue();

        assertEquals(List.of(notDue.getId().value()), entries.stream().map(TrashEntrySummary::id).toList());
        assertEquals(Set.of(notDue.getPhotos().get(0).getId()), photos.storedIds());
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("AK-SR01.6: Lässt sich die Kopie nicht löschen, bleibt der fällige Eintrag gesperrt im Papierkorb")
    void keepsDueEntryWhenCopyCannotBeDeleted() {
        TrashEntry due = trashPhoto("hash-1", NOW.minus(RETENTION));
        photos.failOnDelete = true;

        List<TrashEntrySummary> entries = useCase.execute().getValue();

        assertEquals(1, entries.size());
        assertTrue(entries.get(0).due());
        assertEquals(0, entries.get(0).remainingDays());
        assertEquals(Set.of(due.getPhotos().get(0).getId()), photos.storedIds());
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("ADR-002: Eine Kopie, auf die noch ein Album verweist, wird nicht gelöscht")
    void keepsCopyReferencedByAlbum() {
        Album urlaub = albumWith("Urlaub", "hash-1");
        Photo photo = urlaub.getPhotos().get(0);
        albums.save(urlaub);
        Album sameAlbum = Album.restore(urlaub.getId(), "Urlaub", List.of(Photo.restore(photo.getId(), "hash-1")));
        Trash content = new Trash(List.of());
        content.movePhoto(sameAlbum.getPhotos().get(0), sameAlbum, NOW.minus(RETENTION));
        trash.save(content);
        photos.put(photo.getId(), new byte[] {1});

        useCase.execute();

        assertEquals(Set.of(photo.getId()), photos.storedIds());
        assertEquals(List.of(), trash.entries());
    }

    @Test
    @DisplayName("ADR-002: Sind die Alben nicht lesbar, wird keine Kopie gelöscht")
    void keepsCopiesWhenAlbumsAreUnreadable() {
        TrashEntry due = trashPhoto("hash-1", NOW.minus(RETENTION));
        albums.failOnLoad = true;

        List<TrashEntrySummary> entries = useCase.execute().getValue();

        assertTrue(entries.get(0).due());
        assertEquals(Set.of(due.getPhotos().get(0).getId()), photos.storedIds());
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("Ist der Papierkorb nicht lesbar, wird der Fehler gemeldet")
    void reportsUnreadableTrash() {
        trash.failOnLoad = true;

        Result<List<TrashEntrySummary>> result = useCase.execute();

        assertEquals("Der Papierkorb konnte nicht geladen werden.", result.getMessage());
    }

    private TrashEntry trashPhoto(String hash, Instant deletedAt) {
        Album album = albumWith("Urlaub", hash);
        Trash content = trash.load();
        TrashEntry entry = content.movePhoto(album.getPhotos().get(0), album, deletedAt);
        trash.save(content);
        photos.put(entry.getPhotos().get(0).getId(), new byte[] {1});
        return entry;
    }

    private TrashEntry trashAlbum(Instant deletedAt, String... hashes) {
        Trash content = trash.load();
        TrashEntry entry = content.moveAlbum(albumWith("Urlaub", hashes), deletedAt);
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
