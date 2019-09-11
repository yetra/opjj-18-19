package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

import java.awt.*;
import java.io.IOException;
import java.io.Writer;

/**
 * A visitor to be used for writing graphical objects to a file using a {@link Writer}.
 *
 * @author Bruna Dujmović
 *
 */
public class GeometricalObjectWriter implements GeometricalObjectVisitor {

    /**
     * The {@link Writer} for writing geometrical objects.
     */
    private Writer writer;

    /**
     * Constructs a {@link GeometricalObjectWriter} for the given {@link Writer}.
     *
     * @param writer the {@link Writer} for writing geometrical objects
     */
    public GeometricalObjectWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void visit(Line line) {
        Point start = line.getStart();
        Point end = line.getEnd();
        Color color = line.getLineColor();

        try {
            writer.write(String.format("LINE %d %d %d %d %d %d %d%n",
                    start.x, start.y, end.x, end.y,
                    color.getRed(), color.getGreen(), color.getBlue()
            ));

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void visit(Circle circle) {
        Point center = circle.getCenter();
        int radius = circle.getRadius();
        Color color = circle.getLineColor();

        try {
            writer.write(String.format("CIRCLE %d %d %d %d %d %d%n",
                    center.x, center.y, radius, color.getRed(),
                    color.getGreen(), color.getBlue()
            ));

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        Point center = filledCircle.getCenter();
        int radius = filledCircle.getRadius();
        Color color = filledCircle.getLineColor();
        Color fillColor = filledCircle.getFillColor();

        try {
            writer.write(String.format("FCIRCLE %d %d %d %d %d %d %d %d %d%n",
                    center.x, center.y, radius, color.getRed(),
                    color.getGreen(), color.getBlue(),
                    fillColor.getRed(), fillColor.getGreen(),
                    fillColor.getBlue()
            ));

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
