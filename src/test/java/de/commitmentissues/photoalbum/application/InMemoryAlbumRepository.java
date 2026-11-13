package de.commitmentissues.photoalbum.application;

import java.util.ArrayList;
import java.util.List;

import de.commitmentissues.photoalbum.domain.Album;

/**
 * Speichert Alben im Arbeitsspeicher und kann Lese- und Speicherfehler simulieren.
 * Wie eine Datei hält er Kopien: Änderungen an einem geladenen Album wirken erst nach save.
 */
class InMemoryAlbumRepository implements AlbumRepository {

    private final List<Album> albums = new ArrayList<>();
    boolean failOnLoad;
    boolean failOnSave;

    @Override
    public List<Album> findAll() {
        if (failOnLoad) {
            throw new StorageException("Lesefehler simuliert");
        }
        return albums.stream().map(InMemoryAlbumRepository::copy).toList();
    }

    @Override
    public void save(Album album) {
        if (failOnSave) {
            throw new StorageException("Speicherfehler simuliert");
        }
        albums.removeIf(stored -> stored.getId().equals(album.getId()));
        albums.add(copy(album));
    }

    List<String> names() {
        return albums.stream().map(Album::getName).toList();
    }

    private static Album copy(Album album) {
        return Album.restore(album.getId(), album.getName());
    }
}
