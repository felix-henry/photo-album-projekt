package de.commitmentissues.photoalbum.application;

import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.Photo;

/**
 * Test Design Lab, Verfahren 2: Entscheidungstabelle für UC-01 „Foto hinzufügen“.
 * Treffen mehrere Ablehnungsgründe zu, meldet das System nur den ersten (UC-01, Schritt 4).
 * Die Tabelle steht in TESTING.md, Abschnitt 3.2.
 */
class AddPhotoDecisionTableTest {

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final FakeImageHasher hasher = new FakeImageHasher();
    private final InMemoryPhotoStorage storage = new InMemoryPhotoStorage();
    private final AddPhotoUseCase useCase = new AddPhotoUseCase(albums, hasher, storage);
    private final Path file = Path.of("strand.jpg");

    @Test
    @Tag("fehlerfall")
    @DisplayName("AK-05.2: Ungültige Datei und volles Album, gemeldet wird die leere Datei (Regel R3)")
    void reportsInvalidFileBeforeFullAlbum() {
        Album album = savedAlbumWith(Album.MAX_PHOTOS);
        hasher.rejects(file, ImageProblem.EMPTY);

        Result<PhotoSummary> result = useCase.execute(album.getId().value(), file);

        assertEquals("Die Bilddatei ist leer.", result.getMessage());
        assertEquals(Album.MAX_PHOTOS, photoCountOf(album));
        assertEquals(Set.of(), storage.storedIds());
    }

    @Test
    @Tag("grenzfall")
    @DisplayName("AK-06.3: Duplikat und volles Album, gemeldet wird das Duplikat (Regel R6)")
    void reportsDuplicateBeforeFullAlbum() {
        Album album = savedAlbumWith(Album.MAX_PHOTOS);
        hasher.returns(file, "hash-1");

        Result<PhotoSummary> result = useCase.execute(album.getId().value(), file);

        assertEquals("Dieses Bild befindet sich schon in diesem Album", result.getMessage());
        assertEquals(Album.MAX_PHOTOS, photoCountOf(album));
        assertEquals(Set.of(), storage.storedIds());
    }

    private Album savedAlbumWith(int photoCount) {
        Album album = new Album("Urlaub");
        for (int i = 1; i <= photoCount; i++) {
            album.addPhoto(new Photo("hash-" + i));
        }
        albums.save(album);
        return album;
    }

    private int photoCountOf(Album album) {
        return albums.findById(album.getId()).orElseThrow().getPhotos().size();
    }
}
