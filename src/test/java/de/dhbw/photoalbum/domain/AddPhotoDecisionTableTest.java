package de.dhbw.photoalbum.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
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

    @ParameterizedTest
    @ValueSource(ints = {1, Album.MAX_PHOTOS})
    @Tag("fehler")
    void r2_rejectsDuplicate_andLeavesAlbumUnchanged(int count) {       // AK-07.2, AK-07.6
        Album album = albumWith(count);
        Photo copy = photo(1, "kopie.jpg");
        assertThrows(DuplicatePhotoException.class, () -> album.addPhoto(copy));
        assertEquals(count, album.photoCount());
    }

    @Test
    @Tag("grenzfall")
    void r3_rejectsPhotoWhenAlbumFull_andLeavesAlbumUnchanged() {       // AK-07.5
        Album album = albumWith(Album.MAX_PHOTOS);
        assertThrows(AlbumFullException.class, () -> album.addPhoto(photo(Album.MAX_PHOTOS + 1, "neu.jpg")));
        assertEquals(Album.MAX_PHOTOS, album.photoCount());
    }

    @Test
    @Tag("grenzfall")
    void r4_addsPhotoUpToMax() {                                         // AK-07.4
        Album album = albumWith(Album.MAX_PHOTOS - 1);
        Photo last = photo(Album.MAX_PHOTOS, "neu.jpg");
        album.addPhoto(last);
        assertEquals(Album.MAX_PHOTOS, album.photoCount());
        assertEquals(last, album.photos().get(Album.MAX_PHOTOS - 1));
    }

    private static Album albumWith(int count) {
        List<Photo> photos = IntStream.rangeClosed(1, count).mapToObj(i -> photo(i, "foto" + i + ".jpg")).toList();
        return new Album(AlbumId.newId(), new AlbumName("Urlaub"), photos);
    }

    private static Photo photo(int n, String fileName) {
        return new Photo(PhotoId.newId(), hash(n), fileName, ImageType.JPEG, 1024);
    }

    private static ContentHash hash(int n) {
        return new ContentHash("%064x".formatted(n));
    }
}
