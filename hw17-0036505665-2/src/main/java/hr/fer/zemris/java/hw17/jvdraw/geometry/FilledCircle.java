package hr.fer.zemris.java.hw17.jvdraw.geometry;

import hr.fer.zemris.java.hw17.jvdraw.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import java.awt.*;

/**
 * Models a filled circle that can be drawn on the canvas.
 *
 * @author Bruna Dujmović
 *
 */
public class FilledCircle extends Circle {

    /**
     * The fill color of the circle
     */
    private Color fillColor;

    /**
     * Constructs a {@link FilledCircle} of the given parameters.
     *
     * @param center the center point of the circle
     * @param radius the radius of the circle
     * @param lineColor the color of the circle's outline
     * @param fillColor the fill color of the circle
     */
    public FilledCircle(Point center, int radius,
                        Color lineColor, Color fillColor) {
        super(center, radius, lineColor);
        this.fillColor = fillColor;
    }

    /**
     * Constructs a {@link FilledCircle} of the given parameters.
     *
     * @param center the center point of the circle
     * @param radiusPoint a point of the circle's radius
     * @param lineColor the color of the circle's outline
     * @param fillColor the fill color of the circle
     */
    public FilledCircle(Point center, Point radiusPoint,
                        Color lineColor, Color fillColor) {
        super(center, radiusPoint, lineColor);
        this.fillColor = fillColor;
    }

    /**
     * Returns the fill color of the circle.
     *
     * @return the fill color of the circle
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Sets the fill color of the circle to the given value.
     *
     * @param fillColor the fill color of the circle to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * Constructs a {@link FilledCircle} from a given string.
     *
     * @param s the string containing circle data
     * @return a {@link FilledCircle} parsed from a given string
     */
    public static FilledCircle fromString(String s) {
        String[] parts = s.split(" ");
        Circle circle = Circle.fromString(s);

        Color fillColor = new Color(
                Integer.parseInt(parts[6]),
                Integer.parseInt(parts[7]),
                Integer.parseInt(parts[8])
        );

        return new FilledCircle(circle.getCenter(),
                circle.getRadius(), circle.getLineColor(), fillColor);
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new FilledCircleEditor(this);
    }

    @Override
    public String toString() {
        String colorHex = String.format("#%02X%02X%02X",
                fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());

        return "Filled " + super.toString().toLowerCase() + ", " + colorHex;
    }
}
