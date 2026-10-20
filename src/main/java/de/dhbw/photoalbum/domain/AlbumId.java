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

    /** Liest eine UUID in Standardschreibweise; ungültiges Format: {@link IllegalArgumentException}. */
    public static AlbumId of(String value) {
        UUID uuid = UUID.fromString(value);
        if (!uuid.toString().equalsIgnoreCase(value)) {
            throw new IllegalArgumentException("Not a canonical UUID: " + value);
        }
        return new AlbumId(uuid);
    }
}
