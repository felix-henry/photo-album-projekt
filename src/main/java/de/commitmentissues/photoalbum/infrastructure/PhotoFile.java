package de.commitmentissues.photoalbum.infrastructure;

import de.commitmentissues.photoalbum.domain.Photo;

/**
 * Ein Foto innerhalb einer Albumdatei. Die App-Kopie liegt unter derselben ID (ADR-001).
 * Dateien aus der Zeit vor FA-10 haben kein {@code title} und keine {@code description};
 * Gson liefert dann {@code null}.
 */
record PhotoFile(String id, String contentHash, String title, String description) {

    static PhotoFile of(Photo photo) {
        return new PhotoFile(photo.getId().value().toString(), photo.getContentHash(),
                photo.getTitle(), photo.getDescription());
    }
}
