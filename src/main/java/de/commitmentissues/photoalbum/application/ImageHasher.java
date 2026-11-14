package de.commitmentissues.photoalbum.application;

import java.nio.file.Path;

/**
 * Port: liest eine ausgewählte Datei, prüft sie als JPEG oder PNG bis 20 MiB (A-03)
 * und berechnet den Inhaltshash für die Duplikatprüfung (A-01).
 */
public interface ImageHasher {

    /**
     * @throws ImageRejectedException wenn die Datei nicht lesbar ist oder die Dateiregel verletzt
     */
    String hash(Path file);
}
