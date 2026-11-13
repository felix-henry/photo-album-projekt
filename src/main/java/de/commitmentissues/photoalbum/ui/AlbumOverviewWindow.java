package de.commitmentissues.photoalbum.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import de.commitmentissues.photoalbum.application.AlbumSummary;
import de.commitmentissues.photoalbum.application.CreateAlbumUseCase;
import de.commitmentissues.photoalbum.application.ListAlbumsUseCase;
import de.commitmentissues.photoalbum.application.RenameAlbumUseCase;
import de.commitmentissues.photoalbum.application.Result;

/**
 * Albumübersicht (FA-02) mit dem Einstieg in „Album erstellen“ (UC-02) und „Album umbenennen“ (FA-03).
 * Enthält keine Geschäftsregeln: Sie zeigt nur an, was die Use Cases zurückmelden.
 */
public class AlbumOverviewWindow extends JFrame {

    private final ListAlbumsUseCase listAlbums;
    private final CreateAlbumUseCase createAlbum;
    private final RenameAlbumUseCase renameAlbum;
    private final DefaultListModel<String> albumNames = new DefaultListModel<>();
    private final JList<String> albumList = new JList<>(albumNames);
    private final JButton renameButton = new JButton("Umbenennen");
    private final JLabel status = new JLabel(" ");
    private List<AlbumSummary> shownAlbums = List.of();

    public AlbumOverviewWindow(ListAlbumsUseCase listAlbums, CreateAlbumUseCase createAlbum,
            RenameAlbumUseCase renameAlbum) {
        super("Photo Album");
        this.listAlbums = listAlbums;
        this.createAlbum = createAlbum;
        this.renameAlbum = renameAlbum;

        JButton createButton = new JButton("Album erstellen");
        createButton.addActionListener(event -> openCreateDialog());
        renameButton.addActionListener(event -> openRenameDialog());
        renameButton.setEnabled(false);
        albumList.addListSelectionListener(event -> renameButton.setEnabled(albumList.getSelectedIndex() >= 0));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Albumübersicht"));
        top.add(createButton);
        top.add(renameButton);

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(top, BorderLayout.NORTH);
        content.add(new JScrollPane(albumList), BorderLayout.CENTER);
        content.add(status, BorderLayout.SOUTH);

        setContentPane(content);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(480, 360);
        setLocationRelativeTo(null);
        refresh();
    }

    private void refresh() {
        Result<List<AlbumSummary>> result = listAlbums.execute();
        albumNames.clear();
        if (!result.isSuccess()) {
            shownAlbums = List.of();
            status.setText(result.getMessage());
            return;
        }
        shownAlbums = result.getValue();
        shownAlbums.forEach(album -> albumNames.addElement(album.name()));
        status.setText(shownAlbums.isEmpty() ? "Noch keine Alben vorhanden." : " ");
    }

    private void openCreateDialog() {
        new AlbumNameDialog(this, "Album erstellen", "", createAlbum::execute)
                .showDialog()
                .ifPresent(this::refreshAndSelect);
    }

    private void openRenameDialog() {
        int index = albumList.getSelectedIndex();
        if (index < 0) {
            return;
        }
        AlbumSummary album = shownAlbums.get(index);
        new AlbumNameDialog(this, "Album umbenennen", album.name(),
                newName -> renameAlbum.execute(album.id(), newName))
                .showDialog()
                .ifPresent(this::refreshAndSelect);
    }

    private void refreshAndSelect(AlbumSummary album) {
        refresh();
        albumList.setSelectedValue(album.name(), true);
    }
}
