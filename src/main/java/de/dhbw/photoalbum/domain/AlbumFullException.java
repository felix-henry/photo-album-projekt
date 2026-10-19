package de.dhbw.photoalbum.domain;

/** Fachliche Exception: Album enthält bereits die Höchstzahl an Fotos (FA-06). */
public class AlbumFullException extends RuntimeException {

    public AlbumFullException(String message) {
        super(message);
    }
}
