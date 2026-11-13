package de.commitmentissues.photoalbum;

import java.nio.file.Path;

import javax.swing.SwingUtilities;

import de.commitmentissues.photoalbum.application.AlbumRepository;
import de.commitmentissues.photoalbum.application.CreateAlbumUseCase;
import de.commitmentissues.photoalbum.application.ListAlbumsUseCase;
import de.commitmentissues.photoalbum.application.RenameAlbumUseCase;
import de.commitmentissues.photoalbum.infrastructure.FileAlbumRepository;
import de.commitmentissues.photoalbum.ui.AlbumOverviewWindow;

/**
 * Startet die App und verbindet die Schichten. Nur diese Klasse kennt alle Schichten (ARCHITECTURE.md).
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        Path dataDirectory = Path.of(System.getProperty("user.home"), ".photo-album");
        AlbumRepository albums = new FileAlbumRepository(dataDirectory);
        ListAlbumsUseCase listAlbums = new ListAlbumsUseCase(albums);
        CreateAlbumUseCase createAlbum = new CreateAlbumUseCase(albums);
        RenameAlbumUseCase renameAlbum = new RenameAlbumUseCase(albums);

        SwingUtilities.invokeLater(
                () -> new AlbumOverviewWindow(listAlbums, createAlbum, renameAlbum).setVisible(true));
    }
}
