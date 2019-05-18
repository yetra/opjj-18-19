package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class is an implementation of the {@link MultipleDocumentModel} interface
 * that represents a model capable of holding zero, one or more documents.
 *
 * @see MultipleDocumentModel
 * @author Bruna Dujmović
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

    /**
     * The {@link SingleDocumentModel} that is on the current tab,
     */
    private SingleDocumentModel currentDocument;

    /**
     * A list of {@link SingleDocumentModel} in this tabbed pane.
     */
    private List<SingleDocumentModel> models = new ArrayList<>();

    /**
     * A list of listeners for this document model.
     */
    private List<MultipleDocumentListener> listeners = new ArrayList<>();

    @Override
    public SingleDocumentModel createNewDocument() {
        createNewCurrentDocument(null, "");

        return currentDocument;
    }

    private void createNewCurrentDocument(Path path, String text) {
        currentDocument = new DefaultSingleDocumentModel(path, text);
        models.add(currentDocument);

        addTab("(unnamed)", new JScrollPane(currentDocument.getTextComponent()));
        setSelectedIndex(models.size() - 1);
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        for (int i = 0, size = models.size(); i < size; i++) {
            SingleDocumentModel model = models.get(i);

            if (model.getFilePath().equals(path)) {
                setSelectedIndex(i);
                currentDocument = model;
            }
        }

        try {
            String textContent = Files.readString(path);
            createNewCurrentDocument(path, textContent);

        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "The given path does not point to a readable file!"
            );
        }

        return currentDocument;
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        for (SingleDocumentModel document : models) {
            Path filePath = document.getFilePath();

            if (filePath != null && filePath.equals(newPath)) {
                throw new IllegalArgumentException("The specified file is already opened!");
            }
        }

        try {
            Files.writeString(
                    newPath == null ? model.getFilePath() : newPath,
                    model.getTextComponent().getText(),
                    StandardOpenOption.CREATE
            );

        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "An I/O error occurred while writing to or creating the file!"
            );
        }
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        int index = models.indexOf(model);
        removeTabAt(index);
        models.remove(index);
        currentDocument = models.get(
                (index == models.size() - 1) ? index - 1 : index + 1
        );
    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public int getNumberOfDocuments() {
        return models.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return models.get(index);
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return new DocumentModelIterator();
    }

    /**
     * This class represents an iterator over {@link SingleDocumentModel} objects
     * stored in the {@link #models} list.
     */
    private class DocumentModelIterator implements Iterator<SingleDocumentModel> {

        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < models.size();
        }

        @Override
        public SingleDocumentModel next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return models.get(currentIndex++);
        }
    }
}