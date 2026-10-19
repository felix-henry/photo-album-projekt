package de.dhbw.photoalbum.domain;

import java.util.Objects;

/**
 * Foto in genau einem Album (A-06); kennt seinen Inhalts-Hash, nicht die Bytes (B3 F11).
 * Titel und Beschreibung sind optional und je höchstens 100 Zeichen lang (AE-02, A-02, vorläufig).
 */
public class Photo {

    public static final int MAX_TEXT_LENGTH = 100;

    private final PhotoId id;
    private final String hash;
    private final String storageKey;
    private String title = "";
    private String description = "";

    public Photo(PhotoId id, String hash, String storageKey) {
        this.id = Objects.requireNonNull(id, "id");
        this.hash = requireText(hash, "hash");
        this.storageKey = requireText(storageKey, "storageKey");
    }

    public void describe(String newTitle, String newDescription) {
        String t = newTitle == null ? "" : newTitle;
        String d = newDescription == null ? "" : newDescription;
        if (t.length() > MAX_TEXT_LENGTH || d.length() > MAX_TEXT_LENGTH) {
            throw new InvalidTextException(
                    "Titel und Beschreibung dürfen höchstens " + MAX_TEXT_LENGTH + " Zeichen haben.");
        }
        this.title = t;
        this.description = d;
    }

    public PhotoId id() {
        return id;
    }

    public String hash() {
        return hash;
    }

    public String storageKey() {
        return storageKey;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " darf nicht leer sein");
        }
        return value;
    }
}
