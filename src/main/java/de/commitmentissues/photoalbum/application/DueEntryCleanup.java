package de.commitmentissues.photoalbum.application;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.PhotoId;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

/**
 * Löscht fällige Papierkorbeinträge samt App-Kopien (A-10, SR-01). Läuft vor dem Anzeigen des Papierkorbs,
 * vor einer Wiederherstellung und beim Start. Eine Kopie, auf die noch ein Album verweist, bleibt erhalten
 * (ADR-002). Scheitert eine Löschung, bleibt das Foto im Papierkorb und gesperrt (A-12, AK-SR01.6).
 */
final class DueEntryCleanup {

    private DueEntryCleanup() {
    }

    static Set<PhotoId> photosInAlbums(List<Album> albums) {
        return albums.stream()
                .flatMap(album -> album.getPhotos().stream())
                .map(Photo::getId)
                .collect(Collectors.toSet());
    }

    static void run(Trash trash, Set<PhotoId> photosInAlbums, PhotoStorage photoStorage, Instant now) {
        for (TrashEntry entry : trash.dueEntries(now)) {
            trash.remove(entry.getId(), deleteCopies(entry, photosInAlbums, photoStorage));
        }
    }

    /**
     * Löscht die App-Kopien eines Eintrags und nennt die Fotos, die danach aus dem Papierkorb dürfen: die
     * gelöschten und die, auf die noch ein Album verweist. Auch das endgültige Löschen nutzt diese Regel.
     */
    static List<PhotoId> deleteCopies(TrashEntry entry, Set<PhotoId> photosInAlbums, PhotoStorage photoStorage) {
        List<PhotoId> removable = new ArrayList<>();
        for (Photo photo : entry.getPhotos()) {
            if (photosInAlbums.contains(photo.getId()) || tryDelete(photoStorage, photo.getId())) {
                removable.add(photo.getId());
            }
        }
        return removable;
    }

    private static boolean tryDelete(PhotoStorage photoStorage, PhotoId id) {
        try {
            photoStorage.delete(id);
            return true;
        } catch (StorageException e) {
            return false;
        }
    }
}
