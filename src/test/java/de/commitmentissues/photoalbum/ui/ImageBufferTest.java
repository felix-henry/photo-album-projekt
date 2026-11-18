package de.commitmentissues.photoalbum.ui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.application.Result;

class ImageBufferTest {

    private final Map<UUID, Integer> loads = new HashMap<>();
    private final Set<UUID> unreadable = new HashSet<>();
    private final ImageBuffer buffer = new ImageBuffer(this::load);

    @Test
    @DisplayName("ARCHITECTURE 5: Beim Öffnen wird zuerst nur das erste Bild geladen")
    void showLoadsOnlyCurrentImage() {
        List<UUID> photos = photos(10);

        Result<BufferedImage> result = buffer.show(photos, 0);

        assertTrue(result.isSuccess());
        assertEquals(1, buffer.size());
    }

    @Test
    @DisplayName("ARCHITECTURE 5: Danach werden die Nachbarn nachgeladen")
    void preloadsNeighbours() {
        List<UUID> photos = photos(10);
        buffer.show(photos, 4);

        buffer.preloadNeighbours(photos, 4);

        assertEquals(5, buffer.size());
    }

    @Test
    @DisplayName("AK-N03.2: Beim Durchblättern von 100 Fotos liegen nie mehr als 5 Bilder im Puffer")
    void neverHoldsMoreThanCapacity() {
        List<UUID> photos = photos(100);

        for (int index = 0; index < photos.size(); index++) {
            buffer.show(photos, index);
            buffer.preloadNeighbours(photos, index);

            assertTrue(buffer.size() <= ImageBuffer.CAPACITY, "Puffer bei Foto " + index + ": " + buffer.size());
        }
    }

    @Test
    @DisplayName("AK-N03.2: Ein schon gepuffertes Bild wird beim Blättern nicht erneut geladen")
    void doesNotReloadBufferedImage() {
        List<UUID> photos = photos(10);
        buffer.show(photos, 0);
        buffer.preloadNeighbours(photos, 0);

        buffer.show(photos, 1);

        assertEquals(1, loads.get(photos.get(1)));
    }

    @Test
    @DisplayName("AK-N03.2: Weit entfernte Bilder werden freigegeben und bei Bedarf neu geladen")
    void releasesDistantImages() {
        List<UUID> photos = photos(10);
        buffer.show(photos, 0);

        buffer.show(photos, 9);
        buffer.show(photos, 0);

        assertEquals(2, loads.get(photos.get(0)));
    }

    @Test
    @DisplayName("AK-08.3: Ein nicht lesbares Bild wird gemeldet, nicht gepuffert und später erneut versucht")
    void reportsUnreadableImageAndRetries() {
        List<UUID> photos = photos(3);
        unreadable.add(photos.get(0));

        Result<BufferedImage> first = buffer.show(photos, 0);
        buffer.show(photos, 0);

        assertFalse(first.isSuccess());
        assertEquals(0, buffer.size());
        assertEquals(2, loads.get(photos.get(0)));
    }

    private Result<BufferedImage> load(UUID id) {
        loads.merge(id, 1, Integer::sum);
        if (unreadable.contains(id)) {
            return Result.failure("Das Foto konnte nicht gelesen werden.");
        }
        return Result.success(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
    }

    private static List<UUID> photos(int count) {
        List<UUID> photos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            photos.add(UUID.randomUUID());
        }
        return photos;
    }
}
