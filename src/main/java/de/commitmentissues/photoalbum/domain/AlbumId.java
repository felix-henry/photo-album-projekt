package de.commitmentissues.photoalbum.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Eindeutige Kennung eines Albums.
 */
public record AlbumId(UUID value) {

    public AlbumId {
        Objects.requireNonNull(value, "value");
    }

    public static AlbumId newId() {
        return new AlbumId(UUID.randomUUID());
    }
}
