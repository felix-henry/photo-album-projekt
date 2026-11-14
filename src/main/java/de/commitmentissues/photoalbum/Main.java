package de.commitmentissues.photoalbum;

import java.nio.file.Path;
import java.time.Clock;

import javax.swing.SwingUtilities;

import de.commitmentissues.photoalbum.application.AddPhotoUseCase;
import de.commitmentissues.photoalbum.application.AlbumRepository;
import de.commitmentissues.photoalbum.application.CreateAlbumUseCase;
import de.commitmentissues.photoalbum.application.DeleteAlbumUseCase;
import de.commitmentissues.photoalbum.application.DeletePermanentlyUseCase;
import de.commitmentissues.photoalbum.application.EditPhotoTextsUseCase;
import de.commitmentissues.photoalbum.application.ImageHasher;
import de.commitmentissues.photoalbum.application.ListAlbumsUseCase;
import de.commitmentissues.photoalbum.application.ListTrashUseCase;
import de.commitmentissues.photoalbum.application.LoadPhotoUseCase;
import de.commitmentissues.photoalbum.application.OpenAlbumUseCase;
import de.commitmentissues.photoalbum.application.PhotoStorage;
import de.commitmentissues.photoalbum.application.RemovePhotoUseCase;
import de.commitmentissues.photoalbum.application.RenameAlbumUseCase;
import de.commitmentissues.photoalbum.application.RestoreTrashEntryUseCase;
import de.commitmentissues.photoalbum.application.StartupCleanupUseCase;
import de.commitmentissues.photoalbum.application.TrashRepository;
import de.commitmentissues.photoalbum.infrastructure.FileAlbumRepository;
import de.commitmentissues.photoalbum.infrastructure.FilePhotoStorage;
import de.commitmentissues.photoalbum.infrastructure.FileTrashRepository;
import de.commitmentissues.photoalbum.infrastructure.Sha256ImageHasher;
import de.commitmentissues.photoalbum.ui.AlbumOverviewWindow;
import de.commitmentissues.photoalbum.ui.UseCases;

/**
 * Startet die App und verbindet die Schichten. Nur diese Klasse kennt alle Schichten (ARCHITECTURE.md).
 * Vor dem ersten Fenster läuft die Bereinigung beim Start (A-10, A-11).
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        Path dataDirectory = Path.of(System.getProperty("user.home"), ".photo-album");
        Clock clock = Clock.systemUTC();
        AlbumRepository albums = new FileAlbumRepository(dataDirectory);
        TrashRepository trash = new FileTrashRepository(dataDirectory);
        PhotoStorage photos = new FilePhotoStorage(dataDirectory);
        ImageHasher imageHasher = new Sha256ImageHasher();

        new StartupCleanupUseCase(albums, trash, photos).execute(clock.instant());

        UseCases useCases = new UseCases(
                new ListAlbumsUseCase(albums),
                new CreateAlbumUseCase(albums),
                new RenameAlbumUseCase(albums),
                new DeleteAlbumUseCase(albums, trash, clock),
                new OpenAlbumUseCase(albums),
                new AddPhotoUseCase(albums, imageHasher, photos),
                new LoadPhotoUseCase(photos),
                new EditPhotoTextsUseCase(albums),
                new RemovePhotoUseCase(albums, trash, clock),
                new ListTrashUseCase(trash, albums, photos, clock),
                new RestoreTrashEntryUseCase(albums, trash, photos, clock),
                new DeletePermanentlyUseCase(trash, albums, photos));

        SwingUtilities.invokeLater(() -> new AlbumOverviewWindow(useCases).setVisible(true));
    }
}
