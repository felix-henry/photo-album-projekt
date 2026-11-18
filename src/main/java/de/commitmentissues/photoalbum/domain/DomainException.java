package de.commitmentissues.photoalbum.domain;

/**
 * Lehnt einen Verstoß gegen eine Geschäftsregel mit Grund ab (ADR-002).
 */
public class DomainException extends RuntimeException {

    private final Violation violation;

    public DomainException(Violation violation) {
        super(violation.name());
        this.violation = violation;
    }

    public Violation getViolation() {
        return violation;
    }
}
