package de.commitmentissues.photoalbum.application;

import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Photo;

/**
 * Was die UI über ein Foto wissen muss: ID, Titel und Beschreibung (FA-10).
 * Ein leerer Text bedeutet: kein Titel bzw. keine Beschreibung.
 */
public record PhotoSummary(UUID id, String title, String description) {

    static PhotoSummary of(Photo photo) {
        return new PhotoSummary(photo.getId().value(), photo.getTitle(), photo.getDescription());
    }
}
