package de.commitmentissues.photoalbum.application;

import java.util.List;

import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

/**
 * Hält den Papierkorb im Arbeitsspeicher und kann Lese- und Speicherfehler simulieren. Wie eine Datei
 * hält er eine Kopie: Änderungen an einem geladenen Papierkorb wirken erst nach save.
 */
class InMemoryTrashRepository implements TrashRepository {

    private Trash stored = new Trash(List.of());
    private int savesUntilFailure = -1;
    boolean failOnLoad;
    boolean failOnSave;

    @Override
    public Trash load() {
        if (failOnLoad) {
            throw new StorageException("Lesefehler simuliert");
        }
        return copy(stored);
    }

    @Override
    public void save(Trash trash) {
        if (failOnSave || savesUntilFailure == 0) {
            throw new StorageException("Speicherfehler simuliert");
        }
        if (savesUntilFailure > 0) {
            savesUntilFailure--;
        }
        stored = copy(trash);
    }

    /**
     * Die nächsten {@code count} Speichervorgänge gelingen, alle weiteren scheitern. So lässt sich ein
     * gescheitertes Zurücknehmen simulieren (A-11).
     */
    void failAfterSaves(int count) {
        savesUntilFailure = count;
    }

    List<TrashEntry> entries() {
        return stored.getEntries();
    }

    private static Trash copy(Trash trash) {
        List<TrashEntry> entries = trash.getEntries().stream()
                .map(entry -> TrashEntry.restore(entry.getId(), entry.getDeletedAt(), entry.getOriginAlbumId(),
                        entry.getOriginAlbumName(), entry.isAlbum(), entry.getPhotos().stream()
                                .map(photo -> Photo.restore(photo.getId(), photo.getContentHash(),
                                        photo.getTitle(), photo.getDescription()))
                                .toList()))
                .toList();
        return new Trash(entries);
    }
}
