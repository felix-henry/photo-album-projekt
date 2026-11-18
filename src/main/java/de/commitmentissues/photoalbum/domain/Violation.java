package de.commitmentissues.photoalbum.domain;

/**
 * Gründe, aus denen die Domäne einen Vorgang ablehnt (ADR-002).
 * Die Meldung für den Nutzer formuliert der jeweilige Use Case.
 */
public enum Violation {
    ALBUM_NAME_EMPTY,
    ALBUM_NAME_TOO_LONG,
    ALBUM_NAME_TAKEN,
    PHOTO_DUPLICATE,
    ALBUM_FULL,
    PHOTO_TITLE_TOO_LONG,
    PHOTO_DESCRIPTION_TOO_LONG,
    TRASH_ENTRY_EXPIRED
}
