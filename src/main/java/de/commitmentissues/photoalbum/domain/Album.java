package de.commitmentissues.photoalbum.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Ein Fotoalbum mit Namen (A-02) und höchstens 100 Fotos ohne bytegleiche Duplikate (FA-06, FA-07).
 */
public class Album {

    public static final int MAX_PHOTOS = 100;
    static final int MAX_NAME_LENGTH = 50;

    private final AlbumId id;
    private String name;
    private final List<Photo> photos = new ArrayList<>();

    public Album(String name) {
        this(AlbumId.newId(), name);
    }

    private Album(AlbumId id, String name) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = checkName(name);
    }

    /**
     * Stellt ein gespeichertes Album ohne Fotos mit seiner bisherigen ID wieder her.
     */
    public static Album restore(AlbumId id, String name) {
        return restore(id, name, List.of());
    }

    /**
     * Stellt ein gespeichertes Album mit seiner bisherigen ID und seinen Fotos wieder her.
     * Namensregel, Duplikatverbot und Höchstzahl gelten auch hier.
     */
    public static Album restore(AlbumId id, String name, List<Photo> photos) {
        Album album = new Album(id, name);
        photos.forEach(album::addPhoto);
        return album;
    }

    public AlbumId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Photo> getPhotos() {
        return List.copyOf(photos);
    }

    /**
     * Nimmt ein Foto auf. Lehnt zuerst ein bytegleiches Duplikat ab (A-01),
     * danach ein volles Album (FA-07), wie die Reihenfolge in UC-01 es vorgibt.
     */
    public void addPhoto(Photo photo) {
        Objects.requireNonNull(photo, "photo");
        boolean duplicate = photos.stream()
                .anyMatch(existing -> existing.getContentHash().equals(photo.getContentHash()));
        if (duplicate) {
            throw new DomainException(Violation.PHOTO_DUPLICATE);
        }
        if (photos.size() >= MAX_PHOTOS) {
            throw new DomainException(Violation.ALBUM_FULL);
        }
        photos.add(photo);
    }

    /**
     * Nimmt ein Foto aus dem Album (FA-09) und gibt es zurück.
     */
    public Photo removePhoto(PhotoId id) {
        Photo photo = photos.stream()
                .filter(candidate -> candidate.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Das Foto gehört nicht zu diesem Album: " + id));
        photos.remove(photo);
        return photo;
    }

    /**
     * Nur über {@link AlbumLibrary#rename} aufrufbar, damit die Eindeutigkeit geprüft wird (A-02).
     */
    void rename(String newName) {
        this.name = checkName(newName);
    }

    static String checkName(String name) {
        Objects.requireNonNull(name, "name");
        String stripped = name.strip();
        int length = TextLength.count(stripped);
        if (length == 0) {
            throw new DomainException(Violation.ALBUM_NAME_EMPTY);
        }
        if (length > MAX_NAME_LENGTH) {
            throw new DomainException(Violation.ALBUM_NAME_TOO_LONG);
        }
        return stripped;
    }
}
