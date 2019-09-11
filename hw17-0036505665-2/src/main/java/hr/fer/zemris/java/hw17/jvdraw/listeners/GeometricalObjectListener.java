package hr.fer.zemris.java.hw17.jvdraw.listeners;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;

/**
 * A listener of changes to a specified {@link GeometricalObject}.
 *
 * @author Bruna Dujmović
 *
 */
public interface GeometricalObjectListener {

    /**
     * A method to be called when there is a change in the specified {@link GeometricalObject}.
     *
     * @param o the object whose changes should be observed
     */
    void geometricalObjectChanged(GeometricalObject o);
}
