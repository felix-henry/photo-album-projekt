package de.dhbw.photoalbum.domain;

import java.util.Objects;

/** Albumname abgelehnt (FA-02); der Grund bestimmt die Meldung (UC-02 4a bis 4c). */
public class InvalidAlbumNameException extends DomainException {

    public enum Reason { EMPTY, TOO_LONG, DUPLICATE }

    private final Reason reason;

    public InvalidAlbumNameException(Reason reason) {
        super("Album name rejected: " + Objects.requireNonNull(reason, "reason"));
        this.reason = reason;
    }

    public Reason reason() {
        return reason;
    }
}
