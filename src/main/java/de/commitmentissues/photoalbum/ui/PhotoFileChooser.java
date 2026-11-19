package de.commitmentissues.photoalbum.ui;

import java.awt.Component;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javax.swing.JFileChooser;

/**
 * Eine Dateiauswahl für alle Albumansichten (#42, #43). Sie entsteht erst beim ersten „Foto hinzufügen“,
 * weil das Erzeugen einer {@link JFileChooser} das Öffnen eines Albums spürbar verzögert. Danach merkt
 * sie sich den zuletzt benutzten Ordner über alle Alben hinweg.
 */
final class PhotoFileChooser {

    private JFileChooser chooser;

    /**
     * Zeigt die Dateiauswahl und liefert die gewählte Datei; leer, wenn der Nutzer abbricht oder keine Datei
     * ausgewählt ist.
     */
    Optional<Path> choose(Component parent) {
        if (chooser == null) {
            chooser = new JFileChooser(startDirectory(Path.of(System.getProperty("user.home"))).toFile());
        }
        if (chooser.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return Optional.empty();
        }
        File selected = chooser.getSelectedFile();
        return Optional.ofNullable(selected).map(File::toPath);
    }

    /**
     * Beim ersten Mal der Ordner „Pictures“ im Benutzerordner (unter Windows als „Bilder“ angezeigt), falls es ihn
     * gibt, sonst der Benutzerordner.
     */
    static Path startDirectory(Path home) {
        Path pictures = home.resolve("Pictures");
        return Files.isDirectory(pictures) ? pictures : home;
    }
}
