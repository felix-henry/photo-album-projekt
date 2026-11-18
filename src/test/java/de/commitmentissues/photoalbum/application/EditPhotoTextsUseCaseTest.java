package de.commitmentissues.photoalbum.application;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.PhotoId;

class EditPhotoTextsUseCaseTest {

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final EditPhotoTextsUseCase useCase = new EditPhotoTextsUseCase(albums);
    private final Album urlaub = new Album("Urlaub");
    private final Photo strand = Photo.restore(PhotoId.newId(), "hash-strand", "Strand", "Sommer 2025");

    @BeforeEach
    void setUp() {
        urlaub.addPhoto(strand);
        albums.save(urlaub);
    }

    @Test
    @DisplayName("AK-10.1: Neuer Titel und neue Beschreibung werden gespeichert und sind danach abrufbar")
    void savesBothTexts() {
        Result<PhotoSummary> result = execute("Am Strand", "Sommer 2026");

        assertTrue(result.isSuccess());
        assertEquals("Am Strand", result.getValue().title());
        assertEquals("Am Strand", storedPhoto().getTitle());
        assertEquals("Sommer 2026", storedPhoto().getDescription());
    }

    @Test
    @DisplayName("AK-10.3: Ein zu langer Titel wird gemeldet, der gespeicherte Titel bleibt")
    void rejectsTooLongTitle() {
        Result<PhotoSummary> result = execute("a".repeat(101), "Sommer 2025");

        assertEquals("Der Titel darf höchstens 100 Zeichen lang sein.", result.getMessage());
        assertEquals("Strand", storedPhoto().getTitle());
    }

    @Test
    @DisplayName("AK-10.5: Eine zu lange Beschreibung wird gemeldet, die gespeicherte Beschreibung bleibt")
    void rejectsTooLongDescription() {
        Result<PhotoSummary> result = execute("Strand", "b".repeat(501));

        assertEquals("Die Beschreibung darf höchstens 500 Zeichen lang sein.", result.getMessage());
        assertEquals("Sommer 2025", storedPhoto().getDescription());
    }

    @Test
    @DisplayName("ADR-002: Ist nur die Beschreibung ungültig, wird auch der gültige neue Titel nicht gespeichert")
    void savesNothingIfOneTextIsInvalid() {
        execute("Am Strand", "b".repeat(501));

        assertEquals("Strand", storedPhoto().getTitle());
    }

    @Test
    @DisplayName("Bei einem Speicherfehler wird der Fehler gemeldet, die gespeicherten Texte bleiben")
    void reportsSaveFailure() {
        albums.failOnSave = true;

        Result<PhotoSummary> result = execute("Am Strand", "Sommer 2026");

        assertEquals("Titel und Beschreibung konnten nicht gespeichert werden.", result.getMessage());
        assertEquals("Strand", storedPhoto().getTitle());
        assertEquals("Sommer 2025", storedPhoto().getDescription());
    }

    @Test
    @DisplayName("Ein unbekanntes Foto wird mit Meldung abgelehnt")
    void reportsUnknownPhoto() {
        Result<PhotoSummary> result = useCase.execute(urlaub.getId().value(), UUID.randomUUID(), "Titel", "");

        assertEquals("Das Foto wurde nicht gefunden.", result.getMessage());
    }

    @Test
    @DisplayName("Ein unbekanntes Album wird mit Meldung abgelehnt")
    void reportsUnknownAlbum() {
        Result<PhotoSummary> result = useCase.execute(UUID.randomUUID(), strand.getId().value(), "Titel", "");

        assertEquals("Das Album wurde nicht gefunden.", result.getMessage());
    }

    @Test
    @DisplayName("Ein Lesefehler beim Laden des Albums wird gemeldet")
    void reportsLoadFailure() {
        albums.failOnLoad = true;

        Result<PhotoSummary> result = execute("Am Strand", "Sommer 2026");

        assertEquals("Das Album konnte nicht geladen werden.", result.getMessage());
    }

    private Result<PhotoSummary> execute(String title, String description) {
        return useCase.execute(urlaub.getId().value(), strand.getId().value(), title, description);
    }

    private Photo storedPhoto() {
        return albums.findById(urlaub.getId()).orElseThrow().getPhotos().get(0);
    }
}
