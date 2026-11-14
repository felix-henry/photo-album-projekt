package de.commitmentissues.photoalbum.application;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.Photo;

class OpenAlbumUseCaseTest {

    private final InMemoryAlbumRepository albums = new InMemoryAlbumRepository();
    private final OpenAlbumUseCase useCase = new OpenAlbumUseCase(albums);

    @Test
    @DisplayName("AK-08.1: Das geöffnete Album nennt Namen und Fotos in ihrer Reihenfolge")
    void opensAlbumWithPhotos() {
        Album urlaub = new Album("Urlaub");
        Photo strand = new Photo("hash-strand");
        Photo berg = new Photo("hash-berg");
        urlaub.addPhoto(strand);
        urlaub.addPhoto(berg);
        albums.save(urlaub);

        Result<AlbumDetails> result = useCase.execute(urlaub.getId().value());

        assertEquals("Urlaub", result.getValue().name());
        List<PhotoSummary> expected = List.of(
                new PhotoSummary(strand.getId().value()),
                new PhotoSummary(berg.getId().value()));
        assertEquals(expected, result.getValue().photos());
    }

    @Test
    @DisplayName("AK-08.2: Ein Album ohne Fotos wird ohne Fotos geöffnet")
    void opensEmptyAlbum() {
        Album leer = new Album("Best of");
        albums.save(leer);

        Result<AlbumDetails> result = useCase.execute(leer.getId().value());

        assertTrue(result.isSuccess());
        assertEquals(List.of(), result.getValue().photos());
    }

    @Test
    @DisplayName("Ein unbekanntes Album wird mit Meldung abgelehnt")
    void reportsUnknownAlbum() {
        Result<AlbumDetails> result = useCase.execute(UUID.randomUUID());

        assertEquals("Das Album wurde nicht gefunden.", result.getMessage());
    }

    @Test
    @DisplayName("Ein Lesefehler beim Öffnen wird gemeldet")
    void reportsLoadFailure() {
        Album urlaub = new Album("Urlaub");
        albums.save(urlaub);
        albums.failOnLoad = true;

        Result<AlbumDetails> result = useCase.execute(urlaub.getId().value());

        assertEquals("Das Album konnte nicht geladen werden.", result.getMessage());
    }
}
