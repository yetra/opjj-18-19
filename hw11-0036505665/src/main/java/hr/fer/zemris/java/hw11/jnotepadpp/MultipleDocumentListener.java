package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * The listener interface for receiving multiple document events. It is implemented by
 * classes that wish to process multiple document events, and the objects created with
 * those classes are registered with a component. When a multiple document event occurs,
 * one of the methods specified below is invoked.
 *
 * @author Bruna Dujmović
 *
 */
public interface MultipleDocumentListener {

    /**
     * Invoked when the current document changes from {@code previousModel} to {@code
     * currentModel}.
     *
     * @param previousModel the model of the document before the change
     * @param currentModel the model of the document after the change
     * @throws NullPointerException if the previous and current model are {@code null}
     */
    void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

    /**
     * Invoked when a document is a
     *
     * @param model the model of the document that was added
     */
    void documentAdded(SingleDocumentModel model);

    /**
     * Invoked when a document is removed.
     *
     * @param model the model of the document that was removed
     */
    void documentRemoved(SingleDocumentModel model);
}
