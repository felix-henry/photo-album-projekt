package de.commitmentissues.photoalbum.domain;

import java.util.Objects;

/**
 * Ein Fotoalbum mit Namen (A-02). Fotos folgen mit UC-01.
 */
public class Album {

    static final int MAX_NAME_LENGTH = 50;

    private final AlbumId id;
    private String name;

    public Album(String name) {
        this(AlbumId.newId(), name);
    }

    private Album(AlbumId id, String name) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = checkName(name);
    }

    /**
     * Stellt ein gespeichertes Album mit seiner bisherigen ID wieder her.
     * Die Namensregel gilt auch hier.
     */
    public static Album restore(AlbumId id, String name) {
        return new Album(id, name);
    }

    public AlbumId getId() {
        return id;
    }

    public String getName() {
        return name;
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
