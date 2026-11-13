package de.commitmentissues.photoalbum.infrastructure;

import de.commitmentissues.photoalbum.domain.Album;

/**
 * Inhalt einer Albumdatei. Nur Gson arbeitet mit dieser Klasse (ADR-003).
 */
record AlbumFile(String id, String name) {

    static AlbumFile of(Album album) {
        return new AlbumFile(album.getId().value().toString(), album.getName());
    }
}
