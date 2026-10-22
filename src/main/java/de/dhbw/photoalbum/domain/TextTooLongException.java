package de.dhbw.photoalbum.domain;

import java.util.Objects;

/** Titel oder Beschreibung zu lang; das Feld bestimmt die Meldung. */
public class TextTooLongException extends DomainException {

    public enum Field { TITLE, DESCRIPTION }

    private final Field field;

    public TextTooLongException(Field field) {
        super("Text too long: " + Objects.requireNonNull(field, "field"));
        this.field = field;
    }

    public Field field() {
        return field;
    }
}
