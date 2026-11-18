package de.commitmentissues.photoalbum.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.Optional;
import java.util.function.BiFunction;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.commitmentissues.photoalbum.application.PhotoSummary;
import de.commitmentissues.photoalbum.application.Result;

/**
 * Dialog für Titel und Beschreibung eines Fotos (FA-10). Beide Felder lassen sich leeren.
 * Eine abgelehnte Eingabe wird im Dialog angezeigt, damit der Nutzer sie korrigieren oder abbrechen kann.
 */
class PhotoTextsDialog extends JDialog {

    private final BiFunction<String, String, Result<PhotoSummary>> action;
    private final JTextField titleField = new JTextField(40);
    private final JTextArea descriptionArea = new JTextArea(6, 40);
    private final JLabel message = new JLabel(" ");
    private PhotoSummary saved;

    PhotoTextsDialog(Window owner, PhotoSummary photo, BiFunction<String, String, Result<PhotoSummary>> action) {
        super(owner, "Texte bearbeiten", Dialog.ModalityType.APPLICATION_MODAL);
        this.action = action;

        titleField.setText(photo.title());
        descriptionArea.setText(photo.description());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        message.setForeground(new Color(0xB00020));

        JPanel titlePanel = new JPanel(new BorderLayout(0, 4));
        titlePanel.add(new JLabel("Titel"), BorderLayout.NORTH);
        titlePanel.add(titleField, BorderLayout.CENTER);

        JPanel descriptionPanel = new JPanel(new BorderLayout(0, 4));
        descriptionPanel.add(new JLabel("Beschreibung"), BorderLayout.NORTH);
        descriptionPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        JPanel form = new JPanel(new BorderLayout(0, 8));
        form.add(message, BorderLayout.NORTH);
        form.add(titlePanel, BorderLayout.CENTER);
        form.add(descriptionPanel, BorderLayout.SOUTH);

        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(event -> dispose());
        JButton saveButton = new JButton("Speichern");
        saveButton.addActionListener(event -> save());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(cancelButton);
        buttons.add(saveButton);

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(form, BorderLayout.CENTER);
        content.add(buttons, BorderLayout.SOUTH);

        setContentPane(content);
        getRootPane().setDefaultButton(saveButton);
        pack();
        setLocationRelativeTo(owner);
    }

    Optional<PhotoSummary> showDialog() {
        setVisible(true);
        return Optional.ofNullable(saved);
    }

    private void save() {
        Result<PhotoSummary> result = action.apply(titleField.getText(), descriptionArea.getText());
        if (result.isSuccess()) {
            saved = result.getValue();
            dispose();
        } else {
            message.setText(result.getMessage());
            pack();
        }
    }
}
