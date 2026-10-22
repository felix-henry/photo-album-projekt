package de.dhbw.photoalbum.domain;

import java.util.Objects;

/** Albumname abgelehnt; der Grund bestimmt die Meldung. */
public class InvalidAlbumNameException extends DomainException {

    public enum Reason { EMPTY, TOO_LONG }

    private final Reason reason;

    public InvalidAlbumNameException(Reason reason) {
        super("Album name rejected: " + Objects.requireNonNull(reason, "reason"));
        this.reason = reason;
    }

    public Reason reason() {
        return reason;
    }
}
