package de.commitmentissues.photoalbum.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
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
}
