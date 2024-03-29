package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.math.Vector2D;

import java.awt.*;
import java.util.Objects;

/**
 * This class represents a given turtle's current state - its position, direction,
 * effective move length, and the color in which it draws.
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
     * The effective move length is equal to the distance that the turtle will cross
     * if it is given a unit move length.
     */
    private double effectiveMoveLength;

    /**
     * Constructs a {@link TurtleState} object given all its attributes.
     *
     * @param currentPostition the current position of the turtle
     * @param direction the current direction of the turtle
     * @param color the color in which the turtle draws
     * @param effectiveMoveLength the distance that the turtle will cross if it is
     *                             given a unit move length
     */
    public TurtleState(Vector2D currentPostition, Vector2D direction, Color color,
                       double effectiveMoveLength) {
        this.currentPostition = currentPostition;
        this.direction = direction;
        this.color = color;
        this.effectiveMoveLength = effectiveMoveLength;
    }

    /**
     * Returns the current position of the turtle.
     *
     * @return the current position of the turtle
     */
    public Vector2D getCurrentPostition() {
        return currentPostition;
    }

    /**
     * Sets the current position of the turtle to a given vector.
     *
     * @throws NullPointerException if the given vector is {@code null}
     */
    public void setCurrentPostition(Vector2D currentPostition) {
        Objects.requireNonNull(currentPostition);

        this.currentPostition = currentPostition;
    }

    /**
     * Returns the current direction of the turtle.
     *
     * @return the current direction of the turtle
     */
    public Vector2D getDirection() {
        return direction;
    }

    /**
     * Sets the current direction of the turtle to a given vector.
     *
     * @throws NullPointerException if the given vector is {@code null}
     */
    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

    /**
     * Returns the color in which the turtle draws.
     *
     * @return the color in which the turtle draws.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color in which the turtle draws to a new value.
     *
     * @throws NullPointerException if the given color is {@code null}
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the current effective move length of the turtle.
     *
     * @return the current effective move length of the turtle
     */
    public double getEffectiveMoveLength() {
        return effectiveMoveLength;
    }

    // TODO check IllegalArgExc
    /**
     * Sets the effective move length to a given value.
     *
     * @throws IllegalArgumentException if the given move length is less than 1
     */
    public void setEffectiveMoveLength(double effectiveMoveLength) {
        if (effectiveMoveLength < 1) {
            throw new IllegalArgumentException(
                    "Effective shift length cannot be less than 1.");
        }
        this.effectiveMoveLength = effectiveMoveLength;
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
                new Color(color.getRGB()), effectiveMoveLength);
    }

}
