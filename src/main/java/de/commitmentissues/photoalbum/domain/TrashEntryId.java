package de.commitmentissues.photoalbum.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Eindeutige Kennung eines Papierkorbeintrags.
 */
public record TrashEntryId(UUID value) {

    public TrashEntryId {
        Objects.requireNonNull(value, "value");
    }

    public static TrashEntryId newId() {
        return new TrashEntryId(UUID.randomUUID());
    }
}
