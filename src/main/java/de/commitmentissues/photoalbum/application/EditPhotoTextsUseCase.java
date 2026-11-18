package de.commitmentissues.photoalbum.application;

import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;
import de.commitmentissues.photoalbum.domain.DomainException;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.Violation;

/**
 * Titel und Beschreibung eines Fotos speichern, ändern und leeren (FA-10, A-04).
 * Ist einer der beiden Texte ungültig, wird nichts gespeichert (ADR-002).
 */
public class EditPhotoTextsUseCase {

    private final AlbumRepository albums;

    public EditPhotoTextsUseCase(AlbumRepository albums) {
        this.albums = albums;
    }

    public Result<PhotoSummary> execute(UUID albumId, UUID photoId, String title, String description) {
        Album album;
        try {
            album = albums.findById(new AlbumId(albumId)).orElse(null);
        } catch (StorageException e) {
            return Result.failure("Das Album konnte nicht geladen werden.");
        }
        if (album == null) {
            return Result.failure("Das Album wurde nicht gefunden.");
        }
        Photo photo = album.getPhotos().stream()
                .filter(candidate -> candidate.getId().value().equals(photoId))
                .findFirst()
                .orElse(null);
        if (photo == null) {
            return Result.failure("Das Foto wurde nicht gefunden.");
        }

        try {
            photo.changeTitle(title);
            photo.changeDescription(description);
            albums.save(album);
        } catch (DomainException e) {
            return Result.failure(messageFor(e.getViolation()));
        } catch (StorageException e) {
            return Result.failure("Titel und Beschreibung konnten nicht gespeichert werden.");
        }
        return Result.success(PhotoSummary.of(photo));
    }

    private static String messageFor(Violation violation) {
        return switch (violation) {
            case PHOTO_TITLE_TOO_LONG -> "Der Titel darf höchstens 100 Zeichen lang sein.";
            case PHOTO_DESCRIPTION_TOO_LONG -> "Die Beschreibung darf höchstens 500 Zeichen lang sein.";
            default -> throw new IllegalStateException("Unerwartete Regelverletzung: " + violation);
        };
    }
}
