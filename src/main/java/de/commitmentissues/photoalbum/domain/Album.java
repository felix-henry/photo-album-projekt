package de.commitmentissues.photoalbum.domain;

import java.util.Objects;

/**
 * Ein Fotoalbum mit Namen (A-02). Fotos folgen mit UC-01.
 */
public class Album {

    static final int MAX_NAME_LENGTH = 50;

    private final AlbumId id;
    private final String name;

    public Album(String name) {
        this.id = AlbumId.newId();
        this.name = checkName(name);
    }

    public AlbumId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private static String checkName(String name) {
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
