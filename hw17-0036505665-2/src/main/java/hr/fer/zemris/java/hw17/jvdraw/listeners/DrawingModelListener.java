package hr.fer.zemris.java.hw17.jvdraw.listeners;

import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

/**
 * A listener of changes in the {@link DrawingModel}.
 *
 * @author Bruna Dujmović
 *
 */
public interface  DrawingModelListener {

    /**
     * A method to be called when adding an object to the {@link DrawingModel}.
     *
     * @param source the {@link DrawingModel}
     * @param index0 the start index of the adding
     * @param index1 the end index of the adding
     */
    void objectsAdded(DrawingModel source, int index0, int index1);

    /**
     * A method to be called when removing an object from the {@link DrawingModel}.
     *
     * @param source the {@link DrawingModel}
     * @param index0 the start index of the removal
     * @param index1 the end index of the removal
     */
    void objectsRemoved(DrawingModel source, int index0, int index1);

    /**
     * A method to be called when changing an object in the {@link DrawingModel}.
     *
     * @param source the {@link DrawingModel}
     * @param index0 the start index of the change
     * @param index1 the end index of the change
     */
    void objectsChanged(DrawingModel source, int index0, int index1);
}
