package de.commitmentissues.photoalbum.application;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import de.commitmentissues.photoalbum.domain.TrashEntry;

/**
 * Was die Papierkorbansicht über einen Eintrag wissen muss (A-08): Kachel oder einzelnes Foto,
 * Ursprungsalbum, Löschzeitpunkt, Resttage und Fotos. {@code due} ist wahr, wenn die Frist abgelaufen
 * ist, die Bereinigung aber gescheitert ist (AK-SR01.6).
 */
public record TrashEntrySummary(UUID id, boolean album, String originAlbumName, Instant deletedAt,
        int remainingDays, boolean due, List<PhotoSummary> photos) {

    public TrashEntrySummary {
        photos = List.copyOf(photos);
    }

    static TrashEntrySummary of(TrashEntry entry, Instant now) {
        List<PhotoSummary> photos = entry.getPhotos().stream().map(PhotoSummary::of).toList();
        return new TrashEntrySummary(entry.getId().value(), entry.isAlbum(), entry.getOriginAlbumName(),
                entry.getDeletedAt(), entry.remainingDays(now), entry.isDue(now), photos);
    }

    public int photoCount() {
        return photos.size();
    }
}
