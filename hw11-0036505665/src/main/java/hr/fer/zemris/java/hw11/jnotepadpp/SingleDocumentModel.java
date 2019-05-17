package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.nio.file.Path;

/**
 * This interface represents a model of a single document. It has information about the
 * file path from which the document was loaded, its document modification status, and
 * contains a reference to the Swing component that is used for editing the document.
 *
 * @author Bruna Dujmović
 *
 */
public interface SingleDocumentModel {

    /**
     * Returns a {@link JTextArea} component containing the text content of the document.
     *
     * @return a {@link JTextArea} component containing the text content of the document
     */
    JTextArea getTextComponent();

    /**
     * Returns the file path that was used to load the document.
     *
     * @return the file path that was used to load the document
     */
    Path getFilePath();

    /**
     * Sets the file path of the document to the specified path.
     *
     * @param path the new file path of the document
     */
    void setFilePath(Path path);

    /**
     * Returns {@code true} if the document was modified.
     *
     * @return {@code true} if the document was modified
     */
    boolean isModified();

    /**
     * Sets the document's modification flag to the specified value.
     *
     * @param modified the value of the modification flag
     */
    void setModified(boolean modified);

    /**
     * Registers a {@link SingleDocumentListener}.
     *
     * @param l the listener to register
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * Deregisters a {@link SingleDocumentListener}.
     *
     * @param l the listener to deregister
     */
    void removeSingleDocumentListener(SingleDocumentListener l);
}
