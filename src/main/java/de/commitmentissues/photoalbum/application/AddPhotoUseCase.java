package de.commitmentissues.photoalbum.application;

import java.nio.file.Path;
import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;
import de.commitmentissues.photoalbum.domain.DomainException;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.PhotoId;
import de.commitmentissues.photoalbum.domain.Violation;

/**
 * UC-01 Foto hinzufügen. Prüft zuerst alles und speichert erst danach (ADR-002):
 * erst die App-Kopie, dann das Album. Scheitert das Album, wird die Kopie zurückgenommen (A-11).
 */
public class AddPhotoUseCase {

    static final String SAVE_FAILED = "Das Foto konnte nicht gespeichert werden.";
    static final String CLEANUP_ON_NEXT_START =
            "Speichern fehlgeschlagen. Die App räumt beim nächsten Start auf, es gehen keine Fotos verloren.";

    private final AlbumRepository albums;
    private final ImageHasher imageHasher;
    private final PhotoStorage photoStorage;

    public AddPhotoUseCase(AlbumRepository albums, ImageHasher imageHasher, PhotoStorage photoStorage) {
        this.albums = albums;
        this.imageHasher = imageHasher;
        this.photoStorage = photoStorage;
    }

    public Result<PhotoSummary> execute(UUID albumId, Path file) {
        Album album;
        try {
            album = albums.findById(new AlbumId(albumId)).orElse(null);
        } catch (StorageException e) {
            return Result.failure("Das Album konnte nicht geladen werden.");
        }
        if (album == null) {
            return Result.failure("Das Album wurde nicht gefunden.");
        }

        Photo photo;
        try {
            photo = new Photo(imageHasher.hash(file));
            album.addPhoto(photo);
        } catch (ImageRejectedException e) {
            return Result.failure(messageFor(e.getProblem()));
        } catch (DomainException e) {
            return Result.failure(messageFor(e.getViolation()));
        }

        return storeAndSave(album, photo, file);
    }

    private Result<PhotoSummary> storeAndSave(Album album, Photo photo, Path file) {
        try {
            photoStorage.store(photo.getId(), file);
        } catch (StorageException e) {
            return Result.failure(SAVE_FAILED);
        }
        try {
            albums.save(album);
        } catch (StorageException e) {
            return rollBack(photo.getId());
        }
        return Result.success(new PhotoSummary(photo.getId().value()));
    }

    private Result<PhotoSummary> rollBack(PhotoId photoId) {
        try {
            photoStorage.delete(photoId);
            return Result.failure(SAVE_FAILED);
        } catch (StorageException e) {
            return Result.failure(CLEANUP_ON_NEXT_START);
        }
    }

    private static String messageFor(ImageProblem problem) {
        return switch (problem) {
            case UNREADABLE -> "Die Datei konnte nicht gelesen werden.";
            case EMPTY -> "Die Bilddatei ist leer.";
            case NOT_AN_IMAGE -> "Die Datei enthält kein gültiges JPEG- oder PNG-Bild.";
            case TOO_LARGE -> "Die Bilddatei darf höchstens 20 MiB groß sein.";
        };
    }

    private static String messageFor(Violation violation) {
        return switch (violation) {
            case PHOTO_DUPLICATE -> "Dieses Bild befindet sich schon in diesem Album";
            case ALBUM_FULL -> "Das Album ist voll (maximal 100 Fotos).";
            default -> throw new IllegalStateException("Unerwartete Regelverletzung: " + violation);
        };
    }
}
