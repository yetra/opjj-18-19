package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

import java.awt.*;

/**
 * A class for painting geometrical objects on a {@link Graphics2D} instance.
 *
 * @author Bruna Dujmović
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

    /**
     * The graphics object used for painting.
     */
    private Graphics2D g2d;

    /**
     * Constructs a {@link GeometricalObjectPainter} for the given graphics object.
     *
     * @param g2d the graphics object used for painting
     */
    public GeometricalObjectPainter(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void visit(Line line) {
        Point start = line.getStart();
        Point end = line.getEnd();

        g2d.setColor(line.getLineColor());
        g2d.drawLine(start.x, start.y, end.x, end.y);
    }

    @Override
    public void visit(Circle circle) {
        Point upperLeft = new Point(
                circle.getCenter().x - circle.getRadius(),
                circle.getCenter().y - circle.getRadius()
        );
        int diameter = 2 * circle.getRadius();

        g2d.setColor(circle.getLineColor());
        g2d.drawOval(upperLeft.x, upperLeft.y, diameter, diameter);
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        visit((Circle) filledCircle);

        Point upperLeft = new Point(
                filledCircle.getCenter().x - filledCircle.getRadius(),
                filledCircle.getCenter().y - filledCircle.getRadius()
        );
        int diameter = 2 * filledCircle.getRadius();

        g2d.setColor(filledCircle.getFillColor());
        g2d.fillOval(upperLeft.x, upperLeft.y, diameter, diameter);
    }
}
