package de.dhbw.photoalbum.domain;

/** Das Album enthält bereits {@link Album#MAX_PHOTOS} Fotos. */
public class AlbumFullException extends DomainException {

    public AlbumFullException() {
        super("Album already contains " + Album.MAX_PHOTOS + " photos");
    }
}
