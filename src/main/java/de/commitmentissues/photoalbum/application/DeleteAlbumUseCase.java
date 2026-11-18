package de.commitmentissues.photoalbum.application;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

/**
 * Ein Album samt Fotos als Kachel in den Papierkorb verschieben (FA-04, A-07). Die Rückfrage stellt die
 * UI vorher (AK-04.1, AK-04.7). Speichert zuerst {@code trash.json} und löscht danach die Albumdatei.
 * Scheitert das Löschen, stellt er den alten Papierkorb wieder her (ADR-002, AK-04.2).
 */
public class DeleteAlbumUseCase {

    static final String MOVE_FAILED = "Das Album konnte nicht in den Papierkorb verschoben werden.";

    private final AlbumRepository albums;
    private final TrashRepository trashRepository;
    private final Clock clock;

    public DeleteAlbumUseCase(AlbumRepository albums, TrashRepository trashRepository, Clock clock) {
        this.albums = albums;
        this.trashRepository = trashRepository;
        this.clock = clock;
    }

    public Result<AlbumSummary> execute(UUID albumId) {
        Album album;
        try {
            album = albums.findById(new AlbumId(albumId)).orElse(null);
        } catch (StorageException e) {
            return Result.failure("Das Album konnte nicht geladen werden.");
        }
        if (album == null) {
            return Result.failure("Das Album wurde nicht gefunden.");
        }
        Trash trash;
        try {
            trash = trashRepository.load();
        } catch (StorageException e) {
            return Result.failure("Der Papierkorb konnte nicht geladen werden.");
        }

        List<TrashEntry> before = trash.getEntries();
        trash.moveAlbum(album, clock.instant());
        try {
            trashRepository.save(trash);
        } catch (StorageException e) {
            return Result.failure(MOVE_FAILED);
        }
        try {
            albums.delete(album.getId());
        } catch (StorageException e) {
            return rollBack(before);
        }
        return Result.success(AlbumSummary.of(album));
    }

    private Result<AlbumSummary> rollBack(List<TrashEntry> before) {
        try {
            trashRepository.save(new Trash(before));
            return Result.failure(MOVE_FAILED);
        } catch (StorageException e) {
            return Result.failure(AddPhotoUseCase.CLEANUP_ON_NEXT_START);
        }
    }
}
