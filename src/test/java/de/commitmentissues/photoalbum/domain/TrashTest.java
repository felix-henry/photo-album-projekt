package de.commitmentissues.photoalbum.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TrashTest {

    private static final Instant NOW = Instant.parse("2026-11-14T12:00:00Z");
    private static final Duration RETENTION = Duration.ofHours(30 * 24);

    private final Trash trash = new Trash(List.of());

    @Test
    @DisplayName("AK-09.1: Ein entferntes Foto wird ein eigener Eintrag mit Ursprungsalbum und Löschzeitpunkt")
    void movesPhotoAsOwnEntry() {
        Album urlaub = albumWith("Urlaub", "hash-1", "hash-2", "hash-3");
        Photo second = urlaub.getPhotos().get(1);

        TrashEntry entry = trash.movePhoto(second, urlaub, NOW);

        assertEquals(2, urlaub.getPhotos().size());
        assertFalse(entry.isAlbum());
        assertEquals(List.of(second), entry.getPhotos());
        assertEquals(urlaub.getId(), entry.getOriginAlbumId());
        assertEquals("Urlaub", entry.getOriginAlbumName());
        assertEquals(NOW, entry.getDeletedAt());
    }

    @Test
    @DisplayName("AK-09.1: Ein bytegleiches Foto aus einem anderen Album bleibt ein getrennter Eintrag")
    void keepsByteIdenticalEntriesSeparate() {
        Album party = albumWith("Party", "hash-strand");
        Album urlaub = albumWith("Urlaub", "hash-strand");
        removeFirstPhoto(party, NOW);

        removeFirstPhoto(urlaub, NOW);

        assertEquals(2, trash.getEntries().size());
    }

    @Test
    @DisplayName("AK-04.1: Ein gelöschtes Album wird eine Kachel, ein früher entferntes Foto bleibt ein eigener Eintrag")
    void movesAlbumAsTile() {
        Album urlaub = albumWith("Urlaub", "hash-1", "hash-2", "hash-3");
        TrashEntry single = removeFirstPhoto(urlaub, NOW);

        TrashEntry tile = trash.moveAlbum(urlaub, NOW);

        assertTrue(tile.isAlbum());
        assertEquals("Urlaub", tile.getOriginAlbumName());
        assertEquals(2, tile.getPhotos().size());
        assertEquals(List.of(single, tile), trash.getEntries());
    }

    @Test
    @DisplayName("A-09: Ein nicht fälliger Eintrag wird zum Wiederherstellen herausgegeben und verlässt den Papierkorb")
    void takesEntryBeforeDeadline() {
        TrashEntry entry = trash.moveAlbum(albumWith("Urlaub", "hash-1"), NOW);

        TrashEntry taken = trash.take(entry.getId(), NOW.plus(RETENTION).minusSeconds(1));

        assertEquals(entry, taken);
        assertEquals(List.of(), trash.getEntries());
    }

    @Test
    @DisplayName("AK-SR01.6: Ein fälliger Eintrag wird nicht herausgegeben und bleibt im Papierkorb")
    void refusesDueEntry() {
        TrashEntry entry = trash.moveAlbum(albumWith("Urlaub", "hash-1"), NOW);
        TrashEntryId id = entry.getId();
        Instant due = NOW.plus(RETENTION);

        DomainException exception = assertThrows(DomainException.class, () -> trash.take(id, due));

        assertEquals(Violation.TRASH_ENTRY_EXPIRED, exception.getViolation());
        assertEquals(List.of(entry), trash.getEntries());
    }

    @Test
    @DisplayName("AK-SR01.3: Fällig sind ein Foto nach genau und eine Kachel nach mehr als 30 × 24 Stunden, ein Eintrag eine Sekunde davor nicht")
    void listsOnlyDueEntries() {
        TrashEntry photo = removeFirstPhoto(albumWith("Urlaub", "hash-1"), NOW);
        TrashEntry tile = trash.moveAlbum(albumWith("Party", "hash-2"), NOW.minusSeconds(60));
        removeFirstPhoto(albumWith("Best of", "hash-3"), NOW.plusSeconds(1));

        List<TrashEntry> due = trash.dueEntries(NOW.plus(RETENTION));

        assertEquals(List.of(photo, tile), due);
        assertEquals(3, trash.getEntries().size());
    }

    @Test
    @DisplayName("AK-SR01.4: Nach einem Teilerfolg beim endgültigen Löschen bleibt nur das nicht gelöschte Foto in der Kachel")
    void keepsPhotosThatWereNotDeleted() {
        Album urlaub = albumWith("Urlaub", "hash-1", "hash-2", "hash-3");
        List<Photo> photos = urlaub.getPhotos();
        TrashEntry tile = trash.moveAlbum(urlaub, NOW);

        trash.remove(tile.getId(), List.of(photos.get(0).getId(), photos.get(2).getId()));

        assertEquals(List.of(photos.get(1)), tile.getPhotos());
        assertEquals(List.of(tile), trash.getEntries());
    }

    @Test
    @DisplayName("SR-01: Sind alle Fotos eines Eintrags gelöscht, entfällt der Eintrag")
    void removesEmptyEntry() {
        TrashEntry entry = removeFirstPhoto(albumWith("Urlaub", "hash-1"), NOW);

        trash.remove(entry.getId(), List.of(entry.getPhotos().get(0).getId()));

        assertEquals(List.of(), trash.getEntries());
    }

    private TrashEntry removeFirstPhoto(Album album, Instant at) {
        return trash.movePhoto(album.getPhotos().get(0), album, at);
    }

    private static Album albumWith(String name, String... hashes) {
        Album album = new Album(name);
        for (String hash : hashes) {
            album.addPhoto(new Photo(hash));
        }
        return album;
    }
}
