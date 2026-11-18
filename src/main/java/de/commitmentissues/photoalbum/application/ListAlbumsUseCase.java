package de.commitmentissues.photoalbum.application;

import java.util.Comparator;
import java.util.List;

/**
 * Alben anzeigen (FA-02): liefert alle Alben alphabetisch nach Namen.
 */
public class ListAlbumsUseCase {

    private final AlbumRepository albums;

    public ListAlbumsUseCase(AlbumRepository albums) {
        this.albums = albums;
    }

    public Result<List<AlbumSummary>> execute() {
        try {
            List<AlbumSummary> summaries = albums.findAll().stream()
                    .map(AlbumSummary::of)
                    .sorted(Comparator.comparing(AlbumSummary::name, String.CASE_INSENSITIVE_ORDER))
                    .toList();
            return Result.success(summaries);
        } catch (StorageException e) {
            return Result.failure("Die Alben konnten nicht geladen werden.");
        }
    }
}
