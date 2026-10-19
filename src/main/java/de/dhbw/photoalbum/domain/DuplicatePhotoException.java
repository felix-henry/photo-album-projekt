package de.dhbw.photoalbum.domain;

/** Fachliche Exception: Ein identisches Foto ist bereits im Album (FA-05). */
public class DuplicatePhotoException extends RuntimeException {

    public DuplicatePhotoException(String message) {
        super(message);
    }
}
