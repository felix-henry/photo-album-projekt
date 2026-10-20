package de.dhbw.photoalbum.domain;

/** Kein JPEG/PNG oder größer als {@link Photo#MAX_FILE_SIZE_BYTES} (FA-06, A-03, A-04). */
public class UnsupportedImageException extends DomainException {

    public UnsupportedImageException() {
        super("Only JPEG or PNG up to " + Photo.MAX_FILE_SIZE_BYTES + " bytes allowed");
    }
}
