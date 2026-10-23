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

    /** Fügt ein Foto hinzu; prüft zuerst das Duplikat, dann die Anzahl (FA-07, AK-07.6). */
    public void addPhoto(Photo photo) {
        Objects.requireNonNull(photo, "photo");
        if (photos.stream().anyMatch(p -> p.hash().equals(photo.hash()))) {
            throw new DuplicatePhotoException();
        }
        if (photos.size() >= MAX_PHOTOS) {
            throw new AlbumFullException();
        }
        photos.add(photo);
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
