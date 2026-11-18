package de.commitmentissues.photoalbum.application;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.PhotoId;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

class StartupCleanupUseCaseTest {

    private static final Instant NOW = Instant.parse("2026-11-14T12:00:00Z");
    private static final Duration RETENTION = Duration.ofHours(30 * 24);
    private static final Instant DELETED = NOW.minus(Duration.ofDays(1));
    private static final byte[] BYTES = {1};

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final InMemoryTrashRepository trash = new InMemoryTrashRepository();
    private final InMemoryPhotoStorage photos = new InMemoryPhotoStorage();
    private final StartupCleanupUseCase useCase = new StartupCleanupUseCase(albums, trash, photos);

    @Test
    @DisplayName("A-11: Liegt ein Foto im Album und im Papierkorb, bleibt es im Album und der leere Eintrag entfällt")
    void removesDuplicateFromTrash() {
        Album urlaub = savedAlbum("Urlaub", "hash-1");
        PhotoId photo = urlaub.getPhotos().get(0).getId();
        trashCopyOfPhoto(urlaub, DELETED);

        useCase.execute(NOW);

        assertEquals(List.of(), trash.entries());
        assertEquals(List.of(photo), photoIdsOf(urlaub));
        assertEquals(Set.of(photo), photos.storedIds());
    }

    @Test
    @DisplayName("A-11: Aus einer Kachel kommt nur das doppelte Foto heraus, das andere bleibt im Papierkorb")
    void removesOnlyDuplicateFromTile() {
        Album urlaub = savedAlbum("Urlaub", "hash-1");
        Photo duplicate = Photo.restore(urlaub.getPhotos().get(0).getId(), "hash-1");
        Photo other = new Photo("hash-2");
        Trash content = new Trash(List.of());
        content.moveAlbum(Album.restore(AlbumId.newId(), "Alt", List.of(duplicate, other)), DELETED);
        trash.save(content);
        photos.put(other.getId(), BYTES);

        useCase.execute(NOW);

        assertEquals(List.of(other.getId()), trash.entries().get(0).getPhotos().stream().map(Photo::getId).toList());
    }

    @Test
    @DisplayName("ADR-002: Ist ein doppeltes Foto zugleich fällig, wird seine Kopie trotzdem nicht gelöscht")
    void keepsCopyOfDuplicateThatIsDue() {
        Album urlaub = savedAlbum("Urlaub", "hash-1");
        PhotoId photo = urlaub.getPhotos().get(0).getId();
        trashCopyOfPhoto(urlaub, NOW.minus(RETENTION));

        useCase.execute(NOW);

        assertEquals(List.of(), trash.entries());
        assertEquals(Set.of(photo), photos.storedIds());
    }

    @Test
    @DisplayName("AK-SR01.3: Beim Start werden fällige Einträge samt Kopien gelöscht, der noch nicht fällige bleibt")
    void removesDueEntries() {
        trashPhoto("hash-1", NOW.minus(RETENTION));
        trashTile(NOW.minus(RETENTION).minusSeconds(60), "hash-2", "hash-3");
        TrashEntry notDue = trashPhoto("hash-4", NOW.minus(RETENTION).plusSeconds(1));

        useCase.execute(NOW);

        assertEquals(List.of(notDue.getId()), trash.entries().stream().map(TrashEntry::getId).toList());
        assertEquals(Set.of(notDue.getPhotos().get(0).getId()), photos.storedIds());
    }

    @Test
    @DisplayName("AK-SR01.6: Lässt sich die Kopie eines fälligen Eintrags nicht löschen, bleibt er, die anderen werden bereinigt")
    void keepsDueEntryWhoseCopyCannotBeDeleted() {
        TrashEntry stuck = trashPhoto("hash-1", NOW.minus(RETENTION));
        trashPhoto("hash-2", NOW.minus(RETENTION));
        photos.undeletable.add(stuck.getPhotos().get(0).getId());

        useCase.execute(NOW);

        assertEquals(List.of(stuck.getId()), trash.entries().stream().map(TrashEntry::getId).toList());
    }

    @Test
    @DisplayName("A-11: Eine Kopie ohne Album- oder Papierkorbeintrag wird gelöscht, alle anderen bleiben")
    void deletesOrphanedCopy() {
        Album urlaub = savedAlbum("Urlaub", "hash-1");
        TrashEntry entry = trashPhoto("hash-2", DELETED);
        photos.put(PhotoId.newId(), BYTES);

        useCase.execute(NOW);

        assertEquals(Set.of(urlaub.getPhotos().get(0).getId(), entry.getPhotos().get(0).getId()), photos.storedIds());
    }

    @Test
    @DisplayName("AK-N02.2: Ist eine Albumdatei nicht lesbar, bleiben alle Kopien und Einträge erhalten")
    void changesNothingWhenAlbumsAreUnreadable() {
        trashPhoto("hash-1", NOW.minus(RETENTION));
        photos.put(PhotoId.newId(), BYTES);
        albums.failOnLoad = true;

        useCase.execute(NOW);

        assertEquals(1, trash.entries().size());
        assertEquals(2, photos.storedIds().size());
    }

    @Test
    @DisplayName("AK-N02.2: Ist trash.json nicht lesbar, bleiben alle Kopien erhalten")
    void changesNothingWhenTrashIsUnreadable() {
        photos.put(PhotoId.newId(), BYTES);
        trash.failOnLoad = true;

        useCase.execute(NOW);

        assertEquals(1, photos.storedIds().size());
    }

    @Test
    @DisplayName("Scheitert das Speichern des Papierkorbs, werden keine verwaisten Kopien gelöscht")
    void keepsOrphansWhenTrashCannotBeSaved() {
        photos.put(PhotoId.newId(), BYTES);
        trash.failOnSave = true;

        useCase.execute(NOW);

        assertEquals(1, photos.storedIds().size());
    }

    private Album savedAlbum(String name, String... hashes) {
        Album album = new Album(name);
        for (String hash : hashes) {
            album.addPhoto(new Photo(hash));
        }
        albums.save(album);
        album.getPhotos().forEach(photo -> photos.put(photo.getId(), BYTES));
        return album;
    }

    private void trashCopyOfPhoto(Album album, Instant deletedAt) {
        Photo photo = album.getPhotos().get(0);
        Album sameAlbum = Album.restore(album.getId(), album.getName(),
                List.of(Photo.restore(photo.getId(), photo.getContentHash())));
        Trash content = trash.load();
        content.movePhoto(sameAlbum.getPhotos().get(0), sameAlbum, deletedAt);
        trash.save(content);
    }

    private TrashEntry trashPhoto(String hash, Instant deletedAt) {
        Album album = new Album("Urlaub");
        album.addPhoto(new Photo(hash));
        Trash content = trash.load();
        TrashEntry entry = content.movePhoto(album.getPhotos().get(0), album, deletedAt);
        trash.save(content);
        photos.put(entry.getPhotos().get(0).getId(), BYTES);
        return entry;
    }

    private void trashTile(Instant deletedAt, String... hashes) {
        Album album = new Album("Party");
        for (String hash : hashes) {
            album.addPhoto(new Photo(hash));
        }
        Trash content = trash.load();
        TrashEntry entry = content.moveAlbum(album, deletedAt);
        trash.save(content);
        entry.getPhotos().forEach(photo -> photos.put(photo.getId(), BYTES));
    }

    private List<PhotoId> photoIdsOf(Album album) {
        return albums.findById(album.getId()).orElseThrow().getPhotos().stream().map(Photo::getId).toList();
    }
}
