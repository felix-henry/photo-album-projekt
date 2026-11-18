package de.commitmentissues.photoalbum.ui;

import de.commitmentissues.photoalbum.application.AddPhotoUseCase;
import de.commitmentissues.photoalbum.application.CreateAlbumUseCase;
import de.commitmentissues.photoalbum.application.DeleteAlbumUseCase;
import de.commitmentissues.photoalbum.application.DeletePermanentlyUseCase;
import de.commitmentissues.photoalbum.application.EditPhotoTextsUseCase;
import de.commitmentissues.photoalbum.application.ListAlbumsUseCase;
import de.commitmentissues.photoalbum.application.ListTrashUseCase;
import de.commitmentissues.photoalbum.application.LoadPhotoUseCase;
import de.commitmentissues.photoalbum.application.OpenAlbumUseCase;
import de.commitmentissues.photoalbum.application.RemovePhotoUseCase;
import de.commitmentissues.photoalbum.application.RenameAlbumUseCase;
import de.commitmentissues.photoalbum.application.RestoreTrashEntryUseCase;

/**
 * Alle Use Cases, die die Oberfläche aufruft. {@code Main} erzeugt sie und übergibt sie gesammelt, damit
 * die Fenster keine langen Konstruktoren brauchen.
 */
public record UseCases(
        ListAlbumsUseCase listAlbums,
        CreateAlbumUseCase createAlbum,
        RenameAlbumUseCase renameAlbum,
        DeleteAlbumUseCase deleteAlbum,
        OpenAlbumUseCase openAlbum,
        AddPhotoUseCase addPhoto,
        LoadPhotoUseCase loadPhoto,
        EditPhotoTextsUseCase editPhotoTexts,
        RemovePhotoUseCase removePhoto,
        ListTrashUseCase listTrash,
        RestoreTrashEntryUseCase restoreTrashEntry,
        DeletePermanentlyUseCase deletePermanently) {
}
