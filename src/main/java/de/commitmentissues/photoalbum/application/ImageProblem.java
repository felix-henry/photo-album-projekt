package de.commitmentissues.photoalbum.application;

/**
 * Gründe, aus denen der {@link ImageHasher} eine Datei ablehnt (A-03, AK-05.2, AK-05.4).
 */
public enum ImageProblem {
    UNREADABLE,
    EMPTY,
    NOT_AN_IMAGE,
    TOO_LARGE
}
