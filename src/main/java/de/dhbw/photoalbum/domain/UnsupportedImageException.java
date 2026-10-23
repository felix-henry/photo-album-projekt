package de.dhbw.photoalbum.domain;

/** Kein JPEG/PNG oder größer als {@link Photo#MAX_FILE_SIZE_BYTES}. */
public class UnsupportedImageException extends DomainException {

    public UnsupportedImageException() {
        super("Only JPEG or PNG up to " + Photo.MAX_FILE_SIZE_BYTES + " bytes allowed");
    }
}
