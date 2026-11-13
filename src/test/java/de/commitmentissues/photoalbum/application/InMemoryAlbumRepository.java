package de.commitmentissues.photoalbum.application;

import java.util.ArrayList;
import java.util.List;

import de.commitmentissues.photoalbum.domain.Album;

/**
 * Speichert Alben im Arbeitsspeicher und kann Lese- und Speicherfehler simulieren.
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
        return List.copyOf(albums);
    }

    @Override
    public void save(Album album) {
        if (failOnSave) {
            throw new StorageException("Speicherfehler simuliert");
        }
        albums.removeIf(stored -> stored.getId().equals(album.getId()));
        albums.add(album);
    }

    List<String> names() {
        return albums.stream().map(Album::getName).toList();
    }
}
