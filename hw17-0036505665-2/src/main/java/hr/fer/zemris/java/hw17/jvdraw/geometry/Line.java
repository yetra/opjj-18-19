package hr.fer.zemris.java.hw17.jvdraw.geometry;

import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import java.awt.*;

/**
 * Models a line that can be drawn on the canvas.
 *
 * @author Bruna Dujmović
 *
 */
public class Line extends GeometricalObject {

    /**
     * The start point of the line.
     */
    private Point start;

    /**
     * The end point of the line.
     */
    private Point end;

    /**
     * The color of the line.
     */
    private Color lineColor;

    /**
     * Constructs a {@link Line} of the given parameters.
     *
     * @param start the start point of the line
     * @param end the end point of the line
     * @param lineColor the color of the line
     */
    public Line(Point start, Point end, Color lineColor) {
        this.start = start;
        this.end = end;
        this.lineColor = lineColor;
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point of the line
     */
    public Point getStart() {
        return start;
    }

    /**
     * Sets the start point of the line to the given value.
     *
     * @param start the start point to set
     */
    public void setStart(Point start) {
        this.start = start;
        notifyListeners();
    }

    /**
     * Returns the end point of the line.
     *
     * @return the end point of the line
     */
    public Point getEnd() {
        return end;
    }

    /**
     * Sets the end point of the line to the given value.
     *
     * @param end the end point to set
     */
    public void setEnd(Point end) {
        this.end = end;
        notifyListeners();
    }

    /**
     * Returns the color of the line.
     *
     * @return the color of the line
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Sets the color of the line to the given value.
     *
     * @param lineColor the color of the line to set
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        notifyListeners();
    }

    /**
     * Constructs a {@link Line} from a given string.
     *
     * @param s the string containing line data
     * @return a {@link Line} parsed from a given string
     */
    public static Line fromString(String s) {
        String[] parts = s.split(" ");

        Point start = new Point(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        );

        Point end = new Point(
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3])
        );

        Color lineColor = new Color(
                Integer.parseInt(parts[4]),
                Integer.parseInt(parts[5]),
                Integer.parseInt(parts[6])
        );

        return new Line(start, end, lineColor);
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new LineEditor(this);
    }

    @Override
    public String toString() {
        return "Line (" + start.x + "," + start.y +
                ")-(" + end.x + "," + end.y + ")";
    }
}
