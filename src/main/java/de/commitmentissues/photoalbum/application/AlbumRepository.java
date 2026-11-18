package de.commitmentissues.photoalbum.application;

import java.util.List;
import java.util.Optional;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;

/**
 * Port zum Laden, Speichern und Löschen von Alben (ADR-001).
 * Alle Methoden werfen eine {@link StorageException}, wenn der Zugriff scheitert.
 */
public interface AlbumRepository {

    Optional<Album> findById(AlbumId id);

    List<Album> findAll();

    void save(Album album);

    /**
     * Löscht das gespeicherte Album beim Verschieben in den Papierkorb (A-07). Ein schon fehlendes Album
     * gilt als gelöscht.
     */
    void delete(AlbumId id);
}
