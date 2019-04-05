package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

import java.util.Objects;

/**
 * Instances of this class enable the drawing of fractals.
 *
 * @author Bruna Dujmović
 *
 */
public class Context {

    /**
     * A stack of turtle states. The currently active state is the one that's on the
     * top of this stack.
     */
    private ObjectStack<TurtleState> stateStack;

    /**
     * Returns the currently active turtle state (the one that's on the top of this
     * stack) without removing it from the stack.
     *
     * @return the currently active turtle state
     */
    public TurtleState getCurrentState() {
        return stateStack.peek();
    }

    /**
     * Pushes a given turtle state to the stack.
     *
     * @param state the turtle state to push
     * @throws NullPointerException if the given state is {@code null}
     */
    public void pushState(TurtleState state) {
        Objects.requireNonNull(state);

        stateStack.push(state);
    }

    /**
     * Removes a turtle state from the top of the stack without returning it.
     */
    public void popState() {
        stateStack.pop();
    }
}
