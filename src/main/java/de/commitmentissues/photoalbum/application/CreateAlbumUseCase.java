package de.commitmentissues.photoalbum.application;

import java.util.List;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumLibrary;
import de.commitmentissues.photoalbum.domain.DomainException;
import de.commitmentissues.photoalbum.domain.Violation;

/**
 * UC-02 Album erstellen.
 */
public class CreateAlbumUseCase {

    private final AlbumRepository albums;

    public CreateAlbumUseCase(AlbumRepository albums) {
        this.albums = albums;
    }

    public Result<AlbumSummary> execute(String name) {
        try {
            Album album = new Album(name);
            List<Album> existing = albums.findAll();
            new AlbumLibrary(existing).add(album);
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
