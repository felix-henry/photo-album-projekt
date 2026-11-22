package de.commitmentissues.photoalbum.application;

import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;

/**
 * Lädt das Album, auf das sich ein Use Case bezieht, und liefert die einheitlichen Meldungen, wenn es nicht
 * geladen werden kann oder fehlt (ADR-002).
 */
final class AlbumLookup {

    private AlbumLookup() {
    }

    static Result<Album> load(AlbumRepository albums, UUID albumId) {
        Album album;
        try {
            album = albums.findById(new AlbumId(albumId)).orElse(null);
        } catch (StorageException e) {
            return Result.failure("Das Album konnte nicht geladen werden.");
        }
        if (album == null) {
            return Result.failure("Das Album wurde nicht gefunden.");
        }
        return Result.success(album);
    }
}
