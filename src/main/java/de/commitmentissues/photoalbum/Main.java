package de.commitmentissues.photoalbum;

import java.nio.file.Path;

import javax.swing.SwingUtilities;

import de.commitmentissues.photoalbum.application.AddPhotoUseCase;
import de.commitmentissues.photoalbum.application.AlbumRepository;
import de.commitmentissues.photoalbum.application.CreateAlbumUseCase;
import de.commitmentissues.photoalbum.application.ImageHasher;
import de.commitmentissues.photoalbum.application.ListAlbumsUseCase;
import de.commitmentissues.photoalbum.application.LoadPhotoUseCase;
import de.commitmentissues.photoalbum.application.OpenAlbumUseCase;
import de.commitmentissues.photoalbum.application.PhotoStorage;
import de.commitmentissues.photoalbum.application.RenameAlbumUseCase;
import de.commitmentissues.photoalbum.infrastructure.FileAlbumRepository;
import de.commitmentissues.photoalbum.infrastructure.FilePhotoStorage;
import de.commitmentissues.photoalbum.infrastructure.Sha256ImageHasher;
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
        PhotoStorage photos = new FilePhotoStorage(dataDirectory);
        ImageHasher imageHasher = new Sha256ImageHasher();

        ListAlbumsUseCase listAlbums = new ListAlbumsUseCase(albums);
        CreateAlbumUseCase createAlbum = new CreateAlbumUseCase(albums);
        RenameAlbumUseCase renameAlbum = new RenameAlbumUseCase(albums);
        OpenAlbumUseCase openAlbum = new OpenAlbumUseCase(albums);
        AddPhotoUseCase addPhoto = new AddPhotoUseCase(albums, imageHasher, photos);
        LoadPhotoUseCase loadPhoto = new LoadPhotoUseCase(photos);

        SwingUtilities.invokeLater(() -> new AlbumOverviewWindow(
                listAlbums, createAlbum, renameAlbum, openAlbum, addPhoto, loadPhoto).setVisible(true));
    }
}
