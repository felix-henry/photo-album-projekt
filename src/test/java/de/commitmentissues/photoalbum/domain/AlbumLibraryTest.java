package de.commitmentissues.photoalbum.domain;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AlbumLibraryTest {

    @Test
    @DisplayName("FA-01: Ein Album mit freiem Namen wird aufgenommen")
    void addsAlbumWithFreeName() {
        Album urlaub = new Album("Urlaub");
        AlbumLibrary library = new AlbumLibrary(List.of(urlaub));
        Album party = new Album("Party");

        library.add(party);

        assertEquals(List.of(urlaub, party), library.getAlbums());
    }

    @Test
    @DisplayName("AK-01.7: Ein vergebener Name in anderer Schreibweise wird abgelehnt")
    void rejectsTakenNameIgnoringCase() {
        Album urlaub = new Album("Urlaub");
        AlbumLibrary library = new AlbumLibrary(List.of(urlaub));
        Album duplicate = new Album(" urlaub ");

        DomainException exception = assertThrows(DomainException.class, () -> library.add(duplicate));

        assertEquals(Violation.ALBUM_NAME_TAKEN, exception.getViolation());
    }

    @Test
    @DisplayName("AK-01.7: Nach der Ablehnung enthält die Library nur das bisherige Album")
    void keepsAlbumsUnchangedAfterRejection() {
        Album urlaub = new Album("Urlaub");
        AlbumLibrary library = new AlbumLibrary(List.of(urlaub));

        assertThrows(DomainException.class, () -> library.add(new Album("URLAUB")));

        assertEquals(List.of(urlaub), library.getAlbums());
    }

    @Test
    @DisplayName("FA-03: Ein Album wird in einen freien Namen umbenannt")
    void renamesAlbumToFreeName() {
        Album urlaub = new Album("Urlaub");
        AlbumLibrary library = new AlbumLibrary(List.of(urlaub, new Album("Party")));

        library.rename(urlaub, " Sommer 2026 ");

        assertEquals("Sommer 2026", urlaub.getName());
    }

    @Test
    @DisplayName("AK-03.1: Die Schreibweise des eigenen Namens darf sich ändern")
    void renamesToOwnNameInOtherCase() {
        Album urlaub = new Album("Urlaub");
        AlbumLibrary library = new AlbumLibrary(List.of(urlaub));

        library.rename(urlaub, "urlaub");

        assertEquals("urlaub", urlaub.getName());
    }

    @Test
    @DisplayName("AK-03.2: Ein leerer Name wird abgelehnt, der bisherige Name bleibt")
    void rejectsBlankNameAndKeepsOldName() {
        Album urlaub = new Album("Urlaub");
        AlbumLibrary library = new AlbumLibrary(List.of(urlaub));

        DomainException exception = assertThrows(DomainException.class, () -> library.rename(urlaub, "  "));

        assertEquals(Violation.ALBUM_NAME_EMPTY, exception.getViolation());
        assertEquals("Urlaub", urlaub.getName());
    }

    @Test
    @DisplayName("AK-03.3: Der Name eines anderen Albums wird abgelehnt, beide Alben bleiben unverändert")
    void rejectsNameOfAnotherAlbum() {
        Album urlaub = new Album("Urlaub");
        Album party = new Album("Party");
        AlbumLibrary library = new AlbumLibrary(List.of(urlaub, party));

        DomainException exception = assertThrows(DomainException.class, () -> library.rename(party, "urlaub"));

        assertEquals(Violation.ALBUM_NAME_TAKEN, exception.getViolation());
        assertEquals("Urlaub", urlaub.getName());
        assertEquals("Party", party.getName());
    }

    @Test
    @DisplayName("AK-03.4: Ein Name mit 51 Zeichen wird abgelehnt, der bisherige Name bleibt")
    void rejectsTooLongNameAndKeepsOldName() {
        Album urlaub = new Album("Urlaub");
        AlbumLibrary library = new AlbumLibrary(List.of(urlaub));

        DomainException exception = assertThrows(DomainException.class,
                () -> library.rename(urlaub, " " + "a".repeat(51) + " "));

        assertEquals(Violation.ALBUM_NAME_TOO_LONG, exception.getViolation());
        assertEquals("Urlaub", urlaub.getName());
    }
}
