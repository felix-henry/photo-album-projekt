package de.dhbw.photoalbum.domain;

import java.util.Objects;

/** Titel oder Beschreibung zu lang (FA-10, AE-02, A-05); das Feld bestimmt die Meldung (AK-10.3, AK-10.4). */
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
