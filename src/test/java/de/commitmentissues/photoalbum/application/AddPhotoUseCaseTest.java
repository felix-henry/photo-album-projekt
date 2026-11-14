package de.commitmentissues.photoalbum.application;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.PhotoId;

class AddPhotoUseCaseTest {

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final FakeImageHasher hasher = new FakeImageHasher();
    private final InMemoryPhotoStorage storage = new InMemoryPhotoStorage();
    private final AddPhotoUseCase useCase = new AddPhotoUseCase(albums, hasher, storage);
    private final Path file = Path.of("strand.jpg");

    @Test
    @DisplayName("AK-05.1: Das Foto wird dem Album zugeordnet und seine App-Kopie angelegt")
    void addsPhotoAndStoresCopy() {
        Album album = savedAlbum();

        Result<PhotoSummary> result = useCase.execute(album.getId().value(), file);

        assertTrue(result.isSuccess());
        UUID photoId = result.getValue().id();
        assertEquals(List.of(photoId), photoIdsOf(album));
        assertEquals(Set.of(new PhotoId(photoId)), storage.storedIds());
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("AK-05.2, AK-05.4: Eine ungültige Datei wird mit Grund abgelehnt, nichts wird gespeichert")
    @CsvSource(delimiter = '|', textBlock = """
            UNREADABLE   | Die Datei konnte nicht gelesen werden.
            EMPTY        | Die Bilddatei ist leer.
            NOT_AN_IMAGE | Die Datei enthält kein gültiges JPEG- oder PNG-Bild.
            TOO_LARGE    | Die Bilddatei darf höchstens 20 MiB groß sein.
            """)
    void rejectsInvalidFile(ImageProblem problem, String message) {
        Album album = savedAlbum();
        hasher.rejects(file, problem);

        Result<PhotoSummary> result = useCase.execute(album.getId().value(), file);

        assertEquals(message, result.getMessage());
        assertEquals(List.of(), photoIdsOf(album));
        assertEquals(Set.of(), storage.storedIds());
    }

    @Test
    @DisplayName("AK-06.1: Ein bytegleiches Bild mit anderem Namen wird abgelehnt, keine Kopie wird angelegt")
    void rejectsDuplicateWithoutStoringCopy() {
        Album album = new Album("Urlaub");
        album.addPhoto(new Photo("hash-strand"));
        albums.save(album);
        Path copy = Path.of("kopie.jpg");
        hasher.returns(copy, "hash-strand");

        Result<PhotoSummary> result = useCase.execute(album.getId().value(), copy);

        assertEquals("Dieses Bild befindet sich schon in diesem Album", result.getMessage());
        assertEquals(1, photoIdsOf(album).size());
        assertEquals(Set.of(), storage.storedIds());
    }

    @Test
    @DisplayName("AK-07.2: Ein volles Album lehnt das Foto ab und behält seine 100 Fotos")
    void rejectsPhotoForFullAlbum() {
        Album album = new Album("Klassenfahrt");
        for (int i = 1; i <= 100; i++) {
            album.addPhoto(new Photo("hash-" + i));
        }
        albums.save(album);

        Result<PhotoSummary> result = useCase.execute(album.getId().value(), file);

        assertEquals("Das Album ist voll (maximal 100 Fotos).", result.getMessage());
        assertEquals(100, photoIdsOf(album).size());
        assertEquals(Set.of(), storage.storedIds());
    }

    @Test
    @DisplayName("AK-05.5: Scheitert das Speichern des Albums, wird die App-Kopie zurückgenommen")
    void rollsBackCopyWhenAlbumCannotBeSaved() {
        Album album = savedAlbum();
        albums.failOnSave = true;

        Result<PhotoSummary> result = useCase.execute(album.getId().value(), file);

        assertEquals(AddPhotoUseCase.SAVE_FAILED, result.getMessage());
        assertEquals(List.of(), photoIdsOf(album));
        assertEquals(Set.of(), storage.storedIds());
    }

    @Test
    @DisplayName("A-11: Scheitert auch das Zurücknehmen, meldet die App das Aufräumen beim nächsten Start")
    void reportsCleanupWhenRollbackFails() {
        Album album = savedAlbum();
        albums.failOnSave = true;
        storage.failOnDelete = true;

        Result<PhotoSummary> result = useCase.execute(album.getId().value(), file);

        assertEquals(AddPhotoUseCase.CLEANUP_ON_NEXT_START, result.getMessage());
        assertEquals(List.of(), photoIdsOf(album));
        assertEquals(1, storage.storedIds().size());
    }

    @Test
    @DisplayName("ADR-002: Scheitert das Anlegen der Kopie, bleibt das Album unverändert")
    void keepsAlbumWhenCopyCannotBeStored() {
        Album album = savedAlbum();
        storage.failOnStore = true;

        Result<PhotoSummary> result = useCase.execute(album.getId().value(), file);

        assertEquals(AddPhotoUseCase.SAVE_FAILED, result.getMessage());
        assertEquals(List.of(), photoIdsOf(album));
    }

    @Test
    @DisplayName("Ein unbekanntes Album wird mit Meldung abgelehnt")
    void reportsUnknownAlbum() {
        Result<PhotoSummary> result = useCase.execute(UUID.randomUUID(), file);

        assertEquals("Das Album wurde nicht gefunden.", result.getMessage());
        assertEquals(Set.of(), storage.storedIds());
    }

    @Test
    @DisplayName("Ein Lesefehler beim Laden des Albums wird gemeldet")
    void reportsAlbumLoadFailure() {
        Album album = savedAlbum();
        albums.failOnLoad = true;

        Result<PhotoSummary> result = useCase.execute(album.getId().value(), file);

        assertEquals("Das Album konnte nicht geladen werden.", result.getMessage());
        assertEquals(Set.of(), storage.storedIds());
    }

    private Album savedAlbum() {
        Album album = new Album("Urlaub");
        albums.save(album);
        return album;
    }

    private List<UUID> photoIdsOf(Album album) {
        return albums.findById(album.getId()).orElseThrow().getPhotos().stream()
                .map(photo -> photo.getId().value())
                .toList();
    }
}
