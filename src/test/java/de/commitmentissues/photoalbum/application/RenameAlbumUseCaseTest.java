package de.commitmentissues.photoalbum.application;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.Album;

class RenameAlbumUseCaseTest {

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final RenameAlbumUseCase useCase = new RenameAlbumUseCase(albums);

    @Test
    @DisplayName("FA-03: Ein Album wird umbenannt und gespeichert")
    void renamesAndSavesAlbum() {
        Album urlaub = new Album("Urlaub");
        albums.save(urlaub);

        Result<AlbumSummary> result = useCase.execute(urlaub.getId().value(), " Sommer ");

        assertTrue(result.isSuccess());
        assertEquals("Sommer", result.getValue().name());
        assertEquals(List.of("Sommer"), albums.names());
    }

    @Test
    @DisplayName("AK-03.1: Die Schreibweise des eigenen Namens darf sich ändern")
    void changesCaseOfOwnName() {
        Album urlaub = new Album("Urlaub");
        albums.save(urlaub);

        useCase.execute(urlaub.getId().value(), "urlaub");

        assertEquals(List.of("urlaub"), albums.names());
    }

    @Test
    @DisplayName("AK-03.2: Ein leerer Name wird mit Meldung abgelehnt, der bisherige Name bleibt")
    void rejectsBlankName() {
        Album urlaub = new Album("Urlaub");
        albums.save(urlaub);

        Result<AlbumSummary> result = useCase.execute(urlaub.getId().value(), " ");

        assertEquals("Der Albumname darf nicht leer sein.", result.getMessage());
        assertEquals(List.of("Urlaub"), albums.names());
    }

    @Test
    @DisplayName("AK-03.3: Der Name eines anderen Albums wird abgelehnt, beide Alben bleiben unverändert")
    void rejectsNameOfAnotherAlbum() {
        albums.save(new Album("Urlaub"));
        Album party = new Album("Party");
        albums.save(party);

        Result<AlbumSummary> result = useCase.execute(party.getId().value(), "urlaub");

        assertEquals("Dieser Albumname ist bereits vergeben.", result.getMessage());
        assertEquals(List.of("Urlaub", "Party"), albums.names());
    }

    @Test
    @DisplayName("AK-03.4: Ein zu langer Name wird mit Meldung abgelehnt, der bisherige Name bleibt")
    void rejectsTooLongName() {
        Album urlaub = new Album("Urlaub");
        albums.save(urlaub);

        Result<AlbumSummary> result = useCase.execute(urlaub.getId().value(), "a".repeat(51));

        assertEquals("Der Albumname darf höchstens 50 Zeichen lang sein.", result.getMessage());
        assertEquals(List.of("Urlaub"), albums.names());
    }

    @Test
    @DisplayName("AK-03.5: Bei einem Speicherfehler wird der Fehler gemeldet, der bisherige Name bleibt")
    void reportsSaveFailureAndKeepsOldName() {
        Album urlaub = new Album("Urlaub");
        albums.save(urlaub);
        albums.failOnSave = true;

        Result<AlbumSummary> result = useCase.execute(urlaub.getId().value(), "Sommer");

        assertEquals("Das Album konnte nicht gespeichert werden.", result.getMessage());
        assertEquals(List.of("Urlaub"), albums.names());
    }

    @Test
    @DisplayName("Ein unbekanntes Album wird mit Meldung abgelehnt")
    void reportsUnknownAlbum() {
        albums.save(new Album("Urlaub"));

        Result<AlbumSummary> result = useCase.execute(UUID.randomUUID(), "Sommer");

        assertEquals("Das Album wurde nicht gefunden.", result.getMessage());
        assertEquals(List.of("Urlaub"), albums.names());
    }
}
