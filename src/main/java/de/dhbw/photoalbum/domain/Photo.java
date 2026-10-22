package de.dhbw.photoalbum.domain;

import de.dhbw.photoalbum.domain.TextTooLongException.Field;
import java.text.Normalizer;
import java.util.Objects;

/** Foto mit optionalem Titel und optionaler Beschreibung begrenzter Länge. */
public record Photo(PhotoId id, String title, String description) {

    public static final int MAX_TITLE_LENGTH = 100;
    public static final int MAX_DESCRIPTION_LENGTH = 500;

    public Photo {
        Objects.requireNonNull(id, "id");
        title = checkedText(title, MAX_TITLE_LENGTH, Field.TITLE);
        description = checkedText(description, MAX_DESCRIPTION_LENGTH, Field.DESCRIPTION);
    }

    /** {@code null} wird leer; gezählt werden Codepoints nach NFC. */
    private static String checkedText(String text, int maxLength, Field field) {
        String normalized = text == null ? "" : Normalizer.normalize(text, Normalizer.Form.NFC);
        if (normalized.codePointCount(0, normalized.length()) > maxLength) {
            throw new TextTooLongException(field);
        }
        return normalized;
    }
}
