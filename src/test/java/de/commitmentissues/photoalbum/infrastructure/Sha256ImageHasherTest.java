package de.commitmentissues.photoalbum.infrastructure;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.commitmentissues.photoalbum.application.ImageProblem;
import de.commitmentissues.photoalbum.application.ImageRejectedException;

class Sha256ImageHasherTest {

    private static final long MAX_BYTES = Sha256ImageHasher.MAX_BYTES;

    @TempDir
    Path dir;

    private final Sha256ImageHasher hasher = new Sha256ImageHasher();

    @Test
    @DisplayName("AK-05.1: Ein gültiges JPEG wird angenommen und liefert einen SHA-256-Hash")
    void acceptsJpeg() throws IOException {
        Path file = writeImage("strand.jpg", "jpg", 0xFF8800);

        String hash = hasher.hash(file);

        assertTrue(hash.matches("[0-9a-f]{64}"));
    }

    @Test
    @DisplayName("AK-05.1: Ein PNG mit .jpg-Endung wird nach seinem Inhalt angenommen")
    void acceptsPngWithJpgExtension() throws IOException {
        Path file = writeImage("berg.jpg", "png", 0x336655);

        String hash = hasher.hash(file);

        assertEquals(64, hash.length());
    }

    @Test
    @DisplayName("AK-06.1: Bytegleiche Dateien mit verschiedenen Namen haben denselben Hash")
    void sameContentGivesSameHash() throws IOException {
        Path original = writeImage("strand.jpg", "png", 0x1188FF);
        Path copy = Files.copy(original, dir.resolve("kopie.jpg"));

        assertEquals(hasher.hash(original), hasher.hash(copy));
    }

    @Test
    @DisplayName("AK-06.2: Ein anderer Dateiinhalt ergibt einen anderen Hash")
    void differentContentGivesDifferentHash() throws IOException {
        Path first = writeImage("strand.png", "png", 0x1188FF);
        Path export = writeImage("strand_export.png", "png", 0x1188FE);

        assertNotEquals(hasher.hash(first), hasher.hash(export));
    }

    @Test
    @DisplayName("AK-05.1: Ein gültiges Bild mit genau 20 MiB wird angenommen")
    void acceptsImageOfExactlyMaxSize() throws IOException {
        Path file = padTo(writeImage("genau_20mb.png", "png", 0x123456), MAX_BYTES);

        String hash = hasher.hash(file);

        assertEquals(64, hash.length());
    }

    @Test
    @DisplayName("AK-05.2: Ein gültiges Bild mit 20 MiB und einem Byte ist zu groß")
    void rejectsImageOneByteTooLarge() throws IOException {
        Path file = padTo(writeImage("zu_gross.png", "png", 0x123456), MAX_BYTES + 1);

        assertProblem(ImageProblem.TOO_LARGE, file);
    }

    @Test
    @DisplayName("AK-05.2: Eine leere Datei wird abgelehnt")
    void rejectsEmptyFile() throws IOException {
        Path file = Files.createFile(dir.resolve("leer.jpg"));

        assertProblem(ImageProblem.EMPTY, file);
    }

    @Test
    @DisplayName("AK-05.2: Eine Textdatei mit .jpg-Endung ist kein Bild")
    void rejectsTextFileWithJpgExtension() throws IOException {
        Path file = Files.writeString(dir.resolve("bild.jpg"), "Das ist kein Bild.");

        assertProblem(ImageProblem.NOT_AN_IMAGE, file);
    }

    @Test
    @DisplayName("AK-05.2: Ein beschädigtes PNG ist kein gültiges Bild")
    void rejectsDamagedPng() throws IOException {
        byte[] pngSignature = {(byte) 0x89, 'P', 'N', 'G', '\r', '\n', 0x1A, '\n'};
        Path file = Files.write(dir.resolve("defekt.png"), pngSignature);
        Files.writeString(file, "kaputte Bilddaten", StandardOpenOption.APPEND);

        assertProblem(ImageProblem.NOT_AN_IMAGE, file);
    }

    @Test
    @DisplayName("A-03: Ein GIF ist kein zulässiges Format")
    void rejectsGif() throws IOException {
        Path file = writeImage("animation.gif", "gif", 0x00AA00);

        assertProblem(ImageProblem.NOT_AN_IMAGE, file);
    }

    @Test
    @DisplayName("AK-05.4: Eine fehlende Datei ist nicht lesbar")
    void rejectsMissingFile() {
        assertProblem(ImageProblem.UNREADABLE, dir.resolve("fehlt.jpg"));
    }

    @Test
    @DisplayName("ADR-002: Eine große Datei ohne Bildinhalt wird als kein Bild gemeldet")
    void rejectsLargeNonImageAsNotAnImage() throws IOException {
        Path file = padTo(Files.writeString(dir.resolve("riesig.jpg"), "Hallo"), MAX_BYTES + 1);

        assertProblem(ImageProblem.NOT_AN_IMAGE, file);
    }

    @Test
    @DisplayName("ADR-002: Ein Bild mit mehr als 100 Megapixeln wird vor dem Entschlüsseln abgelehnt")
    void rejectsImageWithTooManyPixels() throws IOException {
        Path file = writeImage("bombe.png", "png", 0xFFFFFF);
        byte[] bytes = Files.readAllBytes(file);
        ByteBuffer.wrap(bytes).putInt(16, 20_000).putInt(20, 20_000);
        Files.write(file, bytes);

        assertProblem(ImageProblem.NOT_AN_IMAGE, file);
    }

    private Path writeImage(String name, String format, int color) throws IOException {
        BufferedImage image = new BufferedImage(4, 3, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, color);
        Path file = dir.resolve(name);
        assertTrue(ImageIO.write(image, format, file.toFile()), "Format nicht schreibbar: " + format);
        return file;
    }

    private static Path padTo(Path file, long size) throws IOException {
        long missing = size - Files.size(file);
        Files.write(file, new byte[(int) missing], StandardOpenOption.APPEND);
        return file;
    }

    private void assertProblem(ImageProblem expected, Path file) {
        ImageRejectedException exception = assertThrows(ImageRejectedException.class, () -> hasher.hash(file));
        assertEquals(expected, exception.getProblem());
    }
}
