package de.dhbw.photoalbum.domain;

import java.util.Objects;
import java.util.regex.Pattern;

/** SHA-256 des Dateiinhalts als 64 Hex-Zeichen in Kleinbuchstaben. */
public record ContentHash(String sha256Hex) {

    private static final Pattern SHA256_HEX = Pattern.compile("[0-9a-f]{64}");

    public ContentHash {
        Objects.requireNonNull(sha256Hex, "sha256Hex");
        if (!SHA256_HEX.matcher(sha256Hex).matches()) {
            throw new IllegalArgumentException("Not a lowercase SHA-256 hex string: " + sha256Hex);
        }
    }
}
