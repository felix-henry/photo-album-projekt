package de.commitmentissues.photoalbum.application;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

/**
 * Ein Foto aus seinem Album in den Papierkorb verschieben (FA-09, A-07), ohne Rückfrage (AK-09.1).
 * Speichert zuerst {@code trash.json} und danach die Albumdatei. Scheitert die Albumdatei, stellt er den
 * alten Papierkorb wieder her (ADR-002, AK-09.2).
 */
public class RemovePhotoUseCase {

    static final String MOVE_FAILED = "Das Foto konnte nicht in den Papierkorb verschoben werden.";

    private final AlbumRepository albums;
    private final TrashRepository trashRepository;
    private final Clock clock;

    public RemovePhotoUseCase(AlbumRepository albums, TrashRepository trashRepository, Clock clock) {
        this.albums = albums;
        this.trashRepository = trashRepository;
        this.clock = clock;
    }

    public Result<AlbumDetails> execute(UUID albumId, UUID photoId) {
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
        Trash trash;
        try {
            trash = trashRepository.load();
        } catch (StorageException e) {
            return Result.failure("Der Papierkorb konnte nicht geladen werden.");
        }

        List<TrashEntry> before = trash.getEntries();
        trash.movePhoto(photo, album, clock.instant());
        try {
            trashRepository.save(trash);
        } catch (StorageException e) {
            return Result.failure(MOVE_FAILED);
        }
        try {
            albums.save(album);
        } catch (StorageException e) {
            return rollBack(before);
        }
        return Result.success(AlbumDetails.of(album));
    }

    private Result<AlbumDetails> rollBack(List<TrashEntry> before) {
        try {
            trashRepository.save(new Trash(before));
            return Result.failure(MOVE_FAILED);
        } catch (StorageException e) {
            return Result.failure(AddPhotoUseCase.CLEANUP_ON_NEXT_START);
        }
    }
}
