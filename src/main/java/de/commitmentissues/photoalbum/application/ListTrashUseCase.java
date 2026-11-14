package de.commitmentissues.photoalbum.application;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import de.commitmentissues.photoalbum.domain.Trash;

/**
 * Den Papierkorb anzeigen (A-08, AK-04.8). Bereinigt vorher fällige Einträge (A-10).
 */
public class ListTrashUseCase {

    private final TrashRepository trashRepository;
    private final AlbumRepository albums;
    private final PhotoStorage photoStorage;
    private final Clock clock;

    public ListTrashUseCase(TrashRepository trashRepository, AlbumRepository albums, PhotoStorage photoStorage,
            Clock clock) {
        this.trashRepository = trashRepository;
        this.albums = albums;
        this.photoStorage = photoStorage;
        this.clock = clock;
    }

    public Result<List<TrashEntrySummary>> execute() {
        Instant now = clock.instant();
        Trash trash;
        try {
            trash = trashRepository.load();
        } catch (StorageException e) {
            return Result.failure("Der Papierkorb konnte nicht geladen werden.");
        }
        cleanUpDueEntries(trash, now);
        return Result.success(trash.getEntries().stream().map(entry -> TrashEntrySummary.of(entry, now)).toList());
    }

    /**
     * Scheitert die Bereinigung, bleiben die fälligen Einträge im Papierkorb und gesperrt (AK-SR01.6). Sie
     * wird beim nächsten Öffnen, vor einer Wiederherstellung und beim Start wiederholt (A-10).
     */
    private void cleanUpDueEntries(Trash trash, Instant now) {
        if (trash.dueEntries(now).isEmpty()) {
            return;
        }
        try {
            DueEntryCleanup.run(trash, DueEntryCleanup.photosInAlbums(albums.findAll()), photoStorage, now);
            trashRepository.save(trash);
        } catch (StorageException e) {
            // Siehe Javadoc: Die Bereinigung wird später wiederholt.
        }
    }
}
