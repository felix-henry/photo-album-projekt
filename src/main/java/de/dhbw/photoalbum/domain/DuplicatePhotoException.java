package de.dhbw.photoalbum.domain;

/** Das Album enthält bereits ein Foto mit gleichem Dateiinhalt. */
public class DuplicatePhotoException extends DomainException {

    public DuplicatePhotoException() {
        super("Album already contains a photo with the same content hash");
    }
}
