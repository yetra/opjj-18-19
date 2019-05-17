package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * This interface represents a model capable of holding zero, one or more documents.
 * It keeps track of the document currently shown to the user.
 *
 * @author Bruna Dujmović
 *
 */
public interface MultipleDocumentModel {

    /**
     * Returns a newly created blank document.
     *
     * @return a newly created blank document
     */
    SingleDocumentModel createNewDocument();

    /**
     * Returns the document currently shown to the user.
     *
     * @return the document currently shown to the user
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Returns a document loaded from the given path.
     *
     * @param path the path to the document to load
     * @return a document loaded from the given path
     */
    SingleDocumentModel loadDocument(Path path);

    /**
     * Saves the specified document specified by a given {@link SingleDocumentModel}
     * to the given path.
     *
     * @param model the model of the document to save
     * @param newPath the path for saving the specified document
     */
    void saveDocument(SingleDocumentModel model, Path newPath);

    /**
     * Closes the document specified by the given {@link SingleDocumentModel}.
     *
     * @param model the model of the document to close
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Registers the given {@link MultipleDocumentListener}.
     *
     * @param l the listener to register
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Deregisters the given {@link MultipleDocumentListener}.
     *
     * @param l the listener to remove
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Returns the number of documents that this {@link MultipleDocumentModel}
     * models.
     *
     * @return the number of documents that this {@link MultipleDocumentModel} model
     */
    int getNumberOfDocuments();

    /**
     * Returns a {@link SingleDocumentModel} of the document specified by a given index.
     *
     * @param index the index of the document
     * @return a {@link SingleDocumentModel} of the document specified by a given index
     */
    SingleDocumentModel getDocument(int index);
}
