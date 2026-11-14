package de.commitmentissues.photoalbum.infrastructure;

import java.util.List;

import de.commitmentissues.photoalbum.domain.Album;

/**
 * Inhalt einer Albumdatei. Nur Gson arbeitet mit dieser Klasse (ADR-003).
 * Dateien aus der Zeit vor den Fotos haben kein Feld {@code photos}; Gson liefert dann {@code null}.
 */
record AlbumFile(String id, String name, List<PhotoFile> photos) {

    static AlbumFile of(Album album) {
        List<PhotoFile> photos = album.getPhotos().stream().map(PhotoFile::of).toList();
        return new AlbumFile(album.getId().value().toString(), album.getName(), photos);
    }
}
