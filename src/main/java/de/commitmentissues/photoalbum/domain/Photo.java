package de.commitmentissues.photoalbum.domain;

import java.util.Objects;

/**
 * Ein Foto in einem Album. Der Inhaltshash erkennt bytegleiche Bilder (A-01).
 * Titel und Beschreibung folgen mit FA-10.
 */
public class Photo {

    private final PhotoId id;
    private final String contentHash;

    public Photo(String contentHash) {
        this(PhotoId.newId(), contentHash);
    }

    private Photo(PhotoId id, String contentHash) {
        this.id = Objects.requireNonNull(id, "id");
        this.contentHash = Objects.requireNonNull(contentHash, "contentHash");
        if (contentHash.isBlank()) {
            throw new IllegalArgumentException("Der Inhaltshash darf nicht leer sein.");
        }
    }

    /**
     * Stellt ein gespeichertes Foto mit seiner bisherigen ID wieder her.
     */
    public static Photo restore(PhotoId id, String contentHash) {
        return new Photo(id, contentHash);
    }

    public PhotoId getId() {
        return id;
    }

    public String getContentHash() {
        return contentHash;
    }
}
