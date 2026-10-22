package de.dhbw.photoalbum.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Album mit seinen Fotos in der Reihenfolge des Hinzufügens. */
public class Album {

    public static final int MAX_PHOTOS = 10_000;

    private final AlbumId id;
    private final AlbumName name;
    private final List<Photo> photos = new ArrayList<>();

    public Album(AlbumId id, AlbumName name) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
    }

    public AlbumId id() {
        return id;
    }

    public AlbumName name() {
        return name;
    }

    public List<Photo> photos() {
        return List.copyOf(photos);
    }
}
