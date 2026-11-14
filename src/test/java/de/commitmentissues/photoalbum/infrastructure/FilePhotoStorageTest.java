package de.commitmentissues.photoalbum.infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.commitmentissues.photoalbum.application.StorageException;
import de.commitmentissues.photoalbum.domain.PhotoId;

class FilePhotoStorageTest {

    private static final byte[] IMAGE_BYTES = {1, 2, 3, 4, 5};

    @TempDir
    Path dir;

    private FilePhotoStorage storage;
    private Path original;

    @BeforeEach
    void setUp() throws IOException {
        storage = new FilePhotoStorage(dir.resolve("daten"));
        original = Files.write(dir.resolve("strand.jpg"), IMAGE_BYTES);
    }

    @Test
    @DisplayName("A-05: store legt eine bytegleiche Kopie unter der Foto-ID an")
    void storeCopiesFileUnderPhotoId() throws IOException {
        PhotoId id = PhotoId.newId();

        storage.store(id, original);

        assertArrayEquals(IMAGE_BYTES, Files.readAllBytes(copyOf(id)));
    }

    @Test
    @DisplayName("AK-05.1: Die Originaldatei bleibt nach dem Speichern unverändert erhalten")
    void storeKeepsOriginal() throws IOException {
        storage.store(PhotoId.newId(), original);

        assertArrayEquals(IMAGE_BYTES, Files.readAllBytes(original));
    }

    @Test
    @DisplayName("ADR-002: Nach dem Speichern bleibt keine Zwischendatei zurück")
    void storeLeavesNoTemporaryFile() throws IOException {
        PhotoId id = PhotoId.newId();

        storage.store(id, original);

        assertEquals(List.of(copyOf(id)), filesInPhotoFolder());
    }

    @Test
    @DisplayName("ADR-002: Scheitert das Kopieren, meldet store einen Speicherfehler und hinterlässt keine Datei")
    void storeOfMissingFileFails() throws IOException {
        Path missing = dir.resolve("fehlt.jpg");

        assertThrows(StorageException.class, () -> storage.store(PhotoId.newId(), missing));
        assertEquals(List.of(), filesInPhotoFolder());
    }

    @Test
    @DisplayName("Ist der Fotoordner nicht anlegbar, meldet store einen Speicherfehler")
    void storeFailsWhenPhotoFolderIsNotCreatable() throws IOException {
        Files.createDirectories(dir.resolve("daten"));
        Files.writeString(photoFolder(), "kein Ordner");

        assertThrows(StorageException.class, () -> storage.store(PhotoId.newId(), original));
    }

    @Test
    @DisplayName("delete entfernt die App-Kopie")
    void deleteRemovesCopy() {
        PhotoId id = PhotoId.newId();
        storage.store(id, original);

        storage.delete(id);

        assertFalse(Files.exists(copyOf(id)));
    }

    @Test
    @DisplayName("ADR-002: Das Löschen einer schon fehlenden Kopie gilt als Erfolg")
    void deleteOfMissingCopySucceeds() {
        assertDoesNotThrow(() -> storage.delete(PhotoId.newId()));
    }

    @Test
    @DisplayName("Scheitert das Löschen, meldet delete einen Speicherfehler")
    void deleteFailureIsReported() throws IOException {
        PhotoId id = PhotoId.newId();
        Files.createDirectories(copyOf(id));
        Files.writeString(copyOf(id).resolve("blockiert.txt"), "x");

        assertThrows(StorageException.class, () -> storage.delete(id));
    }

    @Test
    @DisplayName("AK-08.1: load liefert die Bilddaten der gespeicherten Kopie")
    void loadReturnsStoredCopy() {
        PhotoId id = PhotoId.newId();
        storage.store(id, original);

        byte[] content = storage.load(id);

        assertArrayEquals(IMAGE_BYTES, content);
    }

    @Test
    @DisplayName("AK-08.3: Eine fehlende Kopie meldet einen Lesefehler")
    void loadOfMissingCopyFails() {
        assertThrows(StorageException.class, () -> storage.load(PhotoId.newId()));
    }

    private Path photoFolder() {
        return dir.resolve("daten").resolve("photos");
    }

    private Path copyOf(PhotoId id) {
        return photoFolder().resolve(id.value().toString());
    }

    private List<Path> filesInPhotoFolder() throws IOException {
        if (Files.notExists(photoFolder())) {
            return List.of();
        }
        try (Stream<Path> files = Files.list(photoFolder())) {
            return files.toList();
        }
    }
}
