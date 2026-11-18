package de.commitmentissues.photoalbum.infrastructure;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import de.commitmentissues.photoalbum.application.StorageException;
import de.commitmentissues.photoalbum.application.TrashRepository;
import de.commitmentissues.photoalbum.domain.DomainException;
import de.commitmentissues.photoalbum.domain.Trash;
import de.commitmentissues.photoalbum.domain.TrashEntry;

/**
 * Speichert den Papierkorb als JSON-Datei {@code trash.json} im Datenordner (ADR-001, ADR-003).
 * Eine fehlende Datei bedeutet einen leeren Papierkorb, eine unlesbare wird als Fehler gemeldet (ADR-002).
 */
public class FileTrashRepository implements TrashRepository {

    private final Path file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public FileTrashRepository(Path dataDirectory) {
        this.file = dataDirectory.resolve("trash.json");
    }

    @Override
    public Trash load() {
        if (Files.notExists(file)) {
            return new Trash(List.of());
        }
        try {
            TrashFile data = gson.fromJson(Files.readString(file, StandardCharsets.UTF_8), TrashFile.class);
            if (data == null || data.entries() == null) {
                throw new StorageException("Der Papierkorb ist unvollständig: " + file);
            }
            List<TrashEntry> entries = new ArrayList<>();
            for (TrashEntryFile entry : data.entries()) {
                if (entry == null) {
                    throw new IllegalArgumentException("Ein Papierkorbeintrag fehlt.");
                }
                entries.add(entry.toEntry());
            }
            return new Trash(entries);
        } catch (IOException | JsonParseException | IllegalArgumentException | DateTimeException
                | DomainException e) {
            throw new StorageException("Der Papierkorb ist nicht lesbar: " + file, e);
        }
    }

    @Override
    public void save(Trash trash) {
        try {
            AtomicFile.write(file, gson.toJson(TrashFile.of(trash)));
        } catch (IOException e) {
            throw new StorageException("Der Papierkorb ist nicht speicherbar: " + file, e);
        }
    }
}
