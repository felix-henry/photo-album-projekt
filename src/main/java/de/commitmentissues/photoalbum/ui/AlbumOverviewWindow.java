package de.commitmentissues.photoalbum.ui;

import de.commitmentissues.photoalbum.application.AlbumSummary;
import de.commitmentissues.photoalbum.application.CreateAlbumUseCase;
import de.commitmentissues.photoalbum.application.ListAlbumsUseCase;
import de.commitmentissues.photoalbum.application.Result;
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

/**
 * Albumübersicht (FA-02) mit dem Einstieg in „Album erstellen“ (UC-02).
 * Enthält keine Geschäftsregeln: Sie zeigt nur an, was die Use Cases zurückmelden.
 */
public class AlbumOverviewWindow extends JFrame {

    private final ListAlbumsUseCase listAlbums;
    private final CreateAlbumUseCase createAlbum;
    private final DefaultListModel<String> albumNames = new DefaultListModel<>();
    private final JList<String> albumList = new JList<>(albumNames);
    private final JLabel status = new JLabel(" ");

    public AlbumOverviewWindow(ListAlbumsUseCase listAlbums, CreateAlbumUseCase createAlbum) {
        super("Photo Album");
        this.listAlbums = listAlbums;
        this.createAlbum = createAlbum;

        JButton createButton = new JButton("Album erstellen");
        createButton.addActionListener(event -> openCreateDialog());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Albumübersicht"));
        top.add(createButton);

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
            status.setText(result.getMessage());
            return;
        }
        result.getValue().forEach(album -> albumNames.addElement(album.name()));
        status.setText(albumNames.isEmpty() ? "Noch keine Alben vorhanden." : " ");
    }

    private void openCreateDialog() {
        new CreateAlbumDialog(this, createAlbum).showDialog().ifPresent(created -> {
            refresh();
            albumList.setSelectedValue(created.name(), true);
        });
    }
}
