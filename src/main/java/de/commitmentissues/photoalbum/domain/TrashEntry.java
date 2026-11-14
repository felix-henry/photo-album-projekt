package de.commitmentissues.photoalbum.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Ein Eintrag im Papierkorb (A-07): ein einzeln entferntes Foto oder ein gelöschtes Album als Kachel
 * mit seinen Fotos. Kennt Löschzeitpunkt und Ursprungsalbum mit ID und Namen, damit ein fehlendes
 * Album neu angelegt werden kann (A-09). Ab genau 30 × 24 Stunden nach dem Löschen ist er fällig (A-10).
 */
public class TrashEntry {

    static final Duration RETENTION = Duration.ofHours(30 * 24);
    private static final Duration DAY = Duration.ofHours(24);

    private final TrashEntryId id;
    private final Instant deletedAt;
    private final AlbumId originAlbumId;
    private final String originAlbumName;
    private final boolean album;
    private final List<Photo> photos;

    private TrashEntry(TrashEntryId id, Instant deletedAt, AlbumId originAlbumId, String originAlbumName,
            boolean album, List<Photo> photos) {
        this.id = Objects.requireNonNull(id, "id");
        this.deletedAt = Objects.requireNonNull(deletedAt, "deletedAt");
        this.originAlbumId = Objects.requireNonNull(originAlbumId, "originAlbumId");
        this.originAlbumName = Objects.requireNonNull(originAlbumName, "originAlbumName");
        this.album = album;
        this.photos = new ArrayList<>(photos);
        if (!album && this.photos.size() != 1) {
            throw new IllegalArgumentException("Ein einzeln entferntes Foto ist genau ein Foto.");
        }
    }

    static TrashEntry ofPhoto(Photo photo, Album from, Instant now) {
        return new TrashEntry(TrashEntryId.newId(), now, from.getId(), from.getName(), false, List.of(photo));
    }

    static TrashEntry ofAlbum(Album album, Instant now) {
        return new TrashEntry(TrashEntryId.newId(), now, album.getId(), album.getName(), true, album.getPhotos());
    }

    /**
     * Stellt einen gespeicherten Eintrag wieder her.
     */
    public static TrashEntry restore(TrashEntryId id, Instant deletedAt, AlbumId originAlbumId,
            String originAlbumName, boolean album, List<Photo> photos) {
        return new TrashEntry(id, deletedAt, originAlbumId, originAlbumName, album, photos);
    }

    public TrashEntryId getId() {
        return id;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public AlbumId getOriginAlbumId() {
        return originAlbumId;
    }

    public String getOriginAlbumName() {
        return originAlbumName;
    }

    /**
     * {@code true} für eine Albumkachel, {@code false} für ein einzeln entferntes Foto (A-08).
     */
    public boolean isAlbum() {
        return album;
    }

    public List<Photo> getPhotos() {
        return List.copyOf(photos);
    }

    /**
     * Fällig ab genau 30 × 24 Stunden nach dem Löschzeitpunkt (A-10).
     */
    public boolean isDue(Instant now) {
        return !now.isBefore(dueAt());
    }

    /**
     * Die angefangenen 24-Stunden-Zeiträume bis zur Fälligkeit (A-08). Ein fälliger Eintrag hat 0 Resttage.
     */
    public int remainingDays(Instant now) {
        if (isDue(now)) {
            return 0;
        }
        Duration left = Duration.between(now, dueAt());
        long fullDays = left.toDays();
        boolean startedDay = !left.equals(DAY.multipliedBy(fullDays));
        return (int) (startedDay ? fullDays + 1 : fullDays);
    }

    void removePhotos(Collection<PhotoId> deleted) {
        photos.removeIf(photo -> deleted.contains(photo.getId()));
    }

    boolean isEmpty() {
        return photos.isEmpty();
    }

    private Instant dueAt() {
        return deletedAt.plus(RETENTION);
    }
}
