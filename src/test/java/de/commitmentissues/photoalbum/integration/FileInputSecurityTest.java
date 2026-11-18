package de.commitmentissues.photoalbum.integration;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.commitmentissues.photoalbum.application.AddPhotoUseCase;
import de.commitmentissues.photoalbum.application.CreateAlbumUseCase;
import de.commitmentissues.photoalbum.application.OpenAlbumUseCase;
import de.commitmentissues.photoalbum.application.PhotoSummary;
import de.commitmentissues.photoalbum.application.Result;
import de.commitmentissues.photoalbum.infrastructure.FileAlbumRepository;
import de.commitmentissues.photoalbum.infrastructure.FilePhotoStorage;
import de.commitmentissues.photoalbum.infrastructure.Sha256ImageHasher;

/**
 * Security-Fälle für Dateieingaben über den ganzen Importweg (TESTING.md, Abschnitt 6).
 * Die Fälle zu Endung, Größe und Inhalt prüft {@code Sha256ImageHasherTest} einzeln.
 */
@Tag("security")
class FileInputSecurityTest {

    @TempDir
    Path dataDirectory;

    @TempDir
    Path pictures;

    private UUID albumId;

    @BeforeEach
    void setUp() {
        albumId = new CreateAlbumUseCase(new FileAlbumRepository(dataDirectory)).execute("Urlaub").getValue().id();
    }

    @Test
    @Tag("fehlerfall")
    @DisplayName("AK-05.4: Ein Ordner mit Bildendung wird als nicht lesbar abgelehnt, nichts wird gespeichert")
    void rejectsDirectoryWithImageExtension() throws IOException {
        Path folder = Files.createDirectory(pictures.resolve("ordner.jpg"));

        Result<PhotoSummary> result = addPhoto(folder);

        assertEquals("Die Datei konnte nicht gelesen werden.", result.getMessage());
        assertEquals(0, photoCount());
        assertEquals(List.of(), filesIn(dataDirectory.resolve("photos")));
    }

    @Test
    @Tag("normalfall")
    @DisplayName("A-05: Ein Pfad mit „..“ bestimmt nicht, wo die App-Kopie liegt")
    void storesCopyOfTraversalPathOnlyUnderPhotoId() throws IOException {
        Files.createDirectory(pictures.resolve("unterordner"));
        writePng(pictures.resolve("strand.png"));
        Path traversal = pictures.resolve("unterordner").resolve("..").resolve("strand.png");

        Result<PhotoSummary> result = addPhoto(traversal);

        assertTrue(result.isSuccess());
        assertEquals(List.of(dataDirectory.resolve("photos").resolve(result.getValue().id().toString())),
                filesIn(dataDirectory.resolve("photos")));
        assertEquals(List.of(pictures.resolve("strand.png"), pictures.resolve("unterordner")), filesIn(pictures));
    }

    @Test
    @Tag("normalfall")
    @DisplayName("A-05: Sonderzeichen und Emoji im Dateinamen gehen nicht in den Namen der App-Kopie ein")
    void storesCopyOfFileWithSpecialNameUnderPhotoId() throws IOException {
        Path special = writePng(pictures.resolve("Foto; 😀 ä & $(rm).png"));

        Result<PhotoSummary> result = addPhoto(special);

        assertTrue(result.isSuccess());
        assertEquals(List.of(dataDirectory.resolve("photos").resolve(result.getValue().id().toString())),
                filesIn(dataDirectory.resolve("photos")));
    }

    private Result<PhotoSummary> addPhoto(Path file) {
        return new AddPhotoUseCase(new FileAlbumRepository(dataDirectory), new Sha256ImageHasher(),
                new FilePhotoStorage(dataDirectory)).execute(albumId, file);
    }

    private int photoCount() {
        return new OpenAlbumUseCase(new FileAlbumRepository(dataDirectory)).execute(albumId).getValue().photos().size();
    }

    private static List<Path> filesIn(Path directory) throws IOException {
        if (Files.notExists(directory)) {
            return List.of();
        }
        try (Stream<Path> files = Files.list(directory)) {
            return files.sorted().toList();
        }
    }

    private static Path writePng(Path file) throws IOException {
        BufferedImage image = new BufferedImage(4, 3, BufferedImage.TYPE_INT_RGB);
        image.setRGB(2, 1, 0xCC6633);
        assertTrue(ImageIO.write(image, "png", file.toFile()));
        return file;
    }
}
