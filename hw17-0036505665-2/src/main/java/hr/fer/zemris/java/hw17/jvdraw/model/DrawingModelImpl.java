package hr.fer.zemris.java.hw17.jvdraw.model;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the {@link DrawingModel} interface.
 *
 * @see DrawingModel
 * @author Bruna Dujmović
 *
 */
public class DrawingModelImpl implements DrawingModel {

    /**
     * A list of {@link GeometricalObject}s contained in this model.
     */
    private List<GeometricalObject> objects = new ArrayList<>();

    /**
     * A list of registered {@link DrawingModelListener} objects.
     */
    private List<DrawingModelListener> listeners = new ArrayList<>();

    /**
     * {@code true} if any geometrical objects are added or removed
     * from the model, or are changed
     */
    private boolean isModified = false;

    @Override
    public int getSize() {
        return objects.size();
    }

    @Override
    public GeometricalObject getObject(int index) {
        return objects.get(index);
    }

    @Override
    public void add(GeometricalObject object) {
        int index = objects.size();
        objects.add(object);
        object.addGeometricalObjectListener(this);

        isModified = true;
        listeners.forEach(l -> l.objectsAdded(this, index, index));
    }

    @Override
    public void remove(GeometricalObject object) {
        int index = objects.indexOf(object);
        objects.get(index).removeGeometricalObjectListener(this);
        objects.remove(object);

        isModified = true;
        listeners.forEach(l -> l.objectsRemoved(this, index, index));
    }

    @Override
    public void changeOrder(GeometricalObject object, int offset) {
        int index = objects.indexOf(object);
        int newIndex = index + offset;

        if (newIndex >= 0 && newIndex < objects.size()) {
            GeometricalObject oldObject = objects.get(index);
            objects.set(index, object);
            objects.set(newIndex, oldObject);

            isModified = true;
            listeners.forEach(l -> l.objectsChanged(this, index, newIndex));
        }
    }

    @Override
    public int indexOf(GeometricalObject object) {
        return objects.indexOf(object);
    }

    @Override
    public void clear() {
        int size = objects.size();
        objects.clear();

        isModified = true;
        listeners.forEach(l -> l.objectsRemoved(this, 0, size));
    }

    @Override
    public void clearModifiedFlag() {
        isModified = false;
    }

    @Override
    public boolean isModified() {
        return isModified;
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener l) {
        listeners.remove(l);
    }

    @Override
    public void geometricalObjectChanged(GeometricalObject o) {
        int index = objects.indexOf(o);

        listeners.forEach(l -> l.objectsChanged(this, index, index));
    }
}
