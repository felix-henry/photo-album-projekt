package de.commitmentissues.photoalbum.application;

import java.util.Objects;

/**
 * Ergebnis eines Use Cases für die UI (ADR-002): entweder ein Wert
 * oder eine Meldung für den Nutzer.
 */
public final class Result<T> {

    private final T value;
    private final String message;

    private Result(T value, String message) {
        this.value = value;
        this.message = message;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value, null);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(null, Objects.requireNonNull(message, "message"));
    }

    public boolean isSuccess() {
        return message == null;
    }

    public T getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
