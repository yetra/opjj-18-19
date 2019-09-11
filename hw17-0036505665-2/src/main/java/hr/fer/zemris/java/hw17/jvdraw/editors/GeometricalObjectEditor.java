package hr.fer.zemris.java.hw17.jvdraw.editors;

import javax.swing.*;

/**
 * A class for supporting the editing of geometrical object properties.
 *
 * @author Bruna Dujmović
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

    /**
     * Checks if the editor fields are correctly filled out.
     *
     * @throws IllegalArgumentException if the fields are invalid
     */
    public abstract void checkEditing();

    /**
     * Writes all the editor fields to the geometrical object.
     * It is assumed that {@link #checkEditing()} was called before
     * this method.
     */
    public abstract void acceptEditing();
}
