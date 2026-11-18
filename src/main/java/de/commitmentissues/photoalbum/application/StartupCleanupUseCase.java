package de.commitmentissues.photoalbum.application;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.PhotoId;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

/**
 * Bereinigt beim Start in dieser Reihenfolge: doppelte Foto-IDs, fällige Einträge, verwaiste Kopien
 * (A-10, A-11, ADR-002). Er ändert nur etwas, wenn alle Albumdateien und {@code trash.json} fehlerfrei
 * gelesen wurden; sonst bleiben alle App-Kopien erhalten (AK-N02.2). Fehler meldet er nicht: Die Übersicht
 * zeigt einen Lesefehler ohnehin an (AK-02.3), und beim nächsten Start läuft die Bereinigung erneut.
 */
public class StartupCleanupUseCase {

    private final AlbumRepository albums;
    private final TrashRepository trashRepository;
    private final PhotoStorage photoStorage;

    public StartupCleanupUseCase(AlbumRepository albums, TrashRepository trashRepository, PhotoStorage photoStorage) {
        this.albums = albums;
        this.trashRepository = trashRepository;
        this.photoStorage = photoStorage;
    }

    public void execute(Instant now) {
        List<Album> all;
        Trash trash;
        try {
            all = albums.findAll();
            trash = trashRepository.load();
        } catch (StorageException e) {
            return;
        }
        Set<PhotoId> photosInAlbums = DueEntryCleanup.photosInAlbums(all);
        removeDuplicates(trash, photosInAlbums);
        DueEntryCleanup.run(trash, photosInAlbums, photoStorage, now);
        try {
            trashRepository.save(trash);
            deleteOrphans(photosInAlbums, trash);
        } catch (StorageException e) {
            // Siehe Javadoc: Beim nächsten Start läuft die Bereinigung erneut.
        }
    }

    /**
     * Liegt dieselbe Foto-ID in einem Album und im Papierkorb, bleibt das Foto im Album (A-11).
     */
    private static void removeDuplicates(Trash trash, Set<PhotoId> photosInAlbums) {
        for (TrashEntry entry : trash.getEntries()) {
            List<PhotoId> duplicates = entry.getPhotos().stream()
                    .map(Photo::getId)
                    .filter(photosInAlbums::contains)
                    .toList();
            if (!duplicates.isEmpty()) {
                trash.remove(entry.getId(), duplicates);
            }
        }
    }

    /**
     * Löscht App-Kopien ohne Album- oder Papierkorbeintrag (A-11). Scheitert eine Löschung, bleibt die Kopie
     * bis zum nächsten Start liegen.
     */
    private void deleteOrphans(Set<PhotoId> photosInAlbums, Trash trash) {
        Set<PhotoId> referenced = new HashSet<>(photosInAlbums);
        trash.getEntries().forEach(entry -> entry.getPhotos().forEach(photo -> referenced.add(photo.getId())));
        for (PhotoId id : photoStorage.ids()) {
            if (!referenced.contains(id)) {
                try {
                    photoStorage.delete(id);
                } catch (StorageException e) {
                    // Die Kopie stört nicht und wird beim nächsten Start erneut gelöscht.
                }
            }
        }
    }
}
