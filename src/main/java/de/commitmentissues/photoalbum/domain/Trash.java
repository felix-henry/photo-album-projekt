package de.commitmentissues.photoalbum.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Der Papierkorb mit seinen Einträgen (A-07 bis A-10, SR-01). Beim Wiederherstellen prüft er nur die
 * Frist; die übrigen Regeln prüfen wie sonst {@link AlbumLibrary} und {@link Album} (ARCHITECTURE, Abschnitt 3).
 */
public class Trash {

    private final List<TrashEntry> entries;

    public Trash(List<TrashEntry> entries) {
        this.entries = new ArrayList<>(entries);
    }

    public List<TrashEntry> getEntries() {
        return List.copyOf(entries);
    }

    public Optional<TrashEntry> find(TrashEntryId id) {
        return entries.stream().filter(entry -> entry.getId().equals(id)).findFirst();
    }

    /**
     * Legt ein gelöschtes Album samt Fotos als eine Kachel ab (FA-04, A-07).
     */
    public TrashEntry moveAlbum(Album album, Instant now) {
        TrashEntry entry = TrashEntry.ofAlbum(album, now);
        entries.add(entry);
        return entry;
    }

    /**
     * Nimmt das Foto aus seinem Album und legt es als eigenen Eintrag ab (FA-09, A-07). Bytegleiche
     * Fotos aus verschiedenen Alben bleiben getrennte Einträge (A-08).
     */
    public TrashEntry movePhoto(Photo photo, Album from, Instant now) {
        from.removePhoto(photo.getId());
        TrashEntry entry = TrashEntry.ofPhoto(photo, from, now);
        entries.add(entry);
        return entry;
    }

    /**
     * Gibt einen noch nicht fälligen Eintrag zum Wiederherstellen heraus und nimmt ihn aus dem
     * Papierkorb. Einen fälligen lehnt er ab (A-09, A-10).
     */
    public TrashEntry take(TrashEntryId id, Instant now) {
        TrashEntry entry = find(id)
                .orElseThrow(() -> new IllegalArgumentException("Unbekannter Papierkorbeintrag: " + id));
        if (entry.isDue(now)) {
            throw new DomainException(Violation.TRASH_ENTRY_EXPIRED);
        }
        entries.remove(entry);
        return entry;
    }

    /**
     * Nennt die fälligen Einträge, ohne sie zu entfernen (A-10). Entfernt werden sie erst über
     * {@link #remove}, nachdem ihre App-Kopien gelöscht sind.
     */
    public List<TrashEntry> dueEntries(Instant now) {
        return entries.stream().filter(entry -> entry.isDue(now)).toList();
    }

    /**
     * Nimmt die gelöschten Fotos aus dem Eintrag. Nicht gelöschte bleiben im Papierkorb, ein leerer
     * Eintrag entfällt (SR-01, A-12).
     */
    public void remove(TrashEntryId id, Collection<PhotoId> deleted) {
        TrashEntry entry = find(id)
                .orElseThrow(() -> new IllegalArgumentException("Unbekannter Papierkorbeintrag: " + id));
        entry.removePhotos(deleted);
        if (entry.isEmpty()) {
            entries.remove(entry);
        }
    }
}
