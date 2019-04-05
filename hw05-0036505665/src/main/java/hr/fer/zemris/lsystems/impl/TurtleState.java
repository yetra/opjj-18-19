package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.math.Vector2D;

import java.awt.*;

/**
 * This class represents a given turtle's current state - its position, direction,
 * shift length, and the color in which it draws.
 *
 * @author Bruna Dujmović
 *
 */
public class TurtleState {

    /**
     * The current position of the turtle.
     */
    private Vector2D currentPostition;

    /**
     * The current direction of the turtle;
     */
    private Vector2D direction;

    /**
     * The color in which the turtle draws.
     */
    private Color color;

    /**
     * The effective shift length is equal to the distance that the turtle will cross
     * if it is given a unit shift.
     */
    private double effectiveShiftLength;

    /**
     * Constructs a {@link TurtleState} object given all its attributes.
     *
     * @param currentPostition the current position of the turtle
     * @param direction the current direction of the turtle
     * @param color the color in which the turtle draws
     * @param effectiveShiftLength the distance that the turtle will cross if it is
     *                             given a unit shift
     */
    public TurtleState(Vector2D currentPostition, Vector2D direction, Color color,
                       double effectiveShiftLength) {
        this.currentPostition = currentPostition;
        this.direction = direction;
        this.color = color;
        this.effectiveShiftLength = effectiveShiftLength;
    }

    /**
     * Returns a new {@link TurtleState} state object that is a copy of this turtle
     * state.
     *
     * @return a new {@link TurtleState} state object that is a copy of this turtle
     *         state
     */
    public TurtleState copy() {
        return new TurtleState(currentPostition.copy(), direction.copy(),
                new Color(color.getRGB()), effectiveShiftLength);
    }

}
