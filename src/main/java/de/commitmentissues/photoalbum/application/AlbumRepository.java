package de.commitmentissues.photoalbum.application;

import java.util.List;
import java.util.Optional;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;

/**
 * Port zum Laden und Speichern von Alben (ADR-001).
 * Alle Methoden werfen eine {@link StorageException}, wenn der Zugriff scheitert.
 */
public interface AlbumRepository {

    Optional<Album> findById(AlbumId id);

    List<Album> findAll();

    void save(Album album);
}
