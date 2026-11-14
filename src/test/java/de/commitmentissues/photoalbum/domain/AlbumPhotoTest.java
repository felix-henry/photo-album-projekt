package de.commitmentissues.photoalbum.domain;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AlbumPhotoTest {

    @Test
    @DisplayName("AK-05.1: Ein Foto wird einem Album mit freiem Platz hinzugefügt")
    void addsPhotoToAlbumWithSpace() {
        Album album = new Album("Urlaub");
        Photo photo = new Photo("hash-strand");

        album.addPhoto(photo);

        assertEquals(List.of(photo), album.getPhotos());
    }

    @Test
    @DisplayName("AK-06.1: Ein bytegleiches Foto wird abgelehnt, das vorhandene bleibt unverändert")
    void rejectsDuplicateContent() {
        Album album = new Album("Urlaub");
        Photo original = new Photo("hash-strand");
        album.addPhoto(original);

        DomainException exception = assertThrows(DomainException.class,
                () -> album.addPhoto(new Photo("hash-strand")));

        assertEquals(Violation.PHOTO_DUPLICATE, exception.getViolation());
        assertEquals(List.of(original), album.getPhotos());
    }

    @Test
    @DisplayName("AK-06.2: Ein Foto mit anderem Inhalt wird als eigenes Foto aufgenommen")
    void acceptsDifferentContent() {
        Album album = new Album("Urlaub");
        album.addPhoto(new Photo("hash-strand"));

        album.addPhoto(new Photo("hash-strand-export"));

        assertEquals(2, album.getPhotos().size());
    }

    @Test
    @DisplayName("AK-07.1: Das 100. Foto wird noch aufgenommen")
    void acceptsHundredthPhoto() {
        Album album = albumWithPhotos(99);

        album.addPhoto(new Photo("hash-neu"));

        assertEquals(100, album.getPhotos().size());
    }

    @Test
    @DisplayName("AK-07.2: Das 101. Foto wird abgelehnt, das Album behält seine 100 Fotos")
    void rejectsHundredFirstPhoto() {
        Album album = albumWithPhotos(100);
        List<Photo> before = album.getPhotos();

        DomainException exception = assertThrows(DomainException.class,
                () -> album.addPhoto(new Photo("hash-neu")));

        assertEquals(Violation.ALBUM_FULL, exception.getViolation());
        assertEquals(before, album.getPhotos());
    }

    @Test
    @DisplayName("AK-06.3: Bei vollem Album wird ein Duplikat als Duplikat gemeldet")
    void reportsDuplicateBeforeFullAlbum() {
        Album album = albumWithPhotos(100);

        DomainException exception = assertThrows(DomainException.class,
                () -> album.addPhoto(new Photo("hash-1")));

        assertEquals(Violation.PHOTO_DUPLICATE, exception.getViolation());
    }

    @Test
    @DisplayName("NFA-02: Ein gespeichertes Album behält beim Wiederherstellen seine Fotos")
    void restoreKeepsPhotos() {
        Photo photo = Photo.restore(PhotoId.newId(), "hash-strand");

        Album album = Album.restore(AlbumId.newId(), "Urlaub", List.of(photo));

        assertEquals(List.of(photo), album.getPhotos());
    }

    @Test
    @DisplayName("A-01: Auch beim Wiederherstellen werden bytegleiche Fotos abgelehnt")
    void restoreRejectsDuplicatePhotos() {
        List<Photo> photos = List.of(new Photo("hash-strand"), new Photo("hash-strand"));
        AlbumId id = AlbumId.newId();

        DomainException exception = assertThrows(DomainException.class,
                () -> Album.restore(id, "Urlaub", photos));

        assertEquals(Violation.PHOTO_DUPLICATE, exception.getViolation());
    }

    @Test
    @DisplayName("FA-09: Ein entferntes Foto verlässt das Album, die übrigen bleiben in ihrer Reihenfolge")
    void removesOnlyThatPhoto() {
        Album album = albumWithPhotos(3);
        List<Photo> before = album.getPhotos();

        album.removePhoto(before.get(1).getId());

        assertEquals(List.of(before.get(0), before.get(2)), album.getPhotos());
    }

    @Test
    @DisplayName("Ein Foto, das nicht im Album liegt, lässt sich nicht entfernen")
    void rejectsRemovingUnknownPhoto() {
        Album album = albumWithPhotos(1);
        PhotoId unknown = PhotoId.newId();

        assertThrows(IllegalArgumentException.class, () -> album.removePhoto(unknown));
    }

    private static Album albumWithPhotos(int count) {
        Album album = new Album("Klassenfahrt");
        for (int i = 1; i <= count; i++) {
            album.addPhoto(new Photo("hash-" + i));
        }
        return album;
    }
}
