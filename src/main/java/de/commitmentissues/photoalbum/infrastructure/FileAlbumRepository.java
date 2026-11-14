package de.commitmentissues.photoalbum.infrastructure;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import de.commitmentissues.photoalbum.application.AlbumRepository;
import de.commitmentissues.photoalbum.application.StorageException;
import de.commitmentissues.photoalbum.domain.Album;
import de.commitmentissues.photoalbum.domain.AlbumId;
import de.commitmentissues.photoalbum.domain.DomainException;
import de.commitmentissues.photoalbum.domain.Photo;
import de.commitmentissues.photoalbum.domain.PhotoId;

/**
 * Speichert jedes Album samt seiner Fotos als eigene JSON-Datei {@code albums/<AlbumId>.json}
 * (ADR-001, ADR-003).
 */
public class FileAlbumRepository implements AlbumRepository {

    private static final String SUFFIX = ".json";

    private final Path directory;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public FileAlbumRepository(Path dataDirectory) {
        this.directory = dataDirectory.resolve("albums");
    }

    @Override
    public Optional<Album> findById(AlbumId id) {
        Path file = directory.resolve(id.value() + SUFFIX);
        if (Files.notExists(file)) {
            return Optional.empty();
        }
        return Optional.of(read(file));
    }

    @Override
    public List<Album> findAll() {
        if (Files.notExists(directory)) {
            return List.of();
        }
        List<Album> albums = new ArrayList<>();
        try (Stream<Path> files = Files.list(directory)) {
            for (Path file : files.filter(FileAlbumRepository::isAlbumFile).sorted().toList()) {
                albums.add(read(file));
            }
        } catch (IOException e) {
            throw new StorageException("Der Albumordner ist nicht lesbar: " + directory, e);
        }
        return albums;
    }

    @Override
    public void save(Album album) {
        String fileName = album.getId().value() + SUFFIX;
        Path target = directory.resolve(fileName);
        Path temporary = directory.resolve(fileName + ".tmp");
        try {
            Files.createDirectories(directory);
            Files.writeString(temporary, gson.toJson(AlbumFile.of(album)), StandardCharsets.UTF_8);
            Files.move(temporary, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            deleteQuietly(temporary);
            throw new StorageException("Das Album ist nicht speicherbar: " + target, e);
        }
    }

    private Album read(Path file) {
        try {
            AlbumFile data = gson.fromJson(Files.readString(file, StandardCharsets.UTF_8), AlbumFile.class);
            if (data == null || data.id() == null || data.name() == null) {
                throw new StorageException("Die Albumdatei ist unvollständig: " + file);
            }
            return Album.restore(new AlbumId(UUID.fromString(data.id())), data.name(), photosOf(data, file));
        } catch (IOException | JsonParseException | IllegalArgumentException | DomainException e) {
            throw new StorageException("Die Albumdatei ist nicht lesbar: " + file, e);
        }
    }

    private static List<Photo> photosOf(AlbumFile data, Path file) {
        if (data.photos() == null) {
            return List.of();
        }
        List<Photo> photos = new ArrayList<>();
        for (PhotoFile photo : data.photos()) {
            if (photo == null || photo.id() == null || photo.contentHash() == null) {
                throw new StorageException("Ein Foto in der Albumdatei ist unvollständig: " + file);
            }
            photos.add(Photo.restore(new PhotoId(UUID.fromString(photo.id())), photo.contentHash()));
        }
        return photos;
    }

    private static boolean isAlbumFile(Path file) {
        return file.getFileName().toString().endsWith(SUFFIX);
    }

    private static void deleteQuietly(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException ignored) {
            // Eine übrig gebliebene Zwischendatei stört nicht: findAll liest nur Dateien mit der Endung .json.
        }
    }
}
