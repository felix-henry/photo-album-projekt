package de.commitmentissues.photoalbum.application;

import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Album;

/**
 * Ein Album öffnen und seine Fotos nennen (FA-05 Vorbedingung, FA-08).
 */
public class OpenAlbumUseCase {

    private final AlbumRepository albums;

    public OpenAlbumUseCase(AlbumRepository albums) {
        this.albums = albums;
    }

    public Result<AlbumDetails> execute(UUID albumId) {
        Result<Album> loaded = AlbumLookup.load(albums, albumId);
        if (!loaded.isSuccess()) {
            return Result.failure(loaded.getMessage());
        }
        Album album = loaded.getValue();
        return Result.success(AlbumDetails.of(album));
    }
}
