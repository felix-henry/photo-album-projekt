package de.commitmentissues.photoalbum.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Die aktiven Alben. Sorgt dafür, dass ihre Namen unabhängig von Groß- und
 * Kleinschreibung eindeutig sind (A-02).
 */
public class AlbumLibrary {

    private final List<Album> albums;

    public AlbumLibrary(List<Album> albums) {
        this.albums = new ArrayList<>(albums);
    }

    public void add(Album album) {
        if (isNameTaken(album.getName())) {
            throw new DomainException(Violation.ALBUM_NAME_TAKEN);
        }
        albums.add(album);
    }

    public List<Album> getAlbums() {
        return List.copyOf(albums);
    }

    private boolean isNameTaken(String name) {
        return albums.stream().anyMatch(album -> album.getName().equalsIgnoreCase(name));
    }
}
