package de.commitmentissues.photoalbum.application;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.PhotoId;

class LoadPhotoUseCaseTest {

    private final InMemoryPhotoStorage photos = new InMemoryPhotoStorage();
    private final LoadPhotoUseCase useCase = new LoadPhotoUseCase(photos);

    @Test
    @DisplayName("AK-08.1: Die gespeicherten Bilddaten eines Fotos werden geladen")
    void loadsStoredImage() {
        PhotoId id = PhotoId.newId();
        photos.put(id, new byte[] {1, 2, 3});

        Result<byte[]> result = useCase.execute(id.value());

        assertArrayEquals(new byte[] {1, 2, 3}, result.getValue());
    }

    @Test
    @DisplayName("AK-08.3: Nicht lesbare Bilddaten werden gemeldet, die Kopie bleibt gespeichert")
    void reportsUnreadableImage() {
        PhotoId id = PhotoId.newId();
        photos.put(id, new byte[] {1, 2, 3});
        photos.failOnLoad = true;

        Result<byte[]> result = useCase.execute(id.value());

        assertEquals("Das Foto konnte nicht gelesen werden.", result.getMessage());
        assertEquals(Set.of(id), photos.storedIds());
    }
}
