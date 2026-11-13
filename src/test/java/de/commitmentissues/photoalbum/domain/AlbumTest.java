package de.commitmentissues.photoalbum.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AlbumTest {

    @Test
    @DisplayName("AK-01.1: Äußere Leerzeichen werden entfernt")
    void stripsOuterSpaces() {
        Album album = new Album(" Urlaub ");

        assertEquals("Urlaub", album.getName());
    }

    @Test
    @DisplayName("A-02: Ein Name mit genau 1 Zeichen ist erlaubt")
    void acceptsNameWithOneCharacter() {
        Album album = new Album("A");

        assertEquals("A", album.getName());
    }

    @Test
    @DisplayName("AK-01.3: Ein Name nur aus Leerzeichen wird abgelehnt")
    void rejectsBlankName() {
        DomainException exception = assertThrows(DomainException.class, () -> new Album("   "));

        assertEquals(Violation.ALBUM_NAME_EMPTY, exception.getViolation());
    }

    @Test
    @DisplayName("AK-01.4: 49 Buchstaben und ein Emoji ergeben 50 Zeichen und sind erlaubt")
    void acceptsFiftyCharactersWithEmoji() {
        String name = "a".repeat(49) + "📷";

        Album album = new Album(" " + name + " ");

        assertEquals(name, album.getName());
    }

    @Test
    @DisplayName("AK-01.5: 51 Zeichen nach dem Entfernen äußerer Leerzeichen werden abgelehnt")
    void rejectsFiftyOneCharacters() {
        String name = " " + "a".repeat(51) + " ";

        DomainException exception = assertThrows(DomainException.class, () -> new Album(name));

        assertEquals(Violation.ALBUM_NAME_TOO_LONG, exception.getViolation());
    }

    @Test
    @DisplayName("NFA-02: Ein gespeichertes Album behält beim Wiederherstellen seine ID und seinen Namen")
    void restoreKeepsIdAndName() {
        AlbumId id = AlbumId.newId();

        Album album = Album.restore(id, "Urlaub");

        assertEquals(id, album.getId());
        assertEquals("Urlaub", album.getName());
    }

    @Test
    @DisplayName("A-02: Auch beim Wiederherstellen wird ein leerer Name abgelehnt")
    void restoreRejectsBlankName() {
        AlbumId id = AlbumId.newId();

        DomainException exception = assertThrows(DomainException.class, () -> Album.restore(id, " "));

        assertEquals(Violation.ALBUM_NAME_EMPTY, exception.getViolation());
    }
}
