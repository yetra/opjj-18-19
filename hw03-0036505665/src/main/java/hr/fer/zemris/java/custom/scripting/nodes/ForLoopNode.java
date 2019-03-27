package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

import java.util.Objects;

/**
 * A node that represents a single for-loop construct.
 * It inherits from the {@code Node} class.
 *
 * @author Bruna Dujmović
 *
 */
public class ForLoopNode extends Node {

    /**
     * The control variable of the for-loop.
     */
    private ElementVariable variable;

    /**
     * The initial value of the loop control variable.
     */
    private Element startExpression;

    /**
     * The final value of the loop control variable.
     */
    private Element endExpression;

    /**
     * The step by which the loop control variable will be incremented.
     */
    private Element stepExpression;

    /**
     * Constructs a for-loop node based on a loop control variable, the initial value
     * of the control variable, the final value of the control variable, and the step
     * by which the variable will be incremented after every loop iteration.
     *
     * @param variable the control variable of the for-loop
     * @param startExpression the initial value of the control variable
     * @param endExpression the final value of the control variable
     * @param stepExpression the step by which the control variable will be incremented
     * @throws NullPointerException if {@code variable}, {@code startExpression} or
     *         {@code endExpression} are {@code null}
     */
    public ForLoopNode(ElementVariable variable, Element startExpression,
                       Element endExpression, Element stepExpression) {
        Objects.requireNonNull(variable);
        Objects.requireNonNull(startExpression);
        Objects.requireNonNull(endExpression);

        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * Returns the control variable of the for-loop.
     *
     * @return control variable of the for-loop
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Returns the initial value of the loop control variable.
     *
     * @return the initial value of the loop control variable.
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Returns the final value of the loop control variable.
     *
     * @return the final value of the loop control variable
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Returns the step by which the loop control variable will be incremented.
     *
     * @return the step by which the loop control variable will be incremented
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    @Override
    public String toString() {
        return "{$ FOR " + variable.asText() + " "
                + startExpression.asText() + " "
                + endExpression.asText() + " "
                + (stepExpression == null ? "" : stepExpression.asText() + " ")
                + "$}"
                + super.toString()
                + "{$ END $}";
    }
}
