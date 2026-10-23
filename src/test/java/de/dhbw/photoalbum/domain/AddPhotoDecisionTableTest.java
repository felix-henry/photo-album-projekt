package de.dhbw.photoalbum.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AddPhotoDecisionTableTest {

    @Test
    @Tag("fehler")
    void r1_rejectsUnsupportedImage() {                                  // AK-06.4
        assertThrows(UnsupportedImageException.class,
                () -> new Photo(PhotoId.newId(), hash(1), "dokument.pdf", ImageType.OTHER, 1024));
    }

    @Test
    @Tag("grenzfall")
    void b1_acceptsFileOfExactlyMaxSize() {                              // AK-06.2
        Album album = albumWith(0);
        album.addPhoto(new Photo(PhotoId.newId(), hash(1), "gross.png", ImageType.PNG, Photo.MAX_FILE_SIZE_BYTES));
        assertEquals(1, album.photos().size());
    }

    @Test
    @Tag("grenzfall")
    void b1_rejectsFileOneByteAboveMaxSize() {                           // AK-06.3
        assertThrows(UnsupportedImageException.class,
                () -> new Photo(PhotoId.newId(), hash(1), "zu_gross.jpg", ImageType.JPEG, Photo.MAX_FILE_SIZE_BYTES + 1));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, Album.MAX_PHOTOS})
    @Tag("fehler")
    void r2_rejectsDuplicate_andLeavesAlbumUnchanged(int count) {       // AK-07.2, AK-07.6
        Album album = albumWith(count);
        Photo copy = photo(1, "kopie.jpg");
        assertThrows(DuplicatePhotoException.class, () -> album.addPhoto(copy));
        assertEquals(count, album.photos().size());
    }

    @Test
    @Tag("grenzfall")
    void r3_rejectsPhotoWhenAlbumFull_andLeavesAlbumUnchanged() {       // AK-07.5
        Album album = albumWith(Album.MAX_PHOTOS);
        assertThrows(AlbumFullException.class, () -> album.addPhoto(photo(Album.MAX_PHOTOS + 1, "neu.jpg")));
        assertEquals(Album.MAX_PHOTOS, album.photos().size());
    }

    @Test
    @Tag("grenzfall")
    void r4_addsPhotoUpToMax() {                                         // AK-07.4
        Album album = albumWith(Album.MAX_PHOTOS - 1);
        Photo last = photo(Album.MAX_PHOTOS, "neu.jpg");
        album.addPhoto(last);
        assertEquals(Album.MAX_PHOTOS, album.photos().size());
        assertEquals(last, album.photos().get(Album.MAX_PHOTOS - 1));
    }

    private static Album albumWith(int count) {
        Album album = new Album(AlbumId.newId(), new AlbumName("Urlaub"));
        IntStream.rangeClosed(1, count).forEach(i -> album.addPhoto(photo(i, "foto" + i + ".jpg")));
        return album;
    }

    private static Photo photo(int n, String fileName) {
        return new Photo(PhotoId.newId(), hash(n), fileName, ImageType.JPEG, 1024);
    }

    private static ContentHash hash(int n) {
        return new ContentHash("%064x".formatted(n));
    }
}
