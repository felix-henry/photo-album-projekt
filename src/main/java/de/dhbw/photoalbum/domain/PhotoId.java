package de.dhbw.photoalbum.domain;

import java.util.Objects;
import java.util.UUID;

/** Identität eines Fotos. */
public record PhotoId(UUID value) {

    public PhotoId {
        Objects.requireNonNull(value, "value");
    }

    public static PhotoId newId() {
        return new PhotoId(UUID.randomUUID());
    }
}
