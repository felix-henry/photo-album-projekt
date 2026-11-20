package de.commitmentissues.photoalbum.application;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.Album;

/**
 * Test Design Lab, Verfahren 1: Äquivalenzklassen und Grenzwerte für den Albumnamen (A-02, A-04).
 * Die Tabelle steht in TESTING.md, Abschnitt 3.1.
 */
class AlbumNameLabTest {

    private static final String FAMILY = "👨‍👩‍👧";

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final CreateAlbumUseCase useCase = new CreateAlbumUseCase(albums);

    @Test
    @Tag("normalfall")
    @DisplayName("AK-01.1: Innere Leerzeichen bleiben erhalten, nur die äußeren werden entfernt (ÄK1)")
    void keepsInnerSpaces() {
        Result<AlbumSummary> result = useCase.execute("  Urlaub  2026 ");

        assertTrue(result.isSuccess());
        assertEquals(List.of("Urlaub  2026"), albums.names());
    }

    @Test
    @Tag("fehlerfall")
    @DisplayName("AK-01.3: Ein Name ohne jedes Zeichen wird abgelehnt, nichts wird gespeichert (ÄK2, Grenzwert 0)")
    void rejectsNameWithoutCharacters() {
        Result<AlbumSummary> result = useCase.execute("");

        assertEquals("Der Albumname darf nicht leer sein.", result.getMessage());
        assertEquals(List.of(), albums.names());
    }

    @Test
    @Tag("fehlerfall")
    @DisplayName("AK-01.3: Ein Name nur aus Tabulator und Zeilenumbruch wird abgelehnt, nichts wird gespeichert (ÄK3)")
    void rejectsNameOfTabsAndLineBreaks() {
        Result<AlbumSummary> result = useCase.execute("\t\n \t");

        assertEquals("Der Albumname darf nicht leer sein.", result.getMessage());
        assertEquals(List.of(), albums.names());
    }

    @Test
    @Tag("grenzfall")
    @DisplayName("AK-01.4: 50 zusammengesetzte Emoji zählen als 50 Zeichen und sind erlaubt (ÄK1, Grenzwert 50)")
    void acceptsFiftyComposedEmoji() {
        String name = FAMILY.repeat(50);

        Result<AlbumSummary> result = useCase.execute(name);

        assertTrue(result.isSuccess());
        assertEquals(List.of(name), albums.names());
    }

    @Test
    @Tag("grenzfall")
    @DisplayName("AK-01.5: 51 zusammengesetzte Emoji sind zu lang, nichts wird gespeichert (ÄK4, Grenzwert 51)")
    void rejectsFiftyOneComposedEmoji() {
        Result<AlbumSummary> result = useCase.execute(FAMILY.repeat(51));

        assertEquals("Der Albumname darf höchstens 50 Zeichen lang sein.", result.getMessage());
        assertEquals(List.of(), albums.names());
    }

    @Test
    @Tag("fehlerfall")
    @DisplayName("AK-01.7: Ein Name, der sich nur in der Schreibung der Umlaute unterscheidet, ist vergeben (ÄK5)")
    void rejectsNameDifferingOnlyInUmlautCase() {
        albums.save(new Album("Äpfel und Öl"));

        Result<AlbumSummary> result = useCase.execute("äPFEL UND öL");

        assertEquals("Dieser Albumname ist bereits vergeben.", result.getMessage());
        assertEquals(List.of("Äpfel und Öl"), albums.names());
    }
}
