package de.dhbw.photoalbum.domain;

/** Das Album enthält bereits ein Foto mit gleichem Dateiinhalt (FA-07, AE-03, A-01). */
public class DuplicatePhotoException extends DomainException {

    public DuplicatePhotoException() {
        super("Album already contains a photo with the same content hash");
    }
}
