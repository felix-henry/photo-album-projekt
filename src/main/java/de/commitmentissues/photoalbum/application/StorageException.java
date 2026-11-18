package de.commitmentissues.photoalbum.application;

/**
 * Ein Port konnte Daten nicht lesen oder schreiben (ADR-002).
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
