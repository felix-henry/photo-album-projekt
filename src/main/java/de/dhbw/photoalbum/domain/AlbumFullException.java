package de.dhbw.photoalbum.domain;

/** Das Album hätte mehr als {@link Album#MAX_PHOTOS} Fotos (FA-07, AE-01). */
public class AlbumFullException extends DomainException {

    public AlbumFullException() {
        super("Album would exceed " + Album.MAX_PHOTOS + " photos");
    }
}
