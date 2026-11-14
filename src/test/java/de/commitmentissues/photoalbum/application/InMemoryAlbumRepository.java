package de.commitmentissues.photoalbum.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;
import de.commitmentissues.photoalbum.domain.Photo;

/**
 * Speichert Alben im Arbeitsspeicher und kann Lese-, Speicher- und Löschfehler simulieren.
 * Wie eine Datei hält er Kopien von Album und Fotos: Änderungen an einem geladenen Album
 * wirken erst nach save.
 */
class InMemoryAlbumRepository implements AlbumRepository {

    private final List<Album> albums = new ArrayList<>();
    boolean failOnLoad;
    boolean failOnSave;
    boolean failOnDelete;

    @Override
    public Optional<Album> findById(AlbumId id) {
        if (failOnLoad) {
            throw new StorageException("Lesefehler simuliert");
        }
        return albums.stream()
                .filter(album -> album.getId().equals(id))
                .findFirst()
                .map(InMemoryAlbumRepository::copy);
    }

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

    @Override
    public void delete(AlbumId id) {
        if (failOnDelete) {
            throw new StorageException("Löschfehler simuliert");
        }
        albums.removeIf(stored -> stored.getId().equals(id));
    }

    List<String> names() {
        return albums.stream().map(Album::getName).toList();
    }

    private static Album copy(Album album) {
        List<Photo> photos = album.getPhotos().stream()
                .map(photo -> Photo.restore(photo.getId(), photo.getContentHash(),
                        photo.getTitle(), photo.getDescription()))
                .toList();
        return Album.restore(album.getId(), album.getName(), photos);
    }
}
