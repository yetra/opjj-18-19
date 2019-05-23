package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * The listener interface for receiving single document events. It is implemented by
 * classes that wish to process single document events, and the objects created with
 * those classes are registered with a component. When a single document event occurs,
 * one of the methods specified below is invoked.
 *
 * @author Bruna Dujmović
 *
 */
public interface SingleDocumentListener {

    /**
     * Invoked when a document's modification status is updated.
     *
     * @param model the model of the document whose status was updated
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * Invoked when a document's file path is updated.
     *
     * @param model the model of the document whose file path was updated
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}
