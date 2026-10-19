package de.dhbw.photoalbum.domain;

/** Fachliche Exception: Albumname ist leer oder zu lang (FA-01). */
public class InvalidAlbumNameException extends RuntimeException {

    public InvalidAlbumNameException(String message) {
        super(message);
    }
}
