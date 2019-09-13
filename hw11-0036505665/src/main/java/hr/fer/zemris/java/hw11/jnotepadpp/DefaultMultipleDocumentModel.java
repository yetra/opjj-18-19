package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
     * The icon used for modified documents.
     */
    private static final ImageIcon MODIFIED_ICON = loadIcon("icons/modified.png");
    /**
     * The icon used for unmodified documents.
     */
    private static final ImageIcon UNMODIFIED_ICON = loadIcon("icons/unmodified.png");

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

    /**
     * Constructs a new {@link DefaultMultipleDocumentModel}.
     */
    public DefaultMultipleDocumentModel() {
        // listen for tab change and update currentDocument accordingly
        this.addChangeListener(e -> {
            int selected = getSelectedIndex();
            SingleDocumentModel previousDocument = currentDocument;
            currentDocument = selected == -1 ? null : models.get(selected);

            listeners.forEach(
                    l -> l.currentDocumentChanged(previousDocument, currentDocument)
            );
        });
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        newCurrentDocument(null, "");
        currentDocument.setModified(true);

        return currentDocument;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        for (int i = 0, size = models.size(); i < size; i++) {
            SingleDocumentModel model = models.get(i);

            if (model.getFilePath() != null && model.getFilePath().equals(path)) {
                setSelectedIndex(i);
                return model;
            }
        }

        try {
            String textContent = Files.readString(path);
            newCurrentDocument(path, textContent);

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

            if (filePath != null && document != model && filePath.equals(newPath)) {
                throw new IllegalArgumentException("The specified file is already opened!");
            }
        }

        try {
            Path pathToSave = newPath == null ? model.getFilePath() : newPath;

            Files.writeString(pathToSave, model.getTextComponent().getText());
            model.setFilePath(pathToSave);
            model.setModified(false);

            int index = models.indexOf(currentDocument);
            setToolTipTextAt(index, pathToSave.toString());
            setTitleAt(index, pathToSave.getFileName().toString());
            setIconAt(index, UNMODIFIED_ICON);

        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "An I/O error occurred while writing to or creating the file!"
            );
        }
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        int index = models.indexOf(model);
        models.remove(index);
        removeTabAt(index);
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

            setSelectedIndex(currentIndex);
            return models.get(currentIndex++);
        }
    }

    /**
     * Creates a new current document specified by the given path and text and sets
     * it as the current tab.
     *
     * @param path the path of the document
     * @param text the text of the document
     */
    private void newCurrentDocument(Path path, String text) {
        SingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, text);
        newDocument.addSingleDocumentListener(documentChange);
        models.add(newDocument);

        addTab(
                path == null ? "(unnamed)" : path.getFileName().toString(),
                path == null ? MODIFIED_ICON : UNMODIFIED_ICON,
                new JScrollPane(newDocument.getTextComponent()),
                path == null ? "(unnamed)" : path.toString()
        );

        setSelectedIndex(models.size() - 1);
    }

    /**
     * Loads a resource specified by a given name and returns it as an {@link ImageIcon}.
     *
     * @param name the name of the resource
     * @return an {@link ImageIcon} representing the specified resource
     */
    private static ImageIcon loadIcon(String name) {
        try (InputStream is = DefaultMultipleDocumentModel.class.getResourceAsStream(name)) {
            if (is == null) {
                throw new IllegalArgumentException("No such resource found!");
            }

            byte[] bytes = is.readAllBytes();
            return new ImageIcon(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * A {@link SingleDocumentListener} that listens for changes in the document's
     * content and updates its icon accordingly.
     */
    private final SingleDocumentListener documentChange = new SingleDocumentListener() {
        @Override
        public void documentModifyStatusUpdated(SingleDocumentModel model) {
            setIconAt(models.indexOf(model), MODIFIED_ICON);
        }

        @Override
        public void documentFilePathUpdated(SingleDocumentModel model) {
        }
    };
}
