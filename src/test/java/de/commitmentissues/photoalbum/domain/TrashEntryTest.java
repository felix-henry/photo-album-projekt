package de.commitmentissues.photoalbum.domain;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TrashEntryTest {

    private static final Instant DELETED_AT = Instant.parse("2026-11-01T10:00:00Z");
    private static final Duration RETENTION = Duration.ofHours(30 * 24);

    private final TrashEntry entry = TrashEntry.ofPhoto(new Photo("hash-strand"), new Album("Urlaub"), DELETED_AT);

    @Test
    @DisplayName("AK-SR01.6: Eine Sekunde vor Ablauf von 30 × 24 Stunden ist der Eintrag noch nicht fällig")
    void isNotDueOneSecondBeforeDeadline() {
        assertFalse(entry.isDue(DELETED_AT.plus(RETENTION).minusSeconds(1)));
    }

    @Test
    @DisplayName("AK-SR01.3: Nach genau 30 × 24 Stunden ist der Eintrag fällig")
    void isDueAfterExactlyThirtyTimesTwentyFourHours() {
        assertTrue(entry.isDue(DELETED_AT.plus(RETENTION)));
    }

    @ParameterizedTest(name = "{0} s nach dem Löschen: {1} Resttage")
    @DisplayName("A-08: Die Resttage zählen die angefangenen 24-Stunden-Zeiträume bis zur Fälligkeit")
    @CsvSource({
        "0, 30",
        "1, 30",
        "86400, 29",
        "2505600, 1",
        "2591999, 1",
        "2592000, 0"
    })
    void countsStartedDaysUntilDeadline(long secondsAfterDeletion, int expectedDays) {
        assertEquals(expectedDays, entry.remainingDays(DELETED_AT.plusSeconds(secondsAfterDeletion)));
    }
}
