package de.commitmentissues.photoalbum.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.commitmentissues.photoalbum.application.AddPhotoUseCase;
import de.commitmentissues.photoalbum.application.AlbumDetails;
import de.commitmentissues.photoalbum.application.CreateAlbumUseCase;
import de.commitmentissues.photoalbum.application.LoadPhotoUseCase;
import de.commitmentissues.photoalbum.application.OpenAlbumUseCase;
import de.commitmentissues.photoalbum.application.PhotoSummary;
import de.commitmentissues.photoalbum.application.Result;
import de.commitmentissues.photoalbum.infrastructure.FileAlbumRepository;
import de.commitmentissues.photoalbum.infrastructure.FilePhotoStorage;
import de.commitmentissues.photoalbum.infrastructure.Sha256ImageHasher;

/**
 * Messaufbau für NFA-03 und AK-N03.1 (TESTING.md, Abschnitt 7). Läuft nicht im normalen Build, sondern nur mit
 * {@code ./mvnw test -Dtest=OpenAlbumPerformanceTest -DexcludedGroups=}.
 *
 * <p>Gemessen wird, was {@link AlbumWindow} beim Öffnen ausführt, bevor Fotoanzahl und erstes Bild sichtbar sind:
 * Album laden, erstes Bild über den {@link ImageBuffer} laden und auf Anzeigegröße entschlüsseln. Das Zeichnen
 * durch Swing ist nicht enthalten.
 */
@Tag("performance")
class OpenAlbumPerformanceTest {

    private static final Path DATASET = Path.of("target", "performance-dataset");
    private static final int PHOTOS = 100;
    private static final int JPEG_COUNT = 90;
    private static final long LIMIT_MILLIS = 1000;
    private static final int REPEATS = 10;

    @TempDir
    Path dataDirectory;

    @Test
    @DisplayName("AK-N03.1: Fotoanzahl und erstes Bild eines Albums mit 100 Fotos sind in höchstens 1 Sekunde bereit")
    void opensAlbumWithHundredPhotosWithinOneSecond() throws IOException {
        List<Path> files = dataset();
        UUID albumId = albumWith(files);

        List<Long> millis = new ArrayList<>();
        for (int run = 0; run <= REPEATS; run++) {
            millis.add(measureOpening(albumId));
        }

        long first = millis.get(0);
        List<Long> repeated = millis.subList(1, millis.size()).stream().sorted().toList();
        long median = repeated.get(repeated.size() / 2);
        long max = repeated.get(repeated.size() - 1);
        report(files, first, median, max);
        assertTrue(first <= LIMIT_MILLIS, "Erstes Öffnen dauerte " + first + " ms");
        assertTrue(max <= LIMIT_MILLIS, "Langsamstes wiederholtes Öffnen dauerte " + max + " ms");
    }

    /**
     * Ein „Neustart“ je Messung: neue Adapter lesen das Album von der Platte, ein neuer Puffer ist leer.
     */
    private long measureOpening(UUID albumId) {
        OpenAlbumUseCase openAlbum = new OpenAlbumUseCase(new FileAlbumRepository(dataDirectory));
        LoadPhotoUseCase loadPhoto = new LoadPhotoUseCase(new FilePhotoStorage(dataDirectory));
        ImageBuffer buffer = new ImageBuffer(id -> decode(loadPhoto.execute(id)));

        long start = System.nanoTime();
        AlbumDetails album = openAlbum.execute(albumId).getValue();
        List<UUID> ids = album.photos().stream().map(PhotoSummary::id).toList();
        Result<BufferedImage> firstImage = buffer.show(ids, 0);
        long elapsed = (System.nanoTime() - start) / 1_000_000;

        assertEquals(PHOTOS, ids.size());
        assertTrue(firstImage.isSuccess(), firstImage.getMessage());
        buffer.preloadNeighbours(ids, 0);
        assertTrue(buffer.size() <= ImageBuffer.CAPACITY);
        return elapsed;
    }

    private static Result<BufferedImage> decode(Result<byte[]> data) {
        if (!data.isSuccess()) {
            return Result.failure(data.getMessage());
        }
        return ImageDecoder.decode(data.getValue(), AlbumWindow.IMAGE_WIDTH, AlbumWindow.IMAGE_HEIGHT);
    }

    private UUID albumWith(List<Path> files) {
        FileAlbumRepository albums = new FileAlbumRepository(dataDirectory);
        UUID albumId = new CreateAlbumUseCase(albums).execute("Messung").getValue().id();
        AddPhotoUseCase addPhoto = new AddPhotoUseCase(albums, new Sha256ImageHasher(),
                new FilePhotoStorage(dataDirectory));
        for (Path file : files) {
            Result<PhotoSummary> added = addPhoto.execute(albumId, file);
            assertTrue(added.isSuccess(), file + ": " + added.getMessage());
        }
        return albumId;
    }

    /**
     * Fester Datensatz, auf jedem Laptop gleich erzeugt: 90 JPEG mit 4000 × 3000 Pixeln und 10 PNG mit
     * 1920 × 1080 Pixeln. Er wird einmal unter {@code target/performance-dataset} angelegt und wiederverwendet.
     */
    private static List<Path> dataset() throws IOException {
        Files.createDirectories(DATASET);
        List<Path> files = new ArrayList<>();
        for (int i = 1; i <= PHOTOS; i++) {
            boolean jpeg = i <= JPEG_COUNT;
            Path file = DATASET.resolve(String.format(Locale.ROOT, "foto-%03d.%s", i, jpeg ? "jpg" : "png"));
            if (Files.notExists(file)) {
                BufferedImage image = jpeg ? picture(4000, 3000, i) : picture(1920, 1080, i);
                assertTrue(ImageIO.write(image, jpeg ? "jpeg" : "png", file.toFile()));
            }
            files.add(file);
        }
        return files;
    }

    /**
     * Verlauf mit zufälligem Rauschen in 8 × 8-Blöcken, damit die Dateien ähnlich groß wie Kamerafotos werden.
     */
    private static BufferedImage picture(int width, int height, int seed) {
        Random random = new Random(seed);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y += 8) {
            for (int x = 0; x < width; x += 8) {
                int red = (255 * x / width + random.nextInt(60)) % 256;
                int green = (255 * y / height + random.nextInt(60)) % 256;
                int blue = (seed * 37 + random.nextInt(60)) % 256;
                int rgb = (red << 16) | (green << 8) | blue;
                for (int dy = 0; dy < 8 && y + dy < height; dy++) {
                    for (int dx = 0; dx < 8 && x + dx < width; dx++) {
                        image.setRGB(x + dx, y + dy, rgb ^ random.nextInt(16));
                    }
                }
            }
        }
        return image;
    }

    private static void report(List<Path> files, long first, long median, long max) throws IOException {
        com.sun.management.OperatingSystemMXBean os =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalBytes = 0;
        long smallest = Long.MAX_VALUE;
        long largest = 0;
        for (Path file : files) {
            long size = Files.size(file);
            totalBytes += size;
            smallest = Math.min(smallest, size);
            largest = Math.max(largest, size);
        }
        System.out.printf(Locale.ROOT, """

                ===== NFA-03 Messung =====
                Betriebssystem: %s %s (%s)
                Prozessorkerne: %d, RAM: %.1f GiB, Java: %s
                Datensatz: %d Dateien, %.1f MiB gesamt, %.1f bis %.1f MiB, Pruefsumme %s
                Erstes Oeffnen: %d ms
                Wiederholt (%d Laeufe): Median %d ms, Maximum %d ms
                Grenze: %d ms
                ==========================
                """,
                System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"),
                Runtime.getRuntime().availableProcessors(), os.getTotalMemorySize() / (1024.0 * 1024 * 1024),
                System.getProperty("java.version"),
                files.size(), totalBytes / (1024.0 * 1024), smallest / (1024.0 * 1024), largest / (1024.0 * 1024),
                checksum(files), first, REPEATS, median, max, LIMIT_MILLIS);
    }

    private static String checksum(List<Path> files) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (Path file : files) {
                digest.update(Files.readAllBytes(file));
            }
            return HexFormat.of().formatHex(digest.digest()).substring(0, 16);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
