package de.commitmentissues.photoalbum.application;

import java.util.UUID;

/**
 * Was die UI über ein Foto wissen muss. Titel und Beschreibung folgen mit FA-10.
 */
public record PhotoSummary(UUID id) {
}
