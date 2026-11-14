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
        if (isTakenByAnother(album.getName(), album)) {
            throw new DomainException(Violation.ALBUM_NAME_TAKEN);
        }
        albums.add(album);
    }

    /**
     * Benennt ein Album dieser Bibliothek um. Der neue Name muss gültig und unter den
     * anderen Alben frei sein. Die eigene Schreibweise darf sich ändern (A-02).
     */
    public void rename(Album album, String newName) {
        if (!albums.contains(album)) {
            throw new IllegalArgumentException("Das Album gehört nicht zu dieser Bibliothek.");
        }
        String checkedName = Album.checkName(newName);
        if (isTakenByAnother(checkedName, album)) {
            throw new DomainException(Violation.ALBUM_NAME_TAKEN);
        }
        album.rename(checkedName);
    }

    public List<Album> getAlbums() {
        return List.copyOf(albums);
    }

    private boolean isTakenByAnother(String name, Album album) {
        return albums.stream()
                .filter(other -> !other.getId().equals(album.getId()))
                .anyMatch(other -> other.getName().equalsIgnoreCase(name));
    }
}
