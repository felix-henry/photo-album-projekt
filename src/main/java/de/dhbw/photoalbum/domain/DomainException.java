package de.dhbw.photoalbum.domain;

/**
 * Fachliche Ablehnung durch die Domäne (B3 F13). Die Nachricht ist technisch (Log, Tests);
 * Meldungstexte für die Nutzerin entstehen nur in der Anwendungsschicht (ErrorMessages, NFA-05).
 */
public abstract class DomainException extends RuntimeException {

    protected DomainException(String message) {
        super(message);
    }
}
