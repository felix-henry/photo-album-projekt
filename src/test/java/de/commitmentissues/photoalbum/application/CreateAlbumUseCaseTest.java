package de.commitmentissues.photoalbum.application;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.Album;

class CreateAlbumUseCaseTest {

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final CreateAlbumUseCase useCase = new CreateAlbumUseCase(albums);

    @Test
    @DisplayName("AK-01.1: Ein gültiges Album wird ohne äußere Leerzeichen gespeichert")
    void savesValidAlbum() {
        Result<AlbumSummary> result = useCase.execute(" Urlaub ");

        assertTrue(result.isSuccess());
        assertEquals("Urlaub", result.getValue().name());
        assertEquals(List.of("Urlaub"), albums.names());
    }

    @Test
    @DisplayName("AK-01.3: Ein leerer Name wird mit Meldung abgelehnt, nichts wird gespeichert")
    void rejectsBlankName() {
        Result<AlbumSummary> result = useCase.execute("   ");

        assertEquals("Der Albumname darf nicht leer sein.", result.getMessage());
        assertEquals(List.of(), albums.names());
    }

    @Test
    @DisplayName("AK-01.5: Ein zu langer Name wird mit Meldung abgelehnt, nichts wird gespeichert")
    void rejectsTooLongName() {
        Result<AlbumSummary> result = useCase.execute("a".repeat(51));

        assertEquals("Der Albumname darf höchstens 50 Zeichen lang sein.", result.getMessage());
        assertEquals(List.of(), albums.names());
    }

    @Test
    @DisplayName("AK-01.7: Ein vergebener Name wird mit Meldung abgelehnt, die Alben bleiben unverändert")
    void rejectsTakenName() {
        albums.save(new Album("Urlaub"));

        Result<AlbumSummary> result = useCase.execute(" urlaub ");

        assertEquals("Dieser Albumname ist bereits vergeben.", result.getMessage());
        assertEquals(List.of("Urlaub"), albums.names());
    }

    @Test
    @DisplayName("AK-01.6: Bei einem Speicherfehler wird der Fehler gemeldet, die Alben bleiben unverändert")
    void reportsSaveFailure() {
        albums.failOnSave = true;

        Result<AlbumSummary> result = useCase.execute("Urlaub");

        assertEquals("Das Album konnte nicht gespeichert werden.", result.getMessage());
        assertEquals(List.of(), albums.names());
    }

    @Test
    @DisplayName("NFA-01: Ein Lesefehler wird als Lesefehler gemeldet, die Alben bleiben unverändert")
    void reportsLoadFailure() {
        albums.save(new Album("Party"));
        albums.failOnLoad = true;

        Result<AlbumSummary> result = useCase.execute("Urlaub");

        assertEquals("Die Alben konnten nicht geladen werden.", result.getMessage());
        assertEquals(List.of("Party"), albums.names());
    }
}
