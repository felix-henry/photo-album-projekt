package de.commitmentissues.photoalbum.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.PhotoId;

/**
 * Ein Foto innerhalb einer Album- oder Papierkorbdatei. Die App-Kopie liegt unter derselben ID (ADR-001).
 * Dateien aus der Zeit vor FA-10 haben kein {@code title} und keine {@code description};
 * Gson liefert dann {@code null}.
 */
record PhotoFile(String id, String contentHash, String title, String description) {

    static PhotoFile of(Photo photo) {
        return new PhotoFile(photo.getId().value().toString(), photo.getContentHash(),
                photo.getTitle(), photo.getDescription());
    }

    /**
     * Wandelt gespeicherte Fotos zurück. Eine fehlende Liste bedeutet keine Fotos, fehlende Texte gelten
     * als leer. Fehlen ID oder Inhaltshash, ist die Datei nicht lesbar.
     */
    static List<Photo> toPhotos(List<PhotoFile> files) {
        if (files == null) {
            return List.of();
        }
        List<Photo> photos = new ArrayList<>();
        for (PhotoFile file : files) {
            if (file == null || file.id() == null || file.contentHash() == null) {
                throw new IllegalArgumentException("Ein gespeichertes Foto ist unvollständig.");
            }
            photos.add(Photo.restore(new PhotoId(UUID.fromString(file.id())), file.contentHash(),
                    textOrEmpty(file.title()), textOrEmpty(file.description())));
        }
        return photos;
    }

    private static String textOrEmpty(String text) {
        return text == null ? "" : text;
    }
}
