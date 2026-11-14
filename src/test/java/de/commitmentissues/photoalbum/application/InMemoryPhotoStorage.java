package de.commitmentissues.photoalbum.application;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import de.commitmentissues.photoalbum.domain.PhotoId;

/**
 * Merkt sich die angelegten App-Kopien und kann Speicher- und Löschfehler simulieren.
 */
class InMemoryPhotoStorage implements PhotoStorage {

    private final Set<PhotoId> stored = new HashSet<>();
    boolean failOnStore;
    boolean failOnDelete;

    @Override
    public void store(PhotoId id, Path file) {
        if (failOnStore) {
            throw new StorageException("Speicherfehler simuliert");
        }
        stored.add(id);
    }

    @Override
    public void delete(PhotoId id) {
        if (failOnDelete) {
            throw new StorageException("Löschfehler simuliert");
        }
        stored.remove(id);
    }

    Set<PhotoId> storedIds() {
        return Set.copyOf(stored);
    }
}
