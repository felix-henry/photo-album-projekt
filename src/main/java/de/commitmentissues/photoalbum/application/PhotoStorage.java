package de.commitmentissues.photoalbum.application;

import java.nio.file.Path;

import de.commitmentissues.photoalbum.domain.PhotoId;

/**
 * Port für die App-Kopien der importierten Bilder (A-05).
 * Alle Methoden werfen eine {@link StorageException}, wenn der Zugriff scheitert.
 */
public interface PhotoStorage {

    void store(PhotoId id, Path file);

    /**
     * Löscht die Kopie. Eine schon fehlende Kopie gilt als gelöscht (ADR-002).
     */
    void delete(PhotoId id);
}
