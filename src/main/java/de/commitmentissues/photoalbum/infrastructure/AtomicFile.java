package de.commitmentissues.photoalbum.infrastructure;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Schreibt eine Textdatei zuerst unter einem temporären Namen und ersetzt das Original erst danach in
 * einem Schritt (ADR-002). Die Datei ist dadurch immer vollständig alt oder vollständig neu.
 */
final class AtomicFile {

    private AtomicFile() {
    }

    static void write(Path target, String content) throws IOException {
        Path temporary = target.resolveSibling(target.getFileName() + ".tmp");
        try {
            Files.createDirectories(target.getParent());
            Files.writeString(temporary, content, StandardCharsets.UTF_8);
            Files.move(temporary, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            deleteQuietly(temporary);
            throw e;
        }
    }

    private static void deleteQuietly(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException ignored) {
            // Eine übrig gebliebene Zwischendatei endet auf .tmp und wird von keinem Adapter gelesen.
        }
    }
}
