package de.commitmentissues.photoalbum.application;

import de.commitmentissues.photoalbum.domain.Trash;

/**
 * Port für den Papierkorb (A-07). Beide Methoden werfen eine {@link StorageException}, wenn der Zugriff
 * scheitert. {@code load} meldet einen unlesbaren Papierkorb als Fehler, statt ihn als leer zu behandeln
 * (ADR-002).
 */
public interface TrashRepository {

    Trash load();

    void save(Trash trash);
}
