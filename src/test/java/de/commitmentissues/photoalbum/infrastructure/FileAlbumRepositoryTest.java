package de.commitmentissues.photoalbum.infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.commitmentissues.photoalbum.application.EditPhotoTextsUseCase;
import de.commitmentissues.photoalbum.application.RenameAlbumUseCase;
import de.commitmentissues.photoalbum.application.StorageException;
import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.PhotoId;

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

    @Test
    @DisplayName("NFA-02: Fotos sind nach einem Neustart mit ID, Inhaltshash und Reihenfolge vorhanden")
    void keepsPhotosAfterRestart() {
        Album album = albumWithPhotos("Urlaub", 3);
        new FileAlbumRepository(dataDirectory).save(album);

        Album loaded = new FileAlbumRepository(dataDirectory).findById(album.getId()).orElseThrow();

        assertEquals(idsAndHashes(album), idsAndHashes(loaded));
    }

    @Test
    @DisplayName("Eine Albumdatei ohne Fotoliste aus einer früheren Version wird als Album ohne Fotos gelesen")
    void readsAlbumFileWithoutPhotoList() throws IOException {
        AlbumId id = AlbumId.newId();
        writeAlbumFile(id, """
                {"id": "%s", "name": "Party"}
                """.formatted(id.value()));

        Album loaded = new FileAlbumRepository(dataDirectory).findById(id).orElseThrow();

        assertEquals("Party", loaded.getName());
        assertEquals(List.of(), loaded.getPhotos());
    }

    @Test
    @DisplayName("AK-02.3: Ein Foto ohne Inhaltshash macht die Albumdatei unlesbar")
    void reportsPhotoWithoutHash() throws IOException {
        AlbumId id = AlbumId.newId();
        writeAlbumFile(id, """
                {"id": "%s", "name": "Party", "photos": [{"id": "%s"}]}
                """.formatted(id.value(), PhotoId.newId().value()));
        FileAlbumRepository repository = new FileAlbumRepository(dataDirectory);

        assertThrows(StorageException.class, repository::findAll);
    }

    @Test
    @DisplayName("AK-03.1: Nach dem Umbenennen enthält das gespeicherte Album dieselben Fotos")
    void renamedAlbumKeepsPhotos() {
        FileAlbumRepository repository = new FileAlbumRepository(dataDirectory);
        Album urlaub = albumWithPhotos("Urlaub", 3);
        repository.save(urlaub);

        new RenameAlbumUseCase(repository).execute(urlaub.getId().value(), "urlaub");

        Album loaded = new FileAlbumRepository(dataDirectory).findById(urlaub.getId()).orElseThrow();
        assertEquals("urlaub", loaded.getName());
        assertEquals(idsAndHashes(urlaub), idsAndHashes(loaded));
    }

    @Test
    @DisplayName("AK-10.1: Titel und Beschreibung sind nach einem Neustart abrufbar")
    void keepsPhotoTextsAfterRestart() {
        FileAlbumRepository repository = new FileAlbumRepository(dataDirectory);
        Album urlaub = albumWithPhotos("Urlaub", 1);
        repository.save(urlaub);
        UUID photoId = urlaub.getPhotos().get(0).getId().value();

        new EditPhotoTextsUseCase(repository)
                .execute(urlaub.getId().value(), photoId, "Grüße vom Strand 📷", "Sonne, \"Meer\" & <Wind>");

        Photo loaded = new FileAlbumRepository(dataDirectory).findById(urlaub.getId()).orElseThrow().getPhotos().get(0);
        assertEquals("Grüße vom Strand 📷", loaded.getTitle());
        assertEquals("Sonne, \"Meer\" & <Wind>", loaded.getDescription());
    }

    @Test
    @DisplayName("Ein Foto ohne Titel und Beschreibung aus einer früheren Version wird mit leeren Texten gelesen")
    void readsPhotoWithoutTexts() throws IOException {
        AlbumId id = AlbumId.newId();
        writeAlbumFile(id, """
                {"id": "%s", "name": "Party", "photos": [{"id": "%s", "contentHash": "hash-1"}]}
                """.formatted(id.value(), PhotoId.newId().value()));

        Photo loaded = new FileAlbumRepository(dataDirectory).findById(id).orElseThrow().getPhotos().get(0);

        assertEquals("", loaded.getTitle());
        assertEquals("", loaded.getDescription());
    }

    @Test
    @DisplayName("A-07: Ein gelöschtes Album ist nach einem Neustart nicht mehr vorhanden")
    void deletesAlbumFile() {
        FileAlbumRepository repository = new FileAlbumRepository(dataDirectory);
        Album urlaub = new Album("Urlaub");
        repository.save(urlaub);
        repository.save(new Album("Party"));

        repository.delete(urlaub.getId());

        List<String> names = new FileAlbumRepository(dataDirectory).findAll().stream().map(Album::getName).toList();
        assertEquals(List.of("Party"), names);
    }

    @Test
    @DisplayName("Das Löschen eines schon fehlenden Albums gilt als Erfolg")
    void deletingMissingAlbumSucceeds() {
        FileAlbumRepository repository = new FileAlbumRepository(dataDirectory);

        repository.delete(AlbumId.newId());

        assertEquals(List.of(), repository.findAll());
    }

    private void writeAlbumFile(AlbumId id, String json) throws IOException {
        Path albums = Files.createDirectories(dataDirectory.resolve("albums"));
        Files.writeString(albums.resolve(id.value() + ".json"), json);
    }

    private static Album albumWithPhotos(String name, int count) {
        Album album = new Album(name);
        for (int i = 1; i <= count; i++) {
            album.addPhoto(new Photo("hash-" + i));
        }
        return album;
    }

    private static List<String> idsAndHashes(Album album) {
        return album.getPhotos().stream()
                .map(photo -> photo.getId().value() + ":" + photo.getContentHash())
                .toList();
    }
}
