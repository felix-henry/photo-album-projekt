package de.commitmentissues.photoalbum.application;

import java.util.UUID;

import de.commitmentissues.photoalbum.domain.PhotoId;

/**
 * Die App-Kopie eines Fotos zum Anzeigen laden (FA-08). Ist sie nicht lesbar, bleibt alles
 * gespeichert, wie es ist (AK-08.3).
 */
public class LoadPhotoUseCase {

    /**
     * Auch die UI zeigt diese Meldung, wenn sich geladene Bilddaten nicht als Bild darstellen lassen.
     */
    public static final String UNREADABLE = "Das Foto konnte nicht gelesen werden.";

    private final PhotoStorage photoStorage;

    public LoadPhotoUseCase(PhotoStorage photoStorage) {
        this.photoStorage = photoStorage;
    }

    public Result<byte[]> execute(UUID photoId) {
        try {
            return Result.success(photoStorage.load(new PhotoId(photoId)));
        } catch (StorageException e) {
            return Result.failure(UNREADABLE);
        }
    }
}
