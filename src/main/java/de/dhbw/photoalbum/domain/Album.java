package de.dhbw.photoalbum.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Album mit seinen Fotos; prüft alle Album-Geschäftsregeln (B3 F13, NFA-03).
 * Werte sind vorläufig: AE-01 (100 Fotos), A-01 (Name 1–50 Zeichen).
 */
public class Album {

    public static final int MAX_PHOTOS = 100;
    public static final int MAX_NAME_LENGTH = 50;

    private final AlbumId id;
    private String name;
    private final List<Photo> photos = new ArrayList<>();

    public Album(AlbumId id, String name) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = checkName(name);
    }

    public void addPhoto(Photo photo) {
        Objects.requireNonNull(photo, "photo");
        if (photos.size() >= MAX_PHOTOS) {
            throw new AlbumFullException("Album '" + name + "' enthält bereits " + MAX_PHOTOS + " Fotos.");
        }
        boolean duplicate = photos.stream().anyMatch(p -> p.hash().equals(photo.hash()));
        if (duplicate) {
            throw new DuplicatePhotoException("Dieses Foto ist bereits im Album '" + name + "'.");
        }
        photos.add(photo);
    }

    public void removePhoto(PhotoId photoId) {
        photos.removeIf(p -> p.id().equals(photoId));
    }

    public void rename(String newName) {
        this.name = checkName(newName);
    }

    public AlbumId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public List<Photo> photos() {
        return List.copyOf(photos);
    }

    public List<String> storageKeys() {
        return photos.stream().map(Photo::storageKey).toList();
    }

    private static String checkName(String candidate) {
        if (candidate == null || candidate.isBlank() || candidate.length() > MAX_NAME_LENGTH) {
            throw new InvalidAlbumNameException(
                    "Albumname darf nicht leer sein und höchstens " + MAX_NAME_LENGTH + " Zeichen haben.");
        }
        return candidate;
    }
}
