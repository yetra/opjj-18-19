package hr.fer.zemris.java.hw17.jvdraw.model;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.listeners.GeometricalObjectListener;

/**
 * An interface that models a collection of geometrical objects to be drawn.
 *
 * @author Bruna Dujmović
 *
 */
public interface DrawingModel extends GeometricalObjectListener {

    /**
     * Returns the size of the model.
     *
     * @return the size of the model
     */
    int getSize();

    /**
     * Returns the model's {@link GeometricalObject} on the specified index.
     *
     * @param index the index of the object
     * @return the model's {@link GeometricalObject} on the specified index
     */
    GeometricalObject getObject(int index);

    /**
     * Adds the given {@link GeometricalObject} to the model.
     *
     * @param object the object to add
     */
    void add(GeometricalObject object);

    /**
     * Removes the given {@link GeometricalObject} from the model.
     *
     * @param object the object to remove
     */
    void remove(GeometricalObject object);

    /**
     * Moves the given {@link GeometricalObject} from its current position
     * to the current position plus provided offset.
     *
     * @param object the object to reorder
     * @param offset the offset for the new position
     */
    void changeOrder(GeometricalObject object, int offset);

    /**
     * Returns the index of the given {@link GeometricalObject}.
     *
     * @param object the objects whose index should be returned
     * @return the index of the given {@link GeometricalObject}
     */
    int indexOf(GeometricalObject object);

    /**
     * Clears the model.
     */
    void clear();

    /**
     * Resets the model modification flag back to {@code false}.
     */
    void clearModifiedFlag();

    /**
     * Returns {@code true} if any geometrical objects are added or removed
     * from the model, or are changed.
     *
     * @return {@code true} if the model has been modified
     */
    boolean isModified();

    /**
     * Registers the given {@link DrawingModelListener}.
     *
     * @param l the listener to register
     */
    void addDrawingModelListener(DrawingModelListener l);

    /**
     * Deregisters the given {@link DrawingModelListener}.
     *
     * @param l the listener to deregister
     */
    void removeDrawingModelListener(DrawingModelListener l);
}
