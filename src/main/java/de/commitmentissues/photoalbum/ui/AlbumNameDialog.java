package de.commitmentissues.photoalbum.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Optional;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.commitmentissues.photoalbum.application.AlbumSummary;
import de.commitmentissues.photoalbum.application.Result;

/**
 * Dialog zur Eingabe eines Albumnamens für „Album erstellen“ (UC-02) und „Album umbenennen“ (FA-03).
 * Eine abgelehnte Eingabe wird im Dialog angezeigt, damit der Nutzer den Namen korrigieren oder abbrechen kann.
 */
class AlbumNameDialog extends JDialog {

    private final Function<String, Result<AlbumSummary>> action;
    private final JTextField nameField = new JTextField(30);
    private final JLabel message = new JLabel(" ");
    private AlbumSummary saved;

    AlbumNameDialog(Frame owner, String title, String initialName, Function<String, Result<AlbumSummary>> action) {
        super(owner, title, true);
        this.action = action;

        nameField.setText(initialName);
        nameField.selectAll();
        message.setForeground(new Color(0xB00020));

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 4));
        form.add(message);
        form.add(new JLabel("Albumname"));
        form.add(nameField);

        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(event -> dispose());
        JButton confirmButton = new JButton("Bestätigen");
        confirmButton.addActionListener(event -> confirm());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(cancelButton);
        buttons.add(confirmButton);

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(form, BorderLayout.CENTER);
        content.add(buttons, BorderLayout.SOUTH);

        setContentPane(content);
        getRootPane().setDefaultButton(confirmButton);
        pack();
        setLocationRelativeTo(owner);
    }

    Optional<AlbumSummary> showDialog() {
        setVisible(true);
        return Optional.ofNullable(saved);
    }

    private void confirm() {
        Result<AlbumSummary> result = action.apply(nameField.getText());
        if (result.isSuccess()) {
            saved = result.getValue();
            dispose();
        } else {
            message.setText(result.getMessage());
            pack();
            nameField.requestFocusInWindow();
            nameField.selectAll();
        }
    }
}
