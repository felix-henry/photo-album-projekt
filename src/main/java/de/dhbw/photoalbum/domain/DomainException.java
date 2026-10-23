package de.dhbw.photoalbum.domain;

/** Fachliche Ablehnung durch die Domäne. */
public abstract class DomainException extends RuntimeException {

    protected DomainException(String message) {
        super(message);
    }
}
