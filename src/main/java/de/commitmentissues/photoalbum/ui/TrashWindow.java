package de.commitmentissues.photoalbum.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import de.commitmentissues.photoalbum.application.AlbumSummary;
import de.commitmentissues.photoalbum.application.Result;
import de.commitmentissues.photoalbum.application.TrashEntrySummary;

/**
 * Papierkorb (A-08) mit „Wiederherstellen“ und „In neues Album wiederherstellen“ (A-09) sowie „Endgültig
 * löschen“ mit Rückfrage (SR-01, A-10). Vor dem Anzeigen bereinigt der Use Case fällige Einträge (A-10).
 * Enthält keine Geschäftsregeln: Es zeigt nur an, was die Use Cases zurückmelden.
 */
class TrashWindow extends JDialog {

    private static final DateTimeFormatter DELETED_AT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm").withZone(ZoneId.systemDefault());

    private final UseCases useCases;
    private final DefaultListModel<String> entryTexts = new DefaultListModel<>();
    private final JList<String> entryList = new JList<>(entryTexts);
    private final JButton restoreButton = new JButton("Wiederherstellen");
    private final JButton restoreIntoNewButton = new JButton("In neues Album wiederherstellen");
    private final JButton deleteButton = new JButton("Endgültig löschen");
    private final JLabel status = new JLabel(" ");
    private List<TrashEntrySummary> shownEntries = List.of();

    TrashWindow(Frame owner, UseCases useCases) {
        super(owner, "Papierkorb", true);
        this.useCases = useCases;

        JButton backButton = new JButton("Zurück zur Albumübersicht");
        backButton.addActionListener(event -> dispose());
        restoreButton.addActionListener(event -> restoreSelected());
        restoreIntoNewButton.addActionListener(event -> restoreSelectedIntoNewAlbum());
        deleteButton.addActionListener(event -> deleteSelectedPermanently());
        entryList.addListSelectionListener(event -> updateButtons());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(backButton);
        top.add(restoreButton);
        top.add(restoreIntoNewButton);
        top.add(deleteButton);

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(top, BorderLayout.NORTH);
        content.add(new JScrollPane(entryList), BorderLayout.CENTER);
        content.add(status, BorderLayout.SOUTH);

        setContentPane(content);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(820, 400);
        setLocationRelativeTo(owner);
        refresh();
    }

    private void refresh() {
        Result<List<TrashEntrySummary>> result = useCases.listTrash().execute();
        entryTexts.clear();
        shownEntries = result.isSuccess() ? result.getValue() : List.of();
        shownEntries.forEach(entry -> entryTexts.addElement(describe(entry)));
        updateButtons();
        if (!result.isSuccess()) {
            status.setText(result.getMessage());
        } else {
            status.setText(shownEntries.isEmpty() ? "Der Papierkorb ist leer." : " ");
        }
    }

    private Optional<TrashEntrySummary> selected() {
        int index = entryList.getSelectedIndex();
        return index < 0 ? Optional.empty() : Optional.of(shownEntries.get(index));
    }

    private void updateButtons() {
        Optional<TrashEntrySummary> entry = selected();
        boolean restorable = entry.isPresent() && !entry.get().due();
        restoreButton.setEnabled(restorable);
        restoreIntoNewButton.setEnabled(restorable);
        deleteButton.setEnabled(entry.isPresent());
    }

    private void restoreSelected() {
        selected().ifPresent(entry -> {
            Result<AlbumSummary> result = useCases.restoreTrashEntry().execute(entry.id());
            refresh();
            status.setText(result.isSuccess() ? restoredInto(result.getValue()) : result.getMessage());
        });
    }

    private void restoreSelectedIntoNewAlbum() {
        selected().ifPresent(entry -> new AlbumNameDialog(this, "In neues Album wiederherstellen",
                entry.originAlbumName(), name -> useCases.restoreTrashEntry().executeIntoNewAlbum(entry.id(), name))
                .showDialog()
                .ifPresent(album -> {
                    refresh();
                    status.setText(restoredInto(album));
                }));
    }

    private void deleteSelectedPermanently() {
        selected().ifPresent(entry -> {
            int answer = JOptionPane.showConfirmDialog(this,
                    "Eintrag endgültig löschen? Das lässt sich nicht rückgängig machen.", "Endgültig löschen",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
            Result<Void> result = useCases.deletePermanently().execute(entry.id());
            refresh();
            status.setText(result.isSuccess() ? "Endgültig gelöscht." : result.getMessage());
        });
    }

    private static String describe(TrashEntrySummary entry) {
        String what = entry.album()
                ? "Album „" + entry.originAlbumName() + "“ · " + photoCount(entry.photoCount())
                : "Foto" + titleOf(entry) + " aus „" + entry.originAlbumName() + "“";
        String days = entry.due()
                ? "Frist abgelaufen"
                : "noch " + (entry.remainingDays() == 1 ? "1 Tag" : entry.remainingDays() + " Tage");
        return what + " · gelöscht am " + DELETED_AT.format(entry.deletedAt()) + " · " + days;
    }

    private static String titleOf(TrashEntrySummary entry) {
        String title = entry.photos().get(0).title();
        return title.isEmpty() ? "" : " „" + title + "“";
    }

    private static String restoredInto(AlbumSummary album) {
        return "Wiederhergestellt in „" + album.name() + "“.";
    }

    private static String photoCount(int count) {
        return count == 1 ? "1 Foto" : count + " Fotos";
    }
}
