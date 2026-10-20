package de.dhbw.photoalbum.domain;

import java.util.Objects;
import java.util.UUID;

/** Identität eines Fotos; zugleich Schlüssel seiner Bildkopie im Datenordner (A-08). */
public record PhotoId(UUID value) {

    public PhotoId {
        Objects.requireNonNull(value, "value");
    }

    public static PhotoId newId() {
        return new PhotoId(UUID.randomUUID());
    }

    /** Liest eine UUID in Standardschreibweise; ungültiges Format: {@link IllegalArgumentException}. */
    public static PhotoId of(String value) {
        UUID uuid = UUID.fromString(value);
        if (!uuid.toString().equalsIgnoreCase(value)) {
            throw new IllegalArgumentException("Not a canonical UUID: " + value);
        }
        return new PhotoId(uuid);
    }
}
