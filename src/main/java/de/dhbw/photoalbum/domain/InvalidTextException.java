package de.dhbw.photoalbum.domain;

/** Fachliche Exception: Titel oder Beschreibung ist zu lang (FA-07). */
public class InvalidTextException extends RuntimeException {

    public InvalidTextException(String message) {
        super(message);
    }
}
