package de.commitmentissues.photoalbum.application;

import java.util.List;
import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumLibrary;
import de.commitmentissues.photoalbum.domain.DomainException;
import de.commitmentissues.photoalbum.domain.Violation;

/**
 * Album umbenennen (FA-03).
 */
public class RenameAlbumUseCase {

    private final AlbumRepository albums;

    public RenameAlbumUseCase(AlbumRepository albums) {
        this.albums = albums;
    }

    public Result<AlbumSummary> execute(UUID albumId, String newName) {
        try {
            List<Album> all = albums.findAll();
            Album album = all.stream()
                    .filter(candidate -> candidate.getId().value().equals(albumId))
                    .findFirst()
                    .orElse(null);
            if (album == null) {
                return Result.failure("Das Album wurde nicht gefunden.");
            }
            new AlbumLibrary(all).rename(album, newName);
            albums.save(album);
            return Result.success(AlbumSummary.of(album));
        } catch (DomainException e) {
            return Result.failure(messageFor(e.getViolation()));
        } catch (StorageException e) {
            return Result.failure("Das Album konnte nicht gespeichert werden.");
        }
    }

    private static String messageFor(Violation violation) {
        return switch (violation) {
            case ALBUM_NAME_EMPTY -> "Der Albumname darf nicht leer sein.";
            case ALBUM_NAME_TOO_LONG -> "Der Albumname darf höchstens 50 Zeichen lang sein.";
            case ALBUM_NAME_TAKEN -> "Dieser Albumname ist bereits vergeben.";
            default -> throw new IllegalStateException("Unerwartete Regelverletzung: " + violation);
        };
    }
}
