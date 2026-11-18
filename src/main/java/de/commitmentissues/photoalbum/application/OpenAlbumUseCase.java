package de.commitmentissues.photoalbum.application;

import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;

/**
 * Ein Album öffnen und seine Fotos nennen (FA-05 Vorbedingung, FA-08).
 */
public class OpenAlbumUseCase {

    private final AlbumRepository albums;

    public OpenAlbumUseCase(AlbumRepository albums) {
        this.albums = albums;
    }

    public Result<AlbumDetails> execute(UUID albumId) {
        Album album;
        try {
            album = albums.findById(new AlbumId(albumId)).orElse(null);
        } catch (StorageException e) {
            return Result.failure("Das Album konnte nicht geladen werden.");
        }
        if (album == null) {
            return Result.failure("Das Album wurde nicht gefunden.");
        }
        return Result.success(AlbumDetails.of(album));
    }
}
