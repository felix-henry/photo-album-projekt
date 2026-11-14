package de.commitmentissues.photoalbum.domain;

import java.util.Objects;

/**
 * Ein Foto in einem Album. Der Inhaltshash erkennt bytegleiche Bilder (A-01).
 * Titel und Beschreibung sind optional und lassen sich unabhängig voneinander ändern und
 * leeren (FA-10, A-04). Ein leerer Text bedeutet: kein Titel bzw. keine Beschreibung.
 */
public class Photo {

    static final int MAX_TITLE_LENGTH = 100;
    static final int MAX_DESCRIPTION_LENGTH = 500;

    private final PhotoId id;
    private final String contentHash;
    private String title;
    private String description;

    public Photo(String contentHash) {
        this(PhotoId.newId(), contentHash, "", "");
    }

    private Photo(PhotoId id, String contentHash, String title, String description) {
        this.id = Objects.requireNonNull(id, "id");
        this.contentHash = Objects.requireNonNull(contentHash, "contentHash");
        if (contentHash.isBlank()) {
            throw new IllegalArgumentException("Der Inhaltshash darf nicht leer sein.");
        }
        this.title = checkTitle(title);
        this.description = checkDescription(description);
    }

    /**
     * Stellt ein gespeichertes Foto ohne Titel und Beschreibung mit seiner bisherigen ID wieder her.
     */
    public static Photo restore(PhotoId id, String contentHash) {
        return restore(id, contentHash, "", "");
    }

    /**
     * Stellt ein gespeichertes Foto mit seiner bisherigen ID, seinem Titel und seiner Beschreibung wieder her.
     */
    public static Photo restore(PhotoId id, String contentHash, String title, String description) {
        return new Photo(id, contentHash, title, description);
    }

    public PhotoId getId() {
        return id;
    }

    public String getContentHash() {
        return contentHash;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void changeTitle(String newTitle) {
        this.title = checkTitle(newTitle);
    }

    public void changeDescription(String newDescription) {
        this.description = checkDescription(newDescription);
    }

    /**
     * Prüft einen Titel nach A-04. Öffentlich, damit auch ein Titelvorschlag dieselbe Regel
     * nutzt (ARCHITECTURE, Abschnitt 7).
     */
    public static String checkTitle(String title) {
        Objects.requireNonNull(title, "title");
        if (TextLength.count(title) > MAX_TITLE_LENGTH) {
            throw new DomainException(Violation.PHOTO_TITLE_TOO_LONG);
        }
        return title;
    }

    private static String checkDescription(String description) {
        Objects.requireNonNull(description, "description");
        if (TextLength.count(description) > MAX_DESCRIPTION_LENGTH) {
            throw new DomainException(Violation.PHOTO_DESCRIPTION_TOO_LONG);
        }
        return description;
    }
}
