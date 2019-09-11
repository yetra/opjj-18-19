package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

import java.awt.*;

/**
 * A {@link GeometricalObjectVisitor} for calculating the bounding box of the
 * complete canvas image.
 *
 * @author Bruna Dujmović
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

    /**
     * The top of the bounding box.
     */
    private int top;

    /**
     * The bottom of the bounding box.
     */
    private int bottom;

    /**
     * The leftmost value of the bounding box.
     */
    private int left;

    /**
     * The rightmost value of the bounding box.
     */
    private int right;

    @Override
    public void visit(Line line) {
        Point start = line.getStart();
        Point end = line.getEnd();

        updateBounds(new Rectangle(
                Math.min(start.x, end.x),
                Math.min(start.y, end.y),
                Math.abs(end.x - start.x),
                Math.abs(end.y - start.y)
        ));
    }

    @Override
    public void visit(Circle circle) {
        Point center = circle.getCenter();
        int radius = circle.getRadius();

        updateBounds(new Rectangle(
                center.x - radius,
                center.y - radius,
                2 * radius,
                2 * radius
        ));
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        visit((Circle) filledCircle);
    }

    /**
     * A helper method for updating the bounding box.
     *
     * @param r the values to update with
     */
    private void updateBounds(Rectangle r) {
        top = top > r.y ? r.y : top;
        bottom = bottom < r.y + r.height ? r.y + r.height : bottom;
        left = left > r.x ? r.x : left;
        right = right < r.x + r.width ? r.x + r.width : right;
    }

    /**
     * Returns the bounding box of the complete canvas image.
     *
     * @return the bounding box of the complete canvas image
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(left, top, right - left, bottom - top);
    }
}
