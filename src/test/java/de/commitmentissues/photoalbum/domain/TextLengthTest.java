package de.commitmentissues.photoalbum.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TextLengthTest {

    @Test
    @DisplayName("A-04: Jeder Buchstabe zählt einmal")
    void countsLetters() {
        assertEquals(6, TextLength.count("Urlaub"));
    }

    @Test
    @DisplayName("A-04: Ein aus zwei Teilen zusammengesetzter Umlaut zählt einmal")
    void countsCombinedUmlautOnce() {
        // "u" + Trema-Zeichen ergibt ein sichtbares "ü"; String.length() wäre 6
        assertEquals(5, TextLength.count("Gru\u0308ße"));
    }

    @Test
    @DisplayName("A-04: Ein Emoji zählt einmal")
    void countsEmojiOnce() {
        // String.length() wäre 2
        assertEquals(1, TextLength.count("📷"));
    }

    @Test
    @DisplayName("A-04: Ein zusammengesetztes Emoji zählt einmal")
    void countsCombinedEmojiOnce() {
        // Familie aus drei Personen, intern 8 char-Werte
        assertEquals(1, TextLength.count("👨‍👩‍👧"));
    }

    @Test
    @DisplayName("A-04: Ein leerer Text hat 0 Zeichen")
    void countsEmptyTextAsZero() {
        assertEquals(0, TextLength.count(""));
    }
}
