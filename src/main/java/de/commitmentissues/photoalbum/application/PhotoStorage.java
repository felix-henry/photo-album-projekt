package de.commitmentissues.photoalbum.application;

import java.nio.file.Path;
import java.util.Set;

import de.commitmentissues.photoalbum.domain.PhotoId;

/**
 * Port für die App-Kopien der importierten Bilder (A-05).
 * Alle Methoden werfen eine {@link StorageException}, wenn der Zugriff scheitert.
 */
public interface PhotoStorage {

    void store(PhotoId id, Path file);

    /**
     * Liefert die Bilddaten der Kopie zum Anzeigen (FA-08).
     */
    byte[] load(PhotoId id);

    /**
     * Löscht die Kopie. Eine schon fehlende Kopie gilt als gelöscht (ADR-002).
     */
    void delete(PhotoId id);

    /**
     * Nennt die IDs aller App-Kopien, damit beim Start verwaiste Kopien entfernt werden (A-11).
     */
    Set<PhotoId> ids();
}
