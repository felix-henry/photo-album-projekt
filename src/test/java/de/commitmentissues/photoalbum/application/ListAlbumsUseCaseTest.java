package de.commitmentissues.photoalbum.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.commitmentissues.photoalbum.domain.Album;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ListAlbumsUseCaseTest {

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final ListAlbumsUseCase useCase = new ListAlbumsUseCase(albums);

    @Test
    @DisplayName("AK-02.1: Alle Alben werden alphabetisch angezeigt")
    void listsAllAlbumsSortedByName() {
        albums.save(new Album("Urlaub"));
        albums.save(new Album("party"));

        Result<List<AlbumSummary>> result = useCase.execute();

        List<String> names = result.getValue().stream().map(AlbumSummary::name).toList();
        assertEquals(List.of("party", "Urlaub"), names);
    }

    @Test
    @DisplayName("AK-02.2: Ohne Alben ist die Übersicht leer")
    void listsNothingWithoutAlbums() {
        Result<List<AlbumSummary>> result = useCase.execute();

        assertTrue(result.isSuccess());
        assertEquals(List.of(), result.getValue());
    }

    @Test
    @DisplayName("AK-02.3: Ein Lesefehler wird gemeldet, die gespeicherten Daten bleiben unverändert")
    void reportsLoadFailure() {
        albums.save(new Album("Urlaub"));
        albums.failOnLoad = true;

        Result<List<AlbumSummary>> result = useCase.execute();

        assertEquals("Die Alben konnten nicht geladen werden.", result.getMessage());
        assertEquals(List.of("Urlaub"), albums.names());
    }
}
