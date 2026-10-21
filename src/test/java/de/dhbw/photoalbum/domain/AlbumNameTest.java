package de.dhbw.photoalbum.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.dhbw.photoalbum.domain.InvalidAlbumNameException.Reason;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AlbumNameTest {

    @Test
    @Tag("normal")
    void acceptsName() {                                        // ÄK1
        assertEquals("Urlaub 2026", new AlbumName("Urlaub 2026").value());
    }

    @Test
    @Tag("fehler")
    void stripsSurroundingSpaces() {                            // ÄK2, AK-02.4
        assertEquals("Urlaub", new AlbumName("  Urlaub  ").value());
    }

    @Test
    @Tag("normal")
    void countsComposedAccentAsOneCharacter() {                 // ÄK3, A-13
        String decomposed = "é".repeat(AlbumName.MAX_LENGTH);
        assertEquals(AlbumName.MAX_LENGTH, new AlbumName(decomposed).value().length());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t"})
    @Tag("fehler")
    void rejectsBlankName(String name) {                        // ÄK4, ÄK5, AK-02.1
        var e = assertThrows(InvalidAlbumNameException.class, () -> new AlbumName(name));
        assertEquals(Reason.EMPTY, e.reason());
    }

    @Test
    @Tag("fehler")
    void rejectsNullAsEmpty() {                                 // ÄK6
        var e = assertThrows(InvalidAlbumNameException.class, () -> new AlbumName(null));
        assertEquals(Reason.EMPTY, e.reason());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 49, AlbumName.MAX_LENGTH})
    @Tag("grenzfall")
    void acceptsLengthUpToMax(int length) {                     // AK-02.2
        assertEquals(length, new AlbumName(" " + "a".repeat(length) + " ").value().length());
    }

    @Test
    @Tag("grenzfall")
    void rejectsLengthAboveMax() {                              // ÄK7, AK-02.3
        var e = assertThrows(InvalidAlbumNameException.class,
                () -> new AlbumName("a".repeat(AlbumName.MAX_LENGTH + 1)));
        assertEquals(Reason.TOO_LONG, e.reason());
    }
}
