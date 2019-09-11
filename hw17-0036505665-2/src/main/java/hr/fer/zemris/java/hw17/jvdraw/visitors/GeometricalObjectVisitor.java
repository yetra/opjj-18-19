package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

/**
 * A visitor of {@link GeometricalObject} instances.
 *
 * @author Bruna Dujmović
 *
 */
public interface GeometricalObjectVisitor {

    /**
     * Visits the given {@link Line} object.
     *
     * @param line the line to visit
     */
    void visit(Line line);

    /**
     * Visits the given {@link Circle} object.
     *
     * @param circle the circle to visit
     */
    void visit(Circle circle);

    /**
     * Visits the given {@link FilledCircle} object.
     *
     * @param filledCircle the filled circle to visit
     */
    void visit(FilledCircle filledCircle);
}
