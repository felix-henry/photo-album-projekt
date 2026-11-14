package de.commitmentissues.photoalbum.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PhotoTest {

    @Test
    @DisplayName("FA-10: Ein neues Foto hat keinen Titel und keine Beschreibung")
    void newPhotoHasNoTexts() {
        Photo photo = new Photo("hash-strand");

        assertEquals("", photo.getTitle());
        assertEquals("", photo.getDescription());
    }

    @Test
    @DisplayName("AK-10.1: Titel und Beschreibung lassen sich beide ändern")
    void changesBothTexts() {
        Photo photo = photoWith("Strand", "Sommer 2025");

        photo.changeTitle("Am Strand");
        photo.changeDescription("Sommer 2026");

        assertEquals("Am Strand", photo.getTitle());
        assertEquals("Sommer 2026", photo.getDescription());
    }

    @Test
    @DisplayName("AK-10.2: Ein Titel mit genau 100 Zeichen wird gespeichert, die Beschreibung bleibt leer")
    void acceptsTitleOfMaxLength() {
        Photo photo = new Photo("hash-strand");

        photo.changeTitle("a".repeat(100));

        assertEquals("a".repeat(100), photo.getTitle());
        assertEquals("", photo.getDescription());
    }

    @Test
    @DisplayName("AK-10.3: Ein Titel mit 101 Zeichen wird abgelehnt, der vorhandene Titel bleibt")
    void rejectsTooLongTitle() {
        Photo photo = photoWith("Strand", "");

        DomainException exception = assertThrows(DomainException.class,
                () -> photo.changeTitle("a".repeat(101)));

        assertEquals(Violation.PHOTO_TITLE_TOO_LONG, exception.getViolation());
        assertEquals("Strand", photo.getTitle());
    }

    @Test
    @DisplayName("AK-10.4: Eine Beschreibung mit genau 500 Zeichen wird gespeichert, der Titel bleibt leer")
    void acceptsDescriptionOfMaxLength() {
        Photo photo = new Photo("hash-strand");

        photo.changeDescription("b".repeat(500));

        assertEquals("b".repeat(500), photo.getDescription());
        assertEquals("", photo.getTitle());
    }

    @Test
    @DisplayName("AK-10.5: Eine Beschreibung mit 501 Zeichen wird abgelehnt, die vorhandene Beschreibung bleibt")
    void rejectsTooLongDescription() {
        Photo photo = photoWith("", "Sommer 2025");

        DomainException exception = assertThrows(DomainException.class,
                () -> photo.changeDescription("b".repeat(501)));

        assertEquals(Violation.PHOTO_DESCRIPTION_TOO_LONG, exception.getViolation());
        assertEquals("Sommer 2025", photo.getDescription());
    }

    @Test
    @DisplayName("AK-10.6: Wird der Titel geleert, behält die Beschreibung ihren Wert")
    void clearsTitleOnly() {
        Photo photo = photoWith("Strand", "Sommer 2025");

        photo.changeTitle("");

        assertEquals("", photo.getTitle());
        assertEquals("Sommer 2025", photo.getDescription());
    }

    @Test
    @DisplayName("AK-10.6: Wird die Beschreibung geleert, behält der Titel seinen Wert")
    void clearsDescriptionOnly() {
        Photo photo = photoWith("Strand", "Sommer 2025");

        photo.changeDescription("");

        assertEquals("", photo.getDescription());
        assertEquals("Strand", photo.getTitle());
    }

    @Test
    @DisplayName("A-04: Ein Emoji zählt als ein Zeichen, 99 Buchstaben und ein Emoji sind ein gültiger Titel")
    void countsEmojiAsOneCharacter() {
        Photo photo = new Photo("hash-strand");

        photo.changeTitle("a".repeat(99) + "📷");

        assertEquals("a".repeat(99) + "📷", photo.getTitle());
    }

    private static Photo photoWith(String title, String description) {
        return Photo.restore(PhotoId.newId(), "hash-strand", title, description);
    }
}
