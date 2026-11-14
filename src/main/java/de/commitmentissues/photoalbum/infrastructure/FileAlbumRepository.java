package de.commitmentissues.photoalbum.infrastructure;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
        Path file = fileFor(id);
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
        Path target = fileFor(album.getId());
        try {
            AtomicFile.write(target, gson.toJson(AlbumFile.of(album)));
        } catch (IOException e) {
            throw new StorageException("Das Album ist nicht speicherbar: " + target, e);
        }
    }

    @Override
    public void delete(AlbumId id) {
        Path file = fileFor(id);
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("Die Albumdatei ist nicht löschbar: " + file, e);
        }
    }

    private Path fileFor(AlbumId id) {
        return directory.resolve(id.value() + SUFFIX);
    }

    private Album read(Path file) {
        try {
            AlbumFile data = gson.fromJson(Files.readString(file, StandardCharsets.UTF_8), AlbumFile.class);
            if (data == null || data.id() == null || data.name() == null) {
                throw new StorageException("Die Albumdatei ist unvollständig: " + file);
            }
            return Album.restore(new AlbumId(UUID.fromString(data.id())), data.name(),
                    PhotoFile.toPhotos(data.photos()));
        } catch (IOException | JsonParseException | IllegalArgumentException | DomainException e) {
            throw new StorageException("Die Albumdatei ist nicht lesbar: " + file, e);
        }
    }

    private static boolean isAlbumFile(Path file) {
        return file.getFileName().toString().endsWith(SUFFIX);
    }
}
