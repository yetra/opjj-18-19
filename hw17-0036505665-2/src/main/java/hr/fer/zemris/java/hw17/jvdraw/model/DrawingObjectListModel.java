package hr.fer.zemris.java.hw17.jvdraw.model;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;

import javax.swing.*;

/**
 * An object adapter for the {@link DrawingModel}.
 *
 * @see DrawingModel
 * @author Bruna Dujmović
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject>
        implements DrawingModelListener {

    /**
     * The underlying {@link DrawingModel} object.
     */
    private DrawingModel model;

    /**
     * Constructs a {@link DrawingObjectListModel} for the given {@link DrawingModel}.
     *
     * @param model the underlying {@link DrawingModel} object
     */
    public DrawingObjectListModel(DrawingModel model) {
        this.model = model;

        model.addDrawingModelListener(this);
    }

    @Override
    public int getSize() {
        return model.getSize();
    }

    @Override
    public GeometricalObject getElementAt(int index) {
        return model.getObject(index);
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        fireIntervalAdded(source, index0, index1);
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        fireIntervalRemoved(source, index0, index1);
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        fireContentsChanged(source, index0, index1);
    }
}
