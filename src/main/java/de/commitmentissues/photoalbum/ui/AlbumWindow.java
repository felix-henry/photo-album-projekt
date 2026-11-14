package de.commitmentissues.photoalbum.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import de.commitmentissues.photoalbum.application.AddPhotoUseCase;
import de.commitmentissues.photoalbum.application.AlbumDetails;
import de.commitmentissues.photoalbum.application.EditPhotoTextsUseCase;
import de.commitmentissues.photoalbum.application.LoadPhotoUseCase;
import de.commitmentissues.photoalbum.application.OpenAlbumUseCase;
import de.commitmentissues.photoalbum.application.PhotoSummary;
import de.commitmentissues.photoalbum.application.Result;

/**
 * Albumansicht (FA-08) mit „Foto hinzufügen“ (UC-01) und „Texte bearbeiten“ (FA-10). Zeigt die Fotos
 * einzeln in Anzeigegröße mit Titel und Beschreibung; die Bilder kommen über den Bildpuffer
 * (NFA-03, ARCHITECTURE, Abschnitt 5). Enthält keine Geschäftsregeln: Sie zeigt nur an, was die
 * Use Cases zurückmelden.
 */
class AlbumWindow extends JDialog {

    static final int IMAGE_WIDTH = 800;
    static final int IMAGE_HEIGHT = 600;

    private final UUID albumId;
    private final OpenAlbumUseCase openAlbum;
    private final AddPhotoUseCase addPhoto;
    private final LoadPhotoUseCase loadPhoto;
    private final EditPhotoTextsUseCase editPhotoTexts;
    private final ImageBuffer buffer;
    private final JFileChooser fileChooser = new JFileChooser();
    private final JLabel heading = new JLabel();
    private final JLabel message = new JLabel(" ");
    private final JLabel image = new JLabel("", SwingConstants.CENTER);
    private final JLabel photoTitle = new JLabel(" ");
    private final JTextArea photoDescription = new JTextArea(3, 60);
    private final JLabel counter = new JLabel(" ");
    private final JButton editTextsButton = new JButton("Texte bearbeiten");
    private final JButton previousButton = new JButton("Vorheriges Foto");
    private final JButton nextButton = new JButton("Nächstes Foto");
    private List<PhotoSummary> photos = List.of();
    private List<UUID> photoIds = List.of();
    private int current;

    AlbumWindow(Frame owner, AlbumDetails album, OpenAlbumUseCase openAlbum, AddPhotoUseCase addPhoto,
            LoadPhotoUseCase loadPhoto, EditPhotoTextsUseCase editPhotoTexts) {
        super(owner, "Album „" + album.name() + "“", true);
        this.albumId = album.id();
        this.openAlbum = openAlbum;
        this.addPhoto = addPhoto;
        this.loadPhoto = loadPhoto;
        this.editPhotoTexts = editPhotoTexts;
        this.buffer = new ImageBuffer(this::loadImage);

        JButton backButton = new JButton("Zurück zur Albumübersicht");
        backButton.addActionListener(event -> dispose());
        JButton addButton = new JButton("Foto hinzufügen");
        addButton.addActionListener(event -> chooseAndAddPhoto());
        editTextsButton.addActionListener(event -> openTextsDialog());
        previousButton.addActionListener(event -> showPhoto(current - 1));
        nextButton.addActionListener(event -> showPhoto(current + 1));
        message.setForeground(new Color(0xB00020));
        image.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        photoTitle.setFont(photoTitle.getFont().deriveFont(Font.BOLD));
        photoTitle.putClientProperty("html.disable", Boolean.TRUE);
        photoDescription.setEditable(false);
        photoDescription.setLineWrap(true);
        photoDescription.setWrapStyleWord(true);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(backButton);
        buttons.add(addButton);
        buttons.add(editTextsButton);

        JPanel header = new JPanel(new BorderLayout(0, 4));
        header.add(heading, BorderLayout.NORTH);
        header.add(buttons, BorderLayout.CENTER);
        header.add(message, BorderLayout.SOUTH);

        JPanel texts = new JPanel(new BorderLayout(0, 4));
        texts.add(photoTitle, BorderLayout.NORTH);
        texts.add(new JScrollPane(photoDescription), BorderLayout.CENTER);

        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navigation.add(previousButton);
        navigation.add(counter);
        navigation.add(nextButton);

        JPanel bottom = new JPanel(new BorderLayout(0, 8));
        bottom.add(texts, BorderLayout.CENTER);
        bottom.add(navigation, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(header, BorderLayout.NORTH);
        content.add(image, BorderLayout.CENTER);
        content.add(bottom, BorderLayout.SOUTH);

        setContentPane(content);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(owner);
        showAlbum(album, 0);
    }

    private void showAlbum(AlbumDetails album, int index) {
        photos = album.photos();
        photoIds = photos.stream().map(PhotoSummary::id).toList();
        heading.setText("Album „" + album.name() + "“ – " + photoCount(photoIds.size()));
        editTextsButton.setEnabled(!photos.isEmpty());
        if (photos.isEmpty()) {
            image.setIcon(null);
            image.setText("Dieses Album enthält noch keine Fotos.");
            photoTitle.setText(" ");
            photoDescription.setText("");
            counter.setText(" ");
            previousButton.setEnabled(false);
            nextButton.setEnabled(false);
            return;
        }
        showPhoto(index);
    }

    private void showPhoto(int index) {
        current = index;
        message.setText(" ");
        counter.setText("Foto " + (index + 1) + " von " + photoIds.size());
        previousButton.setEnabled(index > 0);
        nextButton.setEnabled(index < photoIds.size() - 1);
        showTexts(photos.get(index));

        Result<BufferedImage> result = buffer.show(photoIds, index);
        if (result.isSuccess()) {
            image.setText(null);
            image.setIcon(new ImageIcon(result.getValue()));
        } else {
            image.setIcon(null);
            image.setText(result.getMessage());
        }

        List<UUID> shownIds = photoIds;
        SwingUtilities.invokeLater(() -> {
            if (current == index && photoIds == shownIds) {
                buffer.preloadNeighbours(shownIds, index);
            }
        });
    }

    private void showTexts(PhotoSummary photo) {
        photoTitle.setText(photo.title().isEmpty() ? "(kein Titel)" : photo.title());
        photoDescription.setText(photo.description().isEmpty() ? "(keine Beschreibung)" : photo.description());
        photoDescription.setCaretPosition(0);
    }

    private void chooseAndAddPhoto() {
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        Result<PhotoSummary> added = addPhoto.execute(albumId, fileChooser.getSelectedFile().toPath());
        if (!added.isSuccess()) {
            message.setText(added.getMessage());
            return;
        }
        reloadAndShow(added.getValue().id());
    }

    private void openTextsDialog() {
        PhotoSummary photo = photos.get(current);
        new PhotoTextsDialog(this, photo,
                (title, description) -> editPhotoTexts.execute(albumId, photo.id(), title, description))
                .showDialog()
                .ifPresent(saved -> reloadAndShow(saved.id()));
    }

    private void reloadAndShow(UUID photoId) {
        Result<AlbumDetails> reloaded = openAlbum.execute(albumId);
        if (!reloaded.isSuccess()) {
            message.setText(reloaded.getMessage());
            return;
        }
        List<UUID> ids = reloaded.getValue().photos().stream().map(PhotoSummary::id).toList();
        showAlbum(reloaded.getValue(), Math.max(0, ids.indexOf(photoId)));
    }

    private Result<BufferedImage> loadImage(UUID photoId) {
        Result<byte[]> data = loadPhoto.execute(photoId);
        if (!data.isSuccess()) {
            return Result.failure(data.getMessage());
        }
        return ImageDecoder.decode(data.getValue(), IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    private static String photoCount(int count) {
        return count == 1 ? "1 Foto" : count + " Fotos";
    }
}
