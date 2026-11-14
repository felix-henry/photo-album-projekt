package de.commitmentissues.photoalbum.infrastructure;

import de.commitmentissues.photoalbum.domain.Photo;

/**
 * Ein Foto innerhalb einer Albumdatei. Die App-Kopie liegt unter derselben ID (ADR-001).
 */
record PhotoFile(String id, String contentHash) {

    static PhotoFile of(Photo photo) {
        return new PhotoFile(photo.getId().value().toString(), photo.getContentHash());
    }
}
