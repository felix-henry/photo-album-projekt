package de.commitmentissues.photoalbum.infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.commitmentissues.photoalbum.application.StorageException;
import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.PhotoId;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

class FileTrashRepositoryTest {

    private static final Instant NOW = Instant.parse("2026-11-14T12:00:00.123Z");

    @TempDir
    Path dataDirectory;

    @Test
    @DisplayName("Ohne trash.json ist der Papierkorb leer")
    void loadsEmptyTrashWithoutFile() {
        Trash trash = new FileTrashRepository(dataDirectory).load();

        assertEquals(List.of(), trash.getEntries());
    }

    @Test
    @DisplayName("AK-N02.1: Kachel und einzelnes Foto sind nach einem Neustart mit allen Angaben vorhanden")
    void keepsEntriesAfterRestart() {
        Album urlaub = new Album("Grüße 📷");
        urlaub.addPhoto(Photo.restore(PhotoId.newId(), "hash-1", "Strand", "Sonne & \"Meer\""));
        urlaub.addPhoto(new Photo("hash-2"));
        urlaub.addPhoto(new Photo("hash-3"));
        Trash trash = new Trash(List.of());
        trash.movePhoto(urlaub.getPhotos().get(2), urlaub, NOW.minusSeconds(60));
        trash.moveAlbum(urlaub, NOW);
        new FileTrashRepository(dataDirectory).save(trash);

        Trash loaded = new FileTrashRepository(dataDirectory).load();

        assertEquals(describe(trash), describe(loaded));
    }

    @Test
    @DisplayName("Eine Kachel eines leeren Albums bleibt nach einem Neustart erhalten")
    void keepsEmptyAlbumTile() {
        Trash trash = new Trash(List.of());
        trash.moveAlbum(new Album("Leer"), NOW);
        new FileTrashRepository(dataDirectory).save(trash);

        Trash loaded = new FileTrashRepository(dataDirectory).load();

        assertEquals(describe(trash), describe(loaded));
    }

    @Test
    @DisplayName("ADR-002: Nach dem Speichern bleibt keine Zwischendatei zurück")
    void leavesNoTemporaryFile() throws IOException {
        new FileTrashRepository(dataDirectory).save(new Trash(List.of()));

        try (Stream<Path> files = Files.list(dataDirectory)) {
            assertEquals(List.of("trash.json"), files.map(file -> file.getFileName().toString()).toList());
        }
    }

    @Test
    @DisplayName("AK-N02.2: Eine unlesbare trash.json wird als Fehler gemeldet und nicht als leerer Papierkorb")
    void reportsUnreadableFile() throws IOException {
        Files.writeString(dataDirectory.resolve("trash.json"), "{\"entries\": [");
        FileTrashRepository repository = new FileTrashRepository(dataDirectory);

        assertThrows(StorageException.class, repository::load);
    }

    @Test
    @DisplayName("Ein Eintrag ohne Löschzeitpunkt macht trash.json unlesbar")
    void reportsEntryWithoutDeletionTime() throws IOException {
        Files.writeString(dataDirectory.resolve("trash.json"), """
                {"entries": [{"id": "%s", "originAlbumId": "%s", "originAlbumName": "Urlaub", "album": true, "photos": []}]}
                """.formatted(java.util.UUID.randomUUID(), java.util.UUID.randomUUID()));
        FileTrashRepository repository = new FileTrashRepository(dataDirectory);

        assertThrows(StorageException.class, repository::load);
    }

    @Test
    @DisplayName("Scheitert das Speichern, wird ein Speicherfehler gemeldet")
    void reportsSaveFailure() throws IOException {
        Path blocked = Files.createDirectories(dataDirectory.resolve("trash.json"));
        Files.writeString(blocked.resolve("blockiert.txt"), "x");
        FileTrashRepository repository = new FileTrashRepository(dataDirectory);
        Trash trash = new Trash(List.of());

        assertThrows(StorageException.class, () -> repository.save(trash));
        assertTrue(Files.isDirectory(blocked));
    }

    private static List<String> describe(Trash trash) {
        return trash.getEntries().stream().map(FileTrashRepositoryTest::describe).toList();
    }

    private static String describe(TrashEntry entry) {
        List<String> photos = entry.getPhotos().stream()
                .map(photo -> photo.getId().value() + "/" + photo.getContentHash() + "/"
                        + photo.getTitle() + "/" + photo.getDescription())
                .toList();
        return entry.getId().value() + " " + entry.getDeletedAt() + " " + entry.getOriginAlbumId().value()
                + " " + entry.getOriginAlbumName() + " " + entry.isAlbum() + " " + photos;
    }
}
