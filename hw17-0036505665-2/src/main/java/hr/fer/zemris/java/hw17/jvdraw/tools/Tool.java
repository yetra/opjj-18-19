package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * An interface that represents a tool for drawing graphical objects.
 * It is modelled as a state in the State Design Pattern.
 *
 * @author Bruna Dujmović
 *
 */
public interface Tool {

    /**
     * A method to be called on mouse press.
     *
     * @param e the mouse press event
     */
    void mousePressed(MouseEvent e);

    /**
     * A method to be called on mouse release.
     *
     * @param e the mouse release event
     */
    void mouseReleased(MouseEvent e);

    /**
     * A method to be called on mouse click.
     *
     * @param e the mouse clik event
     */
    void mouseClicked(MouseEvent e);

    /**
     * A method to be called on mouse move.
     *
     * @param e the mouse move event
     */
    void mouseMoved(MouseEvent e);

    /**
     * A method to be called on mouse drag.
     *
     * @param e the mouse drag event
     */
    void mouseDragged(MouseEvent e);

    /**
     * Paints on the given {@link Graphics2D} object.
     *
     * @param g2d the graphics object for painting
     */
    void paint(Graphics2D g2d);
}
