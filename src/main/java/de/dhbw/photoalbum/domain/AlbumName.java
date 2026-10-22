package de.dhbw.photoalbum.domain;

import de.dhbw.photoalbum.domain.InvalidAlbumNameException.Reason;
import java.text.Normalizer;

/** Albumname ohne Leerzeichen am Rand, in NFC, 1 bis {@link #MAX_LENGTH} Codepoints; {@code null} gilt als leer. */
public record AlbumName(String value) {

    public static final int MAX_LENGTH = 50;

    public AlbumName {
        String normalized = value == null ? "" : Normalizer.normalize(value.strip(), Normalizer.Form.NFC);
        int length = normalized.codePointCount(0, normalized.length());
        if (length == 0) {
            throw new InvalidAlbumNameException(Reason.EMPTY);
        }
        if (length > MAX_LENGTH) {
            throw new InvalidAlbumNameException(Reason.TOO_LONG);
        }
        value = normalized;
    }
}
