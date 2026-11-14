package de.commitmentissues.photoalbum.infrastructure;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import de.commitmentissues.photoalbum.domain.AlbumId;
import de.commitmentissues.photoalbum.domain.TrashEntry;
import de.commitmentissues.photoalbum.domain.TrashEntryId;

/**
 * Ein Eintrag in {@code trash.json}. Der Löschzeitpunkt steht als ISO-8601-Text in der Datei,
 * etwa {@code 2026-11-14T12:00:00Z}.
 */
record TrashEntryFile(String id, String deletedAt, String originAlbumId, String originAlbumName,
        Boolean album, List<PhotoFile> photos) {

    static TrashEntryFile of(TrashEntry entry) {
        return new TrashEntryFile(entry.getId().value().toString(), entry.getDeletedAt().toString(),
                entry.getOriginAlbumId().value().toString(), entry.getOriginAlbumName(), entry.isAlbum(),
                entry.getPhotos().stream().map(PhotoFile::of).toList());
    }

    TrashEntry toEntry() {
        if (id == null || deletedAt == null || originAlbumId == null || originAlbumName == null || album == null) {
            throw new IllegalArgumentException("Ein Papierkorbeintrag ist unvollständig.");
        }
        return TrashEntry.restore(new TrashEntryId(UUID.fromString(id)), Instant.parse(deletedAt),
                new AlbumId(UUID.fromString(originAlbumId)), originAlbumName, album, PhotoFile.toPhotos(photos));
    }
}
