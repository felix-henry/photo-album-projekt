package de.commitmentissues.photoalbum.domain;

import java.util.regex.Pattern;

/**
 * Zählt Zeichen so, wie Nutzer sie sehen (A-04): Ein Buchstabe, ein Umlaut,
 * ein Leerzeichen oder ein Emoji zählt jeweils einmal.
 */
final class TextLength {

    private static final Pattern USER_CHARACTER = Pattern.compile("\\X");

    private TextLength() {
    }

    static int count(String text) {
        return (int) USER_CHARACTER.matcher(text).results().count();
    }
}
