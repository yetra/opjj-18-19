package hr.fer.zemris.java.hw17.jvdraw.listeners;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;

import java.awt.*;

/**
 * A listener for changes of a specified component's color.
 *
 * @author Bruna Dujmović
 *
 */
public interface ColorChangeListener {

    /**
     * A method to be called after the {@code source} components's color has been changed.
     *
     * @param source the component that has access to the color
     * @param oldColor the old value of the color
     * @param newColor the new value of the color
     */
    void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
