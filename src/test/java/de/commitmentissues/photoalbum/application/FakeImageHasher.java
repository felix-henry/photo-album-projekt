package de.commitmentissues.photoalbum.application;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Liefert ohne echte Dateien einen Hash oder eine Ablehnung, je nachdem, was der Test festlegt.
 */
class FakeImageHasher implements ImageHasher {

    private final Map<Path, String> hashes = new HashMap<>();
    private final Map<Path, ImageProblem> problems = new HashMap<>();

    void returns(Path file, String hash) {
        hashes.put(file, hash);
    }

    void rejects(Path file, ImageProblem problem) {
        problems.put(file, problem);
    }

    @Override
    public String hash(Path file) {
        ImageProblem problem = problems.get(file);
        if (problem != null) {
            throw new ImageRejectedException(problem);
        }
        return hashes.getOrDefault(file, "hash-" + file.getFileName());
    }
}
