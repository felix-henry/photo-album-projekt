package de.commitmentissues.photoalbum.application;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.commitmentissues.photoalbum.domain.PhotoId;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;
import de.commitmentissues.photoalbum.domain.TrashEntryId;

/**
 * Einen Papierkorbeintrag samt App-Kopien endgültig löschen (SR-01). Die Rückfrage stellt die UI vorher
 * (A-10, AK-SR01.5). Jedes Foto zählt einzeln: Gelöschte bleiben gelöscht, die übrigen bleiben im
 * Papierkorb, und ein Teilerfolg kommt als Meldung zurück (A-12, AK-SR01.4).
 */
public class DeletePermanentlyUseCase {

    static final String NOTHING_DELETED =
            "Der Eintrag konnte nicht endgültig gelöscht werden und bleibt im Papierkorb.";

    private final TrashRepository trashRepository;
    private final AlbumRepository albums;
    private final PhotoStorage photoStorage;

    public DeletePermanentlyUseCase(TrashRepository trashRepository, AlbumRepository albums,
            PhotoStorage photoStorage) {
        this.trashRepository = trashRepository;
        this.albums = albums;
        this.photoStorage = photoStorage;
    }

    public Result<Void> execute(UUID entryId) {
        Trash trash;
        try {
            trash = trashRepository.load();
        } catch (StorageException e) {
            return Result.failure("Der Papierkorb konnte nicht geladen werden.");
        }
        TrashEntryId id = new TrashEntryId(entryId);
        TrashEntry entry = trash.find(id).orElse(null);
        if (entry == null) {
            return Result.failure("Der Eintrag wurde nicht gefunden.");
        }
        Set<PhotoId> photosInAlbums;
        try {
            photosInAlbums = DueEntryCleanup.photosInAlbums(albums.findAll());
        } catch (StorageException e) {
            return Result.failure("Die Alben konnten nicht geladen werden.");
        }

        int total = entry.getPhotos().size();
        List<PhotoId> removed = DueEntryCleanup.deleteCopies(entry, photosInAlbums, photoStorage);
        trash.remove(id, removed);
        try {
            trashRepository.save(trash);
        } catch (StorageException e) {
            return Result.failure("Der Papierkorb konnte nicht gespeichert werden.");
        }

        int kept = total - removed.size();
        if (kept == 0) {
            return Result.success(null);
        }
        if (removed.isEmpty()) {
            return Result.failure(NOTHING_DELETED);
        }
        return Result.failure(removed.size() + " von " + total + " Fotos wurden endgültig gelöscht. "
                + (kept == 1 ? "1 Foto bleibt" : kept + " Fotos bleiben") + " im Papierkorb.");
    }
}
