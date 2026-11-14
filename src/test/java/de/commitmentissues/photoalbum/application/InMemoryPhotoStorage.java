package de.commitmentissues.photoalbum.application;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.commitmentissues.photoalbum.domain.PhotoId;

/**
 * Merkt sich die angelegten App-Kopien und kann Speicher-, Lese- und Löschfehler simulieren.
 * Statt die Datei zu lesen, merkt er sich ihren Namen als Inhalt. So greifen Unit-Tests
 * nicht auf Dateien zu (NFA-05).
 */
class InMemoryPhotoStorage implements PhotoStorage {

    private final Map<PhotoId, byte[]> stored = new HashMap<>();
    boolean failOnStore;
    boolean failOnLoad;
    boolean failOnDelete;

    @Override
    public void store(PhotoId id, Path file) {
        if (failOnStore) {
            throw new StorageException("Speicherfehler simuliert");
        }
        stored.put(id, file.getFileName().toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public byte[] load(PhotoId id) {
        if (failOnLoad) {
            throw new StorageException("Lesefehler simuliert");
        }
        byte[] content = stored.get(id);
        if (content == null) {
            throw new StorageException("Die Kopie fehlt");
        }
        return content.clone();
    }

    @Override
    public void delete(PhotoId id) {
        if (failOnDelete) {
            throw new StorageException("Löschfehler simuliert");
        }
        stored.remove(id);
    }

    void put(PhotoId id, byte[] content) {
        stored.put(id, content.clone());
    }

    Set<PhotoId> storedIds() {
        return Set.copyOf(stored.keySet());
    }
}
