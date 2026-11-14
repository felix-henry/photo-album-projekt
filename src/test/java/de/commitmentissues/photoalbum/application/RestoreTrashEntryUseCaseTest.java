package de.commitmentissues.photoalbum.application;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

class RestoreTrashEntryUseCaseTest {

    private static final Instant NOW = Instant.parse("2026-11-14T12:00:00Z");
    private static final Duration RETENTION = Duration.ofHours(30 * 24);
    private static final Instant DELETED = NOW.minus(Duration.ofDays(1));
    private static final String HINT = " Der Eintrag kann in ein neues Album mit freiem Namen wiederhergestellt werden.";

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final InMemoryTrashRepository trash = new InMemoryTrashRepository();
    private final InMemoryPhotoStorage photos = new InMemoryPhotoStorage();
    private final RestoreTrashEntryUseCase useCase =
            new RestoreTrashEntryUseCase(albums, trash, photos, Clock.fixed(NOW, ZoneOffset.UTC));

    @Test
    @DisplayName("AK-09.3: Ein einzelnes Foto kehrt in sein Ursprungsalbum zurück")
    void restoresPhotoIntoOrigin() {
        Album urlaub = albumWith("Urlaub", "hash-1", "hash-2");
        TrashEntry entry = trashPhotoFrom(urlaub, 1, DELETED);
        albums.save(urlaub);

        Result<AlbumSummary> result = useCase.execute(entry.getId().value());

        assertEquals("Urlaub", result.getValue().name());
        assertEquals(List.of("hash-1", "hash-2"), hashesOf(urlaub.getId().value()));
        assertEquals(List.of(), trash.entries());
    }

    @Test
    @DisplayName("AK-09.4: Fehlt das Ursprungsalbum, wird es mit seinem Namen und neuer ID angelegt")
    void recreatesMissingOriginForPhoto() {
        Album urlaub = albumWith("Urlaub", "hash-1");
        TrashEntry entry = trashPhotoFrom(urlaub, 0, DELETED);

        Result<AlbumSummary> result = useCase.execute(entry.getId().value());

        assertEquals("Urlaub", result.getValue().name());
        assertNotEquals(urlaub.getId().value(), result.getValue().id());
        assertEquals(List.of("hash-1"), hashesOf(result.getValue().id()));
        assertEquals(List.of(), trash.entries());
    }

    @Test
    @DisplayName("AK-04.3: Eine Kachel wird mit ihrem Namen und allen Fotos als Album wiederhergestellt")
    void restoresTileAsAlbum() {
        TrashEntry tile = trashAlbum(albumWith("Urlaub", "hash-1", "hash-2", "hash-3"), DELETED);

        Result<AlbumSummary> result = useCase.execute(tile.getId().value());

        assertEquals("Urlaub", result.getValue().name());
        assertEquals(List.of("hash-1", "hash-2", "hash-3"), hashesOf(result.getValue().id()));
        assertEquals(List.of(), trash.entries());
    }

    @Test
    @DisplayName("AK-04.6: Heißt ein anderes Album schon so, wird der Konflikt gemeldet und die Kachel bleibt")
    void reportsNameConflictForTile() {
        TrashEntry tile = trashAlbum(albumWith("Urlaub", "hash-1"), DELETED);
        Album active = albumWith("urlaub", "hash-9");
        albums.save(active);

        Result<AlbumSummary> result = useCase.execute(tile.getId().value());

        assertEquals("Ein anderes Album heißt bereits „Urlaub“." + HINT, result.getMessage());
        assertEquals(1, trash.entries().size());
        assertEquals(List.of("hash-9"), hashesOf(active.getId().value()));
    }

    @Test
    @DisplayName("AK-04.4: Eine Kachel mit 100 Fotos kommt in ein neues Album, das volle aktive Album bleibt unverändert")
    void restoresFullTileIntoNewAlbum() {
        TrashEntry tile = trashAlbum(albumWithPhotos("Urlaub", "kachel-", 100), DELETED);
        Album active = albumWithPhotos("Urlaub", "aktiv-", 100);
        albums.save(active);

        Result<AlbumSummary> result = useCase.executeIntoNewAlbum(tile.getId().value(), "Urlaub 2025");

        assertEquals(100, hashesOf(result.getValue().id()).size());
        assertEquals(100, hashesOf(active.getId().value()).size());
        assertEquals(List.of(), trash.entries());
    }

    @Test
    @DisplayName("AK-09.5: Enthält das Ursprungsalbum ein bytegleiches Foto, wird der Konflikt genannt und das Foto bleibt")
    void reportsDuplicateInOrigin() {
        Album urlaub = albumWith("Urlaub", "hash-1");
        TrashEntry entry = trashPhotoFrom(urlaub, 0, DELETED);
        urlaub.addPhoto(new Photo("hash-1"));
        albums.save(urlaub);

        Result<AlbumSummary> result = useCase.execute(entry.getId().value());

        assertEquals("Dieses Bild befindet sich schon im Ursprungsalbum." + HINT, result.getMessage());
        assertEquals(1, trash.entries().size());
        assertEquals(List.of("hash-1"), hashesOf(urlaub.getId().value()));
    }

    @Test
    @DisplayName("AK-09.5: Ist das Ursprungsalbum voll, wird der Konflikt genannt und das Foto bleibt")
    void reportsFullOrigin() {
        Album urlaub = albumWith("Urlaub", "hash-neu");
        TrashEntry entry = trashPhotoFrom(urlaub, 0, DELETED);
        for (int i = 1; i <= 100; i++) {
            urlaub.addPhoto(new Photo("hash-" + i));
        }
        albums.save(urlaub);

        Result<AlbumSummary> result = useCase.execute(entry.getId().value());

        assertEquals("Das Ursprungsalbum ist voll (maximal 100 Fotos)." + HINT, result.getMessage());
        assertEquals(1, trash.entries().size());
        assertEquals(100, hashesOf(urlaub.getId().value()).size());
    }

    @Test
    @DisplayName("AK-09.6: Ein Foto mit Konflikt kommt in ein neues Album, das Ursprungsalbum bleibt unverändert")
    void restoresConflictingPhotoIntoNewAlbum() {
        Album urlaub = albumWith("Urlaub", "hash-1");
        TrashEntry entry = trashPhotoFrom(urlaub, 0, DELETED);
        urlaub.addPhoto(new Photo("hash-1"));
        albums.save(urlaub);

        Result<AlbumSummary> result = useCase.executeIntoNewAlbum(entry.getId().value(), "Neu");

        assertEquals("Neu", result.getValue().name());
        assertEquals(List.of("hash-1"), hashesOf(result.getValue().id()));
        assertEquals(List.of("hash-1"), hashesOf(urlaub.getId().value()));
        assertEquals(List.of(), trash.entries());
    }

    @Test
    @DisplayName("A-02: Ein vergebener Name für das neue Album wird abgelehnt, der Eintrag bleibt")
    void rejectsTakenNameForNewAlbum() {
        TrashEntry tile = trashAlbum(albumWith("Urlaub", "hash-1"), DELETED);
        albums.save(albumWith("Party", "hash-2"));

        Result<AlbumSummary> result = useCase.executeIntoNewAlbum(tile.getId().value(), " party ");

        assertEquals("Dieser Albumname ist bereits vergeben.", result.getMessage());
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("AK-SR01.6: Ein fälliger Eintrag wird nicht wiederhergestellt, sondern samt Kopie bereinigt")
    void refusesDueEntryAndCleansItUp() {
        TrashEntry tile = trashAlbum(albumWith("Urlaub", "hash-1"), NOW.minus(RETENTION));

        Result<AlbumSummary> result = useCase.execute(tile.getId().value());

        assertEquals("Die Frist für die Wiederherstellung ist abgelaufen.", result.getMessage());
        assertEquals(List.of(), trash.entries());
        assertEquals(Set.of(), photos.storedIds());
        assertEquals(List.of(), albums.names());
    }

    @Test
    @DisplayName("AK-SR01.6: Scheitert die Bereinigung, bleibt der fällige Eintrag gesperrt im Papierkorb")
    void keepsDueEntryBlockedWhenCleanupFails() {
        TrashEntry tile = trashAlbum(albumWith("Urlaub", "hash-1"), NOW.minus(RETENTION));
        photos.failOnDelete = true;

        Result<AlbumSummary> result = useCase.execute(tile.getId().value());

        assertEquals("Die Frist für die Wiederherstellung ist abgelaufen.", result.getMessage());
        assertEquals(1, trash.entries().size());
        assertEquals(1, photos.storedIds().size());
        assertEquals(List.of(), albums.names());
    }

    @Test
    @DisplayName("A-10: Vor dem Wiederherstellen werden andere fällige Einträge bereinigt")
    void cleansOtherDueEntriesBeforeRestoring() {
        trashAlbum(albumWith("Alt", "hash-1"), NOW.minus(RETENTION));
        TrashEntry tile = trashAlbum(albumWith("Urlaub", "hash-2"), DELETED);

        useCase.execute(tile.getId().value());

        assertEquals(List.of(), trash.entries());
        assertEquals(Set.of(tile.getPhotos().get(0).getId()), photos.storedIds());
    }

    @Test
    @DisplayName("AK-04.5: Scheitert trash.json, wird das neu angelegte Album wieder entfernt und die Kachel bleibt")
    void removesCreatedAlbumWhenTrashSaveFails() {
        TrashEntry tile = trashAlbum(albumWith("Urlaub", "hash-1", "hash-2"), DELETED);
        trash.failOnSave = true;

        Result<AlbumSummary> result = useCase.execute(tile.getId().value());

        assertEquals("Der Eintrag konnte nicht wiederhergestellt werden.", result.getMessage());
        assertEquals(List.of(), albums.names());
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("NFA-01: Scheitert trash.json, wird das Foto wieder aus dem Ursprungsalbum genommen")
    void removesPhotoFromOriginWhenTrashSaveFails() {
        Album urlaub = albumWith("Urlaub", "hash-1", "hash-2");
        TrashEntry entry = trashPhotoFrom(urlaub, 1, DELETED);
        albums.save(urlaub);
        trash.failOnSave = true;

        Result<AlbumSummary> result = useCase.execute(entry.getId().value());

        assertEquals("Der Eintrag konnte nicht wiederhergestellt werden.", result.getMessage());
        assertEquals(List.of("hash-1"), hashesOf(urlaub.getId().value()));
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("A-11: Scheitert auch das Zurücknehmen, kommt die Aufräummeldung und das Album bleibt")
    void reportsCleanupWhenRollbackFails() {
        TrashEntry tile = trashAlbum(albumWith("Urlaub", "hash-1"), DELETED);
        trash.failOnSave = true;
        albums.failOnDelete = true;

        Result<AlbumSummary> result = useCase.execute(tile.getId().value());

        assertEquals(AddPhotoUseCase.CLEANUP_ON_NEXT_START, result.getMessage());
        assertEquals(List.of("Urlaub"), albums.names());
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("NFA-01: Scheitert schon das Album, bleibt der Eintrag im Papierkorb")
    void keepsEntryWhenAlbumSaveFails() {
        TrashEntry tile = trashAlbum(albumWith("Urlaub", "hash-1"), DELETED);
        albums.failOnSave = true;

        Result<AlbumSummary> result = useCase.execute(tile.getId().value());

        assertEquals("Der Eintrag konnte nicht wiederhergestellt werden.", result.getMessage());
        assertEquals(List.of(), albums.names());
        assertEquals(1, trash.entries().size());
    }

    @Test
    @DisplayName("Ein unbekannter Eintrag wird mit Meldung abgelehnt")
    void reportsUnknownEntry() {
        Result<AlbumSummary> result = useCase.execute(UUID.randomUUID());

        assertEquals("Der Eintrag wurde nicht gefunden.", result.getMessage());
    }

    private TrashEntry trashPhotoFrom(Album album, int photoIndex, Instant deletedAt) {
        Trash content = trash.load();
        TrashEntry entry = content.movePhoto(album.getPhotos().get(photoIndex), album, deletedAt);
        trash.save(content);
        entry.getPhotos().forEach(photo -> photos.put(photo.getId(), new byte[] {1}));
        return entry;
    }

    private TrashEntry trashAlbum(Album album, Instant deletedAt) {
        Trash content = trash.load();
        TrashEntry entry = content.moveAlbum(album, deletedAt);
        trash.save(content);
        entry.getPhotos().forEach(photo -> photos.put(photo.getId(), new byte[] {1}));
        return entry;
    }

    private List<String> hashesOf(UUID albumId) {
        return albums.findById(new AlbumId(albumId)).orElseThrow().getPhotos().stream()
                .map(Photo::getContentHash)
                .toList();
    }

    private static Album albumWith(String name, String... hashes) {
        Album album = new Album(name);
        for (String hash : hashes) {
            album.addPhoto(new Photo(hash));
        }
        return album;
    }

    private static Album albumWithPhotos(String name, String hashPrefix, int count) {
        Album album = new Album(name);
        for (int i = 1; i <= count; i++) {
            album.addPhoto(new Photo(hashPrefix + i));
        }
        return album;
    }
}
