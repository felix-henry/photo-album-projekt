package de.commitmentissues.photoalbum.infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.commitmentissues.photoalbum.application.StorageException;
import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;

class FileAlbumRepositoryTest {

    @TempDir
    Path dataDirectory;

    @Test
    @DisplayName("NFA-02: Ein gespeichertes Album ist nach einem Neustart mit ID und Namen vorhanden")
    void keepsAlbumAfterRestart() {
        Album album = new Album("Grüße 📷");
        new FileAlbumRepository(dataDirectory).save(album);

        List<Album> loaded = new FileAlbumRepository(dataDirectory).findAll();

        assertEquals(1, loaded.size());
        assertEquals(album.getId(), loaded.get(0).getId());
        assertEquals("Grüße 📷", loaded.get(0).getName());
    }

    @Test
    @DisplayName("ADR-001: Erneutes Speichern ersetzt die Datei desselben Albums")
    void replacesFileOfSameAlbum() {
        FileAlbumRepository repository = new FileAlbumRepository(dataDirectory);
        Album album = new Album("Urlaub");
        repository.save(album);

        repository.save(Album.restore(album.getId(), "Sommer"));

        List<String> names = repository.findAll().stream().map(Album::getName).toList();
        assertEquals(List.of("Sommer"), names);
    }

    @Test
    @DisplayName("ADR-002: Nach dem Speichern bleibt keine Zwischendatei zurück")
    void leavesNoTemporaryFile() throws IOException {
        Album album = new Album("Urlaub");

        new FileAlbumRepository(dataDirectory).save(album);

        try (Stream<Path> files = Files.list(dataDirectory.resolve("albums"))) {
            List<String> names = files.map(file -> file.getFileName().toString()).toList();
            assertEquals(List.of(album.getId().value() + ".json"), names);
        }
    }

    @Test
    @DisplayName("AK-02.2: Ohne gespeicherte Alben ist die Liste leer")
    void findsNothingWithoutAlbumFolder() {
        List<Album> loaded = new FileAlbumRepository(dataDirectory).findAll();

        assertEquals(List.of(), loaded);
    }

    @Test
    @DisplayName("AK-02.3: Eine unlesbare Albumdatei wird als Fehler gemeldet statt übersprungen")
    void reportsUnreadableAlbumFile() throws IOException {
        Path albums = Files.createDirectories(dataDirectory.resolve("albums"));
        Files.writeString(albums.resolve(AlbumId.newId().value() + ".json"), "{\"id\": ");
        FileAlbumRepository repository = new FileAlbumRepository(dataDirectory);

        assertThrows(StorageException.class, repository::findAll);
    }

    @Test
    @DisplayName("AK-01.6: Scheitert das Speichern, wird ein Speicherfehler gemeldet")
    void reportsSaveFailure() throws IOException {
        Files.writeString(dataDirectory.resolve("albums"), "Datei statt Ordner");
        FileAlbumRepository repository = new FileAlbumRepository(dataDirectory);

        assertThrows(StorageException.class, () -> repository.save(new Album("Urlaub")));
    }

    @Test
    @DisplayName("Ein gespeichertes Album wird über seine ID gefunden")
    void findsAlbumById() {
        FileAlbumRepository repository = new FileAlbumRepository(dataDirectory);
        Album album = new Album("Urlaub");
        repository.save(album);

        Optional<Album> found = repository.findById(album.getId());

        assertEquals("Urlaub", found.orElseThrow().getName());
    }

    @Test
    @DisplayName("Zu einer unbekannten ID gibt es kein Album")
    void findsNothingForUnknownId() {
        Optional<Album> found = new FileAlbumRepository(dataDirectory).findById(AlbumId.newId());

        assertTrue(found.isEmpty());
    }
}
