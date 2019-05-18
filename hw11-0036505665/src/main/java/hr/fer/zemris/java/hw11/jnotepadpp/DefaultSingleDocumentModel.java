package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is an implementation of the {@link SingleDocumentModel} interface
 * that represents a model of a single document.
 *
 * @see SingleDocumentModel
 * @author Bruna Dujmović
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

    /**
     * The path of the document.
     */
    private Path filePath;

    /**
     * The {@link JTextArea} component that shows the documents text content.
     */
    private JTextArea textArea;

    /**
     * A flag for checking if the document was modified.
     */
    private boolean modified = false;

    /**
     * A list of listeners for this document model.
     */
    private List<SingleDocumentListener> listeners = new ArrayList<>();

    /**
     * Constructs a {@link DefaultSingleDocumentModel} for a file specified by the
     * given path and text content.
     *
     * @param filePath the path of the document
     * @param textContent the text content of the document
     */
    public DefaultSingleDocumentModel(Path filePath, String textContent) {
        this.filePath = filePath;

        textArea = new JTextArea(textContent);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                modified = true;
                listeners.forEach(l -> l.documentModifyStatusUpdated(
                        DefaultSingleDocumentModel.this
                ));
            }
        });
    }

    @Override
    public JTextArea getTextComponent() {
        return textArea;
    }

    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(Path path) {
        filePath = Objects.requireNonNull(path);
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(l);
    }
}
