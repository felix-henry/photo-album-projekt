package de.commitmentissues.photoalbum.application;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumLibrary;
import de.commitmentissues.photoalbum.domain.DomainException;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;
import de.commitmentissues.photoalbum.domain.TrashEntryId;
import de.commitmentissues.photoalbum.domain.Violation;

/**
 * Einen Papierkorbeintrag wiederherstellen (FA-04, FA-09, A-09). Bereinigt vorher fällige Einträge (A-10).
 * Speichert zuerst das aufnehmende Album und danach {@code trash.json}. Scheitert {@code trash.json},
 * nimmt er die Änderung am Album zurück (ADR-002, AK-04.5).
 */
public class RestoreTrashEntryUseCase {

    static final String EXPIRED = "Die Frist für die Wiederherstellung ist abgelaufen.";
    static final String RESTORE_FAILED = "Der Eintrag konnte nicht wiederhergestellt werden.";
    private static final String NEW_ALBUM_HINT =
            " Der Eintrag kann in ein neues Album mit freiem Namen wiederhergestellt werden.";

    private final AlbumRepository albums;
    private final TrashRepository trashRepository;
    private final PhotoStorage photoStorage;
    private final Clock clock;

    public RestoreTrashEntryUseCase(AlbumRepository albums, TrashRepository trashRepository,
            PhotoStorage photoStorage, Clock clock) {
        this.albums = albums;
        this.trashRepository = trashRepository;
        this.photoStorage = photoStorage;
        this.clock = clock;
    }

    /**
     * Stellt den Eintrag in sein Ursprungsalbum wieder her. Fehlt es, wird es mit seinem Namen neu angelegt
     * (AK-04.3, AK-09.3, AK-09.4).
     */
    public Result<AlbumSummary> execute(UUID entryId) {
        return restore(entryId, null);
    }

    /**
     * Stellt den Eintrag in ein neues Album mit dem angegebenen Namen wieder her (AK-04.4, AK-09.6).
     */
    public Result<AlbumSummary> executeIntoNewAlbum(UUID entryId, String albumName) {
        return restore(entryId, Objects.requireNonNull(albumName, "albumName"));
    }

    private Result<AlbumSummary> restore(UUID entryId, String newAlbumName) {
        Instant now = clock.instant();
        Trash trash;
        List<Album> all;
        try {
            trash = trashRepository.load();
        } catch (StorageException e) {
            return Result.failure("Der Papierkorb konnte nicht geladen werden.");
        }
        try {
            all = albums.findAll();
        } catch (StorageException e) {
            return Result.failure("Die Alben konnten nicht geladen werden.");
        }
        TrashEntryId id = new TrashEntryId(entryId);
        TrashEntry entry = trash.find(id).orElse(null);
        if (entry == null) {
            return Result.failure("Der Eintrag wurde nicht gefunden.");
        }
        boolean expired = entry.isDue(now);
        cleanUpDueEntries(trash, all, now);
        if (expired) {
            return Result.failure(EXPIRED);
        }

        Target target;
        try {
            trash.take(id, now);
            target = newAlbumName == null ? originOf(entry, all) : newAlbum(newAlbumName, all);
            entry.getPhotos().forEach(target.album()::addPhoto);
        } catch (DomainException e) {
            return Result.failure(messageFor(e.getViolation(), entry, newAlbumName == null));
        }
        return saveAlbumThenTrash(target, entry, trash);
    }

    private void cleanUpDueEntries(Trash trash, List<Album> all, Instant now) {
        if (trash.dueEntries(now).isEmpty()) {
            return;
        }
        try {
            DueEntryCleanup.run(trash, DueEntryCleanup.photosInAlbums(all), photoStorage, now);
            trashRepository.save(trash);
        } catch (StorageException e) {
            // Nicht bereinigte Einträge bleiben gesperrt und werden später erneut bereinigt (A-10).
        }
    }

    private static Target originOf(TrashEntry entry, List<Album> all) {
        if (!entry.isAlbum()) {
            Optional<Album> origin = all.stream()
                    .filter(album -> album.getId().equals(entry.getOriginAlbumId()))
                    .findFirst();
            if (origin.isPresent()) {
                return new Target(origin.get(), false);
            }
        }
        return newAlbum(entry.getOriginAlbumName(), all);
    }

    private static Target newAlbum(String name, List<Album> all) {
        Album album = new Album(name);
        new AlbumLibrary(all).add(album);
        return new Target(album, true);
    }

    private Result<AlbumSummary> saveAlbumThenTrash(Target target, TrashEntry entry, Trash trash) {
        try {
            albums.save(target.album());
        } catch (StorageException e) {
            return Result.failure(RESTORE_FAILED);
        }
        try {
            trashRepository.save(trash);
        } catch (StorageException e) {
            return rollBack(target, entry);
        }
        return Result.success(AlbumSummary.of(target.album()));
    }

    private Result<AlbumSummary> rollBack(Target target, TrashEntry entry) {
        try {
            if (target.created()) {
                albums.delete(target.album().getId());
            } else {
                entry.getPhotos().forEach(photo -> target.album().removePhoto(photo.getId()));
                albums.save(target.album());
            }
            return Result.failure(RESTORE_FAILED);
        } catch (StorageException e) {
            return Result.failure(AddPhotoUseCase.CLEANUP_ON_NEXT_START);
        }
    }

    private static String messageFor(Violation violation, TrashEntry entry, boolean intoOrigin) {
        return switch (violation) {
            case PHOTO_DUPLICATE -> "Dieses Bild befindet sich schon im Ursprungsalbum." + NEW_ALBUM_HINT;
            case ALBUM_FULL -> "Das Ursprungsalbum ist voll (maximal 100 Fotos)." + NEW_ALBUM_HINT;
            case ALBUM_NAME_TAKEN -> intoOrigin
                    ? "Ein anderes Album heißt bereits „" + entry.getOriginAlbumName() + "“." + NEW_ALBUM_HINT
                    : "Dieser Albumname ist bereits vergeben.";
            case ALBUM_NAME_EMPTY -> "Der Albumname darf nicht leer sein.";
            case ALBUM_NAME_TOO_LONG -> "Der Albumname darf höchstens 50 Zeichen lang sein.";
            default -> throw new IllegalStateException("Unerwartete Regelverletzung: " + violation);
        };
    }

    private record Target(Album album, boolean created) {
    }
}
