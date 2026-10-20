package de.dhbw.photoalbum.domain;

import de.dhbw.photoalbum.domain.TextTooLongException.Field;
import java.text.Normalizer;
import java.util.Objects;

/**
 * Foto in genau einem Album. Der Konstruktor prüft Typ und Größe (FA-06, A-03, A-04) sowie
 * Titel und Beschreibung (FA-10, AE-02, A-05, A-06, A-13). Identität ist {@code id};
 * {@code hash} entscheidet über Inhaltsgleichheit (FA-07, A-01).
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

    /** Neues Foto ohne Titel und Beschreibung (UC-01, A-06). */
    public Photo(PhotoId id, ContentHash hash, String fileName, ImageType type, long sizeBytes) {
        this(id, hash, fileName, type, sizeBytes, "", "");
    }

    /** {@code null} wird leer (A-06); gezählt werden Codepoints nach NFC (A-13). */
    private static String checkedText(String text, int maxLength, Field field) {
        String normalized = text == null ? "" : Normalizer.normalize(text, Normalizer.Form.NFC);
        if (normalized.codePointCount(0, normalized.length()) > maxLength) {
            throw new TextTooLongException(field);
        }
        return normalized;
    }
}
