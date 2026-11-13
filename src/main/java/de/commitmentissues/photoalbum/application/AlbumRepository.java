package de.commitmentissues.photoalbum.application;

import java.util.List;

import de.commitmentissues.photoalbum.domain.Album;

/**
 * Port zum Laden und Speichern von Alben (ADR-001).
 * Beide Methoden werfen eine {@link StorageException}, wenn der Zugriff scheitert.
 */
public interface AlbumRepository {

    List<Album> findAll();

    void save(Album album);
}
