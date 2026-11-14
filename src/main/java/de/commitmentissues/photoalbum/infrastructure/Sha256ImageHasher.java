package de.commitmentissues.photoalbum.infrastructure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import de.commitmentissues.photoalbum.application.ImageHasher;
import de.commitmentissues.photoalbum.application.ImageProblem;
import de.commitmentissues.photoalbum.application.ImageRejectedException;

/**
 * Prüft eine ausgewählte Datei als JPEG oder PNG bis 20 MiB (A-03) und berechnet ihren
 * SHA-256-Hash für die Duplikatprüfung (A-01, ADR-001). Dateien über 20 MiB werden nicht
 * vollständig gelesen, Bilder über 100 Megapixel nicht entschlüsselt (ADR-002).
 */
public class Sha256ImageHasher implements ImageHasher {

    static final long MAX_BYTES = 20L * 1024 * 1024;
    static final long MAX_PIXELS = 100_000_000L;
    private static final Set<String> ALLOWED_FORMATS = Set.of("jpeg", "png");
    private static final int CHECK_SUBSAMPLING = 8;

    @Override
    public String hash(Path file) {
        if (!Files.isRegularFile(file) || !Files.isReadable(file)) {
            throw new ImageRejectedException(ImageProblem.UNREADABLE);
        }
        long size = sizeOf(file);
        if (size == 0) {
            throw new ImageRejectedException(ImageProblem.EMPTY);
        }
        if (size > MAX_BYTES) {
            if (!hasAllowedImageHeader(file)) {
                throw new ImageRejectedException(ImageProblem.NOT_AN_IMAGE);
            }
            throw new ImageRejectedException(ImageProblem.TOO_LARGE);
        }
        byte[] content = readAll(file);
        checkDecodableImage(content);
        return sha256(content);
    }

    private static long sizeOf(Path file) {
        try {
            return Files.size(file);
        } catch (IOException e) {
            throw new ImageRejectedException(ImageProblem.UNREADABLE, e);
        }
    }

    private static byte[] readAll(Path file) {
        try {
            return Files.readAllBytes(file);
        } catch (IOException e) {
            throw new ImageRejectedException(ImageProblem.UNREADABLE, e);
        }
    }

    /**
     * Liest nur so viele Bytes am Dateianfang, wie ImageIO zum Erkennen des Formats braucht.
     */
    private static boolean hasAllowedImageHeader(Path file) {
        try (ImageInputStream input = ImageIO.createImageInputStream(file.toFile())) {
            if (input == null) {
                return false;
            }
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            return readers.hasNext() && isAllowedFormat(readers.next());
        } catch (IOException e) {
            throw new ImageRejectedException(ImageProblem.UNREADABLE, e);
        }
    }

    private static void checkDecodableImage(byte[] content) {
        try (ImageInputStream input = ImageIO.createImageInputStream(new ByteArrayInputStream(content))) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext()) {
                throw new ImageRejectedException(ImageProblem.NOT_AN_IMAGE);
            }
            ImageReader reader = readers.next();
            try {
                if (!isAllowedFormat(reader)) {
                    throw new ImageRejectedException(ImageProblem.NOT_AN_IMAGE);
                }
                reader.setInput(input, true, true);
                long pixels = (long) reader.getWidth(0) * reader.getHeight(0);
                if (pixels > MAX_PIXELS) {
                    throw new ImageRejectedException(ImageProblem.NOT_AN_IMAGE);
                }
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceSubsampling(CHECK_SUBSAMPLING, CHECK_SUBSAMPLING, 0, 0);
                reader.read(0, param);
            } finally {
                reader.dispose();
            }
        } catch (ImageRejectedException e) {
            throw e;
        } catch (IOException | RuntimeException e) {
            throw new ImageRejectedException(ImageProblem.NOT_AN_IMAGE, e);
        }
    }

    private static boolean isAllowedFormat(ImageReader reader) throws IOException {
        return ALLOWED_FORMATS.contains(reader.getFormatName().toLowerCase(Locale.ROOT));
    }

    private static String sha256(byte[] content) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(content);
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 ist in jedem JDK enthalten.", e);
        }
    }
}
