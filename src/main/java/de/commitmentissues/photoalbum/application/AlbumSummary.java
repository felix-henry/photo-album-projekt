package de.commitmentissues.photoalbum.application;

import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Album;

/**
 * Was die UI über ein Album anzeigen muss.
 */
public record AlbumSummary(UUID id, String name) {

    static AlbumSummary of(Album album) {
        return new AlbumSummary(album.getId().value(), album.getName());
    }
}
