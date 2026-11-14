package de.commitmentissues.photoalbum.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import de.commitmentissues.photoalbum.application.AddPhotoUseCase;
import de.commitmentissues.photoalbum.application.AlbumDetails;
import de.commitmentissues.photoalbum.application.LoadPhotoUseCase;
import de.commitmentissues.photoalbum.application.OpenAlbumUseCase;
import de.commitmentissues.photoalbum.application.PhotoSummary;
import de.commitmentissues.photoalbum.application.Result;

/**
 * Albumansicht (FA-08) mit „Foto hinzufügen“ (UC-01). Zeigt die Fotos einzeln in Anzeigegröße;
 * die Bilder kommen über den Bildpuffer (NFA-03, ARCHITECTURE, Abschnitt 5).
 * Enthält keine Geschäftsregeln: Sie zeigt nur an, was die Use Cases zurückmelden.
 */
class AlbumWindow extends JDialog {

    static final int IMAGE_WIDTH = 800;
    static final int IMAGE_HEIGHT = 600;

    private final UUID albumId;
    private final OpenAlbumUseCase openAlbum;
    private final AddPhotoUseCase addPhoto;
    private final LoadPhotoUseCase loadPhoto;
    private final ImageBuffer buffer;
    private final JFileChooser fileChooser = new JFileChooser();
    private final JLabel heading = new JLabel();
    private final JLabel message = new JLabel(" ");
    private final JLabel image = new JLabel("", SwingConstants.CENTER);
    private final JLabel counter = new JLabel(" ");
    private final JButton previousButton = new JButton("Vorheriges Foto");
    private final JButton nextButton = new JButton("Nächstes Foto");
    private List<UUID> photoIds = List.of();
    private int current;

    AlbumWindow(Frame owner, AlbumDetails album, OpenAlbumUseCase openAlbum, AddPhotoUseCase addPhoto,
            LoadPhotoUseCase loadPhoto) {
        super(owner, "Album „" + album.name() + "“", true);
        this.albumId = album.id();
        this.openAlbum = openAlbum;
        this.addPhoto = addPhoto;
        this.loadPhoto = loadPhoto;
        this.buffer = new ImageBuffer(this::loadImage);

        JButton backButton = new JButton("Zurück zur Albumübersicht");
        backButton.addActionListener(event -> dispose());
        JButton addButton = new JButton("Foto hinzufügen");
        addButton.addActionListener(event -> chooseAndAddPhoto());
        previousButton.addActionListener(event -> showPhoto(current - 1));
        nextButton.addActionListener(event -> showPhoto(current + 1));
        message.setForeground(new Color(0xB00020));
        image.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(backButton);
        buttons.add(addButton);

        JPanel header = new JPanel(new BorderLayout(0, 4));
        header.add(heading, BorderLayout.NORTH);
        header.add(buttons, BorderLayout.CENTER);
        header.add(message, BorderLayout.SOUTH);

        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navigation.add(previousButton);
        navigation.add(counter);
        navigation.add(nextButton);

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(header, BorderLayout.NORTH);
        content.add(image, BorderLayout.CENTER);
        content.add(navigation, BorderLayout.SOUTH);

        setContentPane(content);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(owner);
        showAlbum(album, 0);
    }

    private void showAlbum(AlbumDetails album, int index) {
        photoIds = album.photos().stream().map(PhotoSummary::id).toList();
        heading.setText("Album „" + album.name() + "“ – " + photoCount(photoIds.size()));
        if (photoIds.isEmpty()) {
            image.setIcon(null);
            image.setText("Dieses Album enthält noch keine Fotos.");
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

    private void chooseAndAddPhoto() {
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        Result<PhotoSummary> added = addPhoto.execute(albumId, fileChooser.getSelectedFile().toPath());
        if (!added.isSuccess()) {
            message.setText(added.getMessage());
            return;
        }
        Result<AlbumDetails> reloaded = openAlbum.execute(albumId);
        if (!reloaded.isSuccess()) {
            message.setText(reloaded.getMessage());
            return;
        }
        int newIndex = reloaded.getValue().photos().indexOf(added.getValue());
        showAlbum(reloaded.getValue(), Math.max(0, newIndex));
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
