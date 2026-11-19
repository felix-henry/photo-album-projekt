package de.commitmentissues.photoalbum.integration;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.commitmentissues.photoalbum.application.AddPhotoUseCase;
import de.commitmentissues.photoalbum.application.AlbumDetails;
import de.commitmentissues.photoalbum.application.AlbumSummary;
import de.commitmentissues.photoalbum.application.CreateAlbumUseCase;
import de.commitmentissues.photoalbum.application.DeleteAlbumUseCase;
import de.commitmentissues.photoalbum.application.EditPhotoTextsUseCase;
import de.commitmentissues.photoalbum.application.LoadPhotoUseCase;
import de.commitmentissues.photoalbum.application.OpenAlbumUseCase;
import de.commitmentissues.photoalbum.application.PhotoSummary;
import de.commitmentissues.photoalbum.application.RestoreTrashEntryUseCase;
import de.commitmentissues.photoalbum.application.Result;
import de.commitmentissues.photoalbum.domain.TrashEntry;
import de.commitmentissues.photoalbum.infrastructure.FileAlbumRepository;
import de.commitmentissues.photoalbum.infrastructure.FilePhotoStorage;
import de.commitmentissues.photoalbum.infrastructure.FileTrashRepository;
import de.commitmentissues.photoalbum.infrastructure.Sha256ImageHasher;

/**
 * Integrationstests: Use Cases mit den echten Dateiadaptern in einem temporären Datenordner.
 * Ein „Neustart“ heißt, dass neue Adapter-Objekte die Daten von der Platte lesen.
 */
@Tag("integration")
class PersistenceIntegrationTest {

    private static final Clock CLOCK = Clock.fixed(Instant.parse("2026-11-18T10:00:00Z"), ZoneOffset.UTC);

    @TempDir
    Path dataDirectory;

    @TempDir
    Path pictures;

    @Test
    @DisplayName("AK-06.1, NFA-02: Ein bytegleiches Bild wird auch nach einem Neustart als Duplikat erkannt, das vorhandene Foto bleibt unverändert")
    void duplicateIsDetectedAfterRestart() throws IOException {
        UUID albumId = new CreateAlbumUseCase(new FileAlbumRepository(dataDirectory)).execute("Urlaub").getValue().id();
        Path original = writePng("strand.png");
        UUID photoId = addPhotoUseCase().execute(albumId, original).getValue().id();
        new EditPhotoTextsUseCase(new FileAlbumRepository(dataDirectory))
                .execute(albumId, photoId, "Strand am Morgen", "Erster Urlaubstag");
        Path renamedCopy = Files.copy(original, pictures.resolve("kopie.jpg"));

        Result<PhotoSummary> result = addPhotoUseCase().execute(albumId, renamedCopy);

        assertEquals("Dieses Bild befindet sich schon in diesem Album", result.getMessage());
        assertEquals(List.of(new PhotoSummary(photoId, "Strand am Morgen", "Erster Urlaubstag")),
                openAlbum(albumId).photos());
        assertEquals(1, new FilePhotoStorage(dataDirectory).ids().size());
    }

    @Test
    @DisplayName("AK-04.3, NFA-02: Ein gelöschtes Album lässt sich nach einem Neustart mit Name und Bild wiederherstellen")
    void deletedAlbumIsRestoredAfterRestart() throws IOException {
        UUID albumId = new CreateAlbumUseCase(new FileAlbumRepository(dataDirectory)).execute("Urlaub").getValue().id();
        Path original = writePng("strand.png");
        UUID photoId = addPhotoUseCase().execute(albumId, original).getValue().id();
        new DeleteAlbumUseCase(new FileAlbumRepository(dataDirectory), new FileTrashRepository(dataDirectory), CLOCK)
                .execute(albumId);
        TrashEntry tile = new FileTrashRepository(dataDirectory).load().getEntries().get(0);

        Result<AlbumSummary> result = new RestoreTrashEntryUseCase(new FileAlbumRepository(dataDirectory),
                new FileTrashRepository(dataDirectory), new FilePhotoStorage(dataDirectory), CLOCK)
                .execute(tile.getId().value());

        assertTrue(result.isSuccess());
        AlbumDetails restored = openAlbum(result.getValue().id());
        assertEquals("Urlaub", restored.name());
        assertEquals(List.of(photoId), restored.photos().stream().map(PhotoSummary::id).toList());
        assertArrayEquals(Files.readAllBytes(original),
                new LoadPhotoUseCase(new FilePhotoStorage(dataDirectory)).execute(photoId).getValue());
        assertEquals(List.of(), new FileTrashRepository(dataDirectory).load().getEntries());
    }

    private AddPhotoUseCase addPhotoUseCase() {
        return new AddPhotoUseCase(new FileAlbumRepository(dataDirectory), new Sha256ImageHasher(),
                new FilePhotoStorage(dataDirectory));
    }

    private AlbumDetails openAlbum(UUID albumId) {
        return new OpenAlbumUseCase(new FileAlbumRepository(dataDirectory)).execute(albumId).getValue();
    }

    private Path writePng(String name) throws IOException {
        BufferedImage image = new BufferedImage(4, 3, BufferedImage.TYPE_INT_RGB);
        image.setRGB(1, 1, 0x3366CC);
        Path file = pictures.resolve(name);
        assertTrue(ImageIO.write(image, "png", file.toFile()));
        return file;
    }
}
