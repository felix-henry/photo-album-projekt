package de.dhbw.photoalbum.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Album mit seinen Fotos in der Reihenfolge des Hinzufügens (A-11). Invarianten: kein zweites Foto
 * mit gleichem {@link ContentHash} (FA-07, AE-03, A-01), höchstens {@link #MAX_PHOTOS} Fotos (FA-07, AE-01).
 * {@code removePhoto} und {@code rename} folgen in Block 4.
 */
public class Album {

    public static final int MAX_PHOTOS = 100;

    private final AlbumId id;
    private final AlbumName name;
    private final List<Photo> photos;

    /** Neues, leeres Album (UC-02). */
    public Album(AlbumId id, AlbumName name) {
        this(id, name, List.of());
    }

    /** Stellt ein gespeichertes Album wieder her; verletzte Invarianten werden abgelehnt (NFA-02). */
    public Album(AlbumId id, AlbumName name, List<Photo> photos) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        List<Photo> given = List.copyOf(photos);
        Set<ContentHash> hashes = new HashSet<>();
        for (Photo photo : given) {
            if (!hashes.add(photo.hash())) {
                throw new DuplicatePhotoException();
            }
        }
        if (given.size() > MAX_PHOTOS) {
            throw new AlbumFullException();
        }
        this.photos = new ArrayList<>(given);
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

    public int photoCount() {
        return photos.size();
    }
}
