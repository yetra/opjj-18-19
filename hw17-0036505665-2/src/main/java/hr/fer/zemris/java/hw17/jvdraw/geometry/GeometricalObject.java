package hr.fer.zemris.java.hw17.jvdraw.geometry;

import hr.fer.zemris.java.hw17.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Models a geometrical object that can be drawn on the canvas.
 *
 * @author Bruna Dujmović
 *
 */
public abstract class GeometricalObject {

    /**
     * A list of registered {@link GeometricalObjectListener} objects.
     */
    private List<GeometricalObjectListener> listeners = new ArrayList<>();

    /**
     * Accepts the specified {@link GeometricalObjectVisitor} object
     * (calls the appropriate visit method).
     *
     * @param v the visitor to accept
     */
    public abstract void accept(GeometricalObjectVisitor v);

    /**
     * Creates a {@link GeometricalObjectEditor} for the given {@link GeometricalObject}.
     *
     * @return a {@link GeometricalObjectEditor} for the given {@link GeometricalObject}
     */
    public abstract GeometricalObjectEditor createGeometricalObjectEditor();

    /**
     * Registers a {@link GeometricalObjectVisitor}.
     *
     * @param l the listener to register
     */
    public void addGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.add(l);
    }

    /**
     * Deregisters the given {@link GeometricalObjectVisitor}.
     *
     * @param l the listener to deregister
     */
    public void removeGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.remove(l);
    }

    /**
     * Notifies all registered {@link GeometricalObjectListener} objects.
     */
    void notifyListeners() {
        listeners.forEach(l -> l.geometricalObjectChanged(this));
    }
}
