package de.commitmentissues.photoalbum.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PhotoFileChooserTest {

    @TempDir
    Path home;

    @Test
    @Tag("normalfall")
    @DisplayName("NFA-04, #42: Die Dateiauswahl startet im Ordner „Pictures“, wenn es ihn gibt")
    void startsInPicturesFolder() throws IOException {
        Path pictures = Files.createDirectory(home.resolve("Pictures"));

        assertEquals(pictures, PhotoFileChooser.startDirectory(home));
    }

    @Test
    @Tag("grenzfall")
    @DisplayName("NFA-04, #42: Ohne Ordner „Pictures“ startet die Dateiauswahl im Benutzerordner")
    void startsInHomeWithoutPicturesFolder() {
        assertEquals(home, PhotoFileChooser.startDirectory(home));
    }

    @Test
    @Tag("grenzfall")
    @DisplayName("NFA-04, #42: Eine Datei namens „Pictures“ gilt nicht als Startordner")
    void ignoresFileNamedPictures() throws IOException {
        Files.createFile(home.resolve("Pictures"));

        assertEquals(home, PhotoFileChooser.startDirectory(home));
    }
}
