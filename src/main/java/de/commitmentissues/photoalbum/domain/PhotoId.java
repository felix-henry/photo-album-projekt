package de.commitmentissues.photoalbum.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Eindeutige Kennung eines Fotos. Unter dieser ID liegt auch seine App-Kopie (A-05).
 */
public record PhotoId(UUID value) {

    public PhotoId {
        Objects.requireNonNull(value, "value");
    }

    public static PhotoId newId() {
        return new PhotoId(UUID.randomUUID());
    }
}
