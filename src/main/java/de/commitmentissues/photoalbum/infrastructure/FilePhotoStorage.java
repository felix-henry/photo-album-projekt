package de.commitmentissues.photoalbum.infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import de.commitmentissues.photoalbum.application.PhotoStorage;
import de.commitmentissues.photoalbum.application.StorageException;
import de.commitmentissues.photoalbum.domain.PhotoId;

/**
 * Legt die App-Kopie jedes Bildes als Datei {@code photos/<PhotoId>} ab (A-05, ADR-001).
 */
public class FilePhotoStorage implements PhotoStorage {

    private static final String TEMPORARY_SUFFIX = ".tmp";

    private final Path directory;

    public FilePhotoStorage(Path dataDirectory) {
        this.directory = dataDirectory.resolve("photos");
    }

    @Override
    public void store(PhotoId id, Path file) {
        Path target = fileFor(id);
        Path temporary = directory.resolve(id.value() + TEMPORARY_SUFFIX);
        try {
            Files.createDirectories(directory);
            Files.copy(file, temporary, StandardCopyOption.REPLACE_EXISTING);
            Files.move(temporary, target, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            deleteQuietly(temporary);
            throw new StorageException("Die App-Kopie ist nicht speicherbar: " + target, e);
        }
    }

    @Override
    public byte[] load(PhotoId id) {
        Path file = fileFor(id);
        try {
            return Files.readAllBytes(file);
        } catch (IOException e) {
            throw new StorageException("Die App-Kopie ist nicht lesbar: " + file, e);
        }
    }

    @Override
    public void delete(PhotoId id) {
        Path file = fileFor(id);
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("Die App-Kopie ist nicht löschbar: " + file, e);
        }
    }

    /**
     * Nennt nur Dateien, deren Name genau eine Foto-ID ist. Zwischendateien und fremde Dateien zählen nicht.
     */
    @Override
    public Set<PhotoId> ids() {
        if (Files.notExists(directory)) {
            return Set.of();
        }
        Set<PhotoId> ids = new HashSet<>();
        try (Stream<Path> files = Files.list(directory)) {
            for (Path file : files.toList()) {
                parseId(file.getFileName().toString()).ifPresent(ids::add);
            }
        } catch (IOException e) {
            throw new StorageException("Der Fotoordner ist nicht lesbar: " + directory, e);
        }
        return ids;
    }

    private Path fileFor(PhotoId id) {
        return directory.resolve(id.value().toString());
    }

    private static Optional<PhotoId> parseId(String fileName) {
        try {
            UUID uuid = UUID.fromString(fileName);
            return uuid.toString().equals(fileName) ? Optional.of(new PhotoId(uuid)) : Optional.empty();
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private static void deleteQuietly(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException ignored) {
            // Eine übrig gebliebene Zwischendatei gehört zu keinem Foto und wird nie angezeigt.
        }
    }
}
