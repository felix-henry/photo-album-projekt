package de.dhbw.photoalbum.domain;

import de.dhbw.photoalbum.domain.TextTooLongException.Field;
import java.text.Normalizer;
import java.util.Objects;

/**
 * Foto mit Bilddaten und optionalem Titel und optionaler Beschreibung begrenzter Länge.
 * {@code hash} entscheidet über Inhaltsgleichheit, {@code id} über Identität.
 */
public record Photo(PhotoId id, ContentHash hash, String fileName, ImageType type, long sizeBytes,
                    String title, String description) {

    public static final long MAX_FILE_SIZE_BYTES = 20L * 1024 * 1024;
    public static final int MAX_TITLE_LENGTH = 100;
    public static final int MAX_DESCRIPTION_LENGTH = 500;

    public Photo {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(hash, "hash");
        Objects.requireNonNull(fileName, "fileName");
        Objects.requireNonNull(type, "type");
        if (sizeBytes < 0) {
            throw new IllegalArgumentException("sizeBytes must not be negative");
        }
        if (type == ImageType.OTHER || sizeBytes > MAX_FILE_SIZE_BYTES) {
            throw new UnsupportedImageException();
        }
        if (fileName.isBlank()) {
            throw new IllegalArgumentException("fileName must not be blank");
        }
        title = checkedText(title, MAX_TITLE_LENGTH, Field.TITLE);
        description = checkedText(description, MAX_DESCRIPTION_LENGTH, Field.DESCRIPTION);
    }

    /** Neues Foto ohne Titel und Beschreibung. */
    public Photo(PhotoId id, ContentHash hash, String fileName, ImageType type, long sizeBytes) {
        this(id, hash, fileName, type, sizeBytes, "", "");
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
