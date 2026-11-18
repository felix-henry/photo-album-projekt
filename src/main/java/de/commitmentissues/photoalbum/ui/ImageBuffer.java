package de.commitmentissues.photoalbum.ui;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import de.commitmentissues.photoalbum.application.Result;

/**
 * Hält für die Einzelansicht höchstens 5 Bilder im Speicher: das angezeigte und je zwei davor
 * und danach (NFA-03, A-06, ARCHITECTURE, Abschnitt 5). Bilder außerhalb dieses Fensters gibt
 * er beim Blättern frei.
 */
class ImageBuffer {

    static final int CAPACITY = 5;
    private static final int NEIGHBOURS = (CAPACITY - 1) / 2;

    private final Function<UUID, Result<BufferedImage>> loader;
    private final Map<UUID, BufferedImage> images = new HashMap<>();

    ImageBuffer(Function<UUID, Result<BufferedImage>> loader) {
        this.loader = loader;
    }

    /**
     * Liefert das Bild an Position {@code index} und gibt alle Bilder frei, die mehr als zwei
     * Positionen davon entfernt sind.
     */
    Result<BufferedImage> show(List<UUID> photos, int index) {
        images.keySet().retainAll(window(photos, index));
        return load(photos.get(index));
    }

    /**
     * Lädt die Nachbarn des angezeigten Fotos nach. Ein Fehler wird erst gemeldet, wenn der
     * Nutzer zu diesem Foto blättert.
     */
    void preloadNeighbours(List<UUID> photos, int index) {
        for (UUID id : window(photos, index)) {
            load(id);
        }
    }

    int size() {
        return images.size();
    }

    private Result<BufferedImage> load(UUID id) {
        BufferedImage buffered = images.get(id);
        if (buffered != null) {
            return Result.success(buffered);
        }
        Result<BufferedImage> result = loader.apply(id);
        if (result.isSuccess()) {
            images.put(id, result.getValue());
        }
        return result;
    }

    private static List<UUID> window(List<UUID> photos, int index) {
        int from = Math.max(0, index - NEIGHBOURS);
        int to = Math.min(photos.size(), index + NEIGHBOURS + 1);
        return photos.subList(from, to);
    }
}
