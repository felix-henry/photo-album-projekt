package de.dhbw.photoalbum.domain;

import java.util.Objects;
import java.util.UUID;

/** Identität eines Albums. */
public record AlbumId(UUID value) {

    public AlbumId {
        Objects.requireNonNull(value, "value");
    }

    public static AlbumId newId() {
        return new AlbumId(UUID.randomUUID());
    }
}
