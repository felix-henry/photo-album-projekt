package de.commitmentissues.photoalbum.application;

import java.util.List;
import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Album;

/**
 * Was die Albumansicht über ein geöffnetes Album wissen muss (FA-08). Die Fotos stehen
 * in der Reihenfolge, in der sie hinzugefügt wurden.
 */
public record AlbumDetails(UUID id, String name, List<PhotoSummary> photos) {

    public AlbumDetails {
        photos = List.copyOf(photos);
    }

    static AlbumDetails of(Album album) {
        List<PhotoSummary> photos = album.getPhotos().stream()
                .map(PhotoSummary::of)
                .toList();
        return new AlbumDetails(album.getId().value(), album.getName(), photos);
    }
}
