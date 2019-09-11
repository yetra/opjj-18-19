package hr.fer.zemris.java.hw17.jvdraw.geometry;

import hr.fer.zemris.java.hw17.jvdraw.editors.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import java.awt.*;

/**
 * Models a circle that can be drawn on the canvas.
 *
 * @author Bruna Dujmović
 *
 */
public class Circle extends GeometricalObject {

    /**
     * The center point of the circle.
     */
    private Point center;

    /**
     * A point on the circle's radius.
     */
    private Point radiusPoint;

    /**
     * The radius of the circle.
     */
    private int radius;

    /**
     * The color of the circle's outline.
     */
    private Color lineColor;

    /**
     * Constructs a {@link Circle} of the given parameters.
     *
     * @param center the center point of the circle
     * @param radius the radius of the circle
     * @param lineColor the color of the circle's outline
     */
    public Circle(Point center, int radius, Color lineColor) {
        this.center = center;
        this.radius = radius;
        this.lineColor = lineColor;
    }

    /**
     * Constructs a {@link Circle} of the given parameters.
     *
     * @param center the center point of the circle
     * @param radiusPoint a point of the circle's radius
     * @param lineColor the color of the circle's outline
     */
    public Circle(Point center, Point radiusPoint, Color lineColor) {
        this.center = center;
        this.radiusPoint = radiusPoint;
        this.lineColor = lineColor;

        setRadius();
    }

    /**
     * Returns the center point of the circle.
     *
     * @return the center point of the circle
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Sets the center point of the circle to the given value.
     *
     * @param center the center point of the circle to set
     */
    public void setCenter(Point center) {
        this.center = center;
        notifyListeners();
    }

    /**
     * Returns a point of the circle's radius.
     *
     * @return a point of the circle's radius
     */
    public Point getRadiusPoint() {
        return radiusPoint;
    }

    /**
     * Sets the point of the circle's radius to the given value.
     *
     * @param radiusPoint a point of the circle's radius to set
     */
    public void setRadiusPoint(Point radiusPoint) {
        this.radiusPoint = radiusPoint;
        setRadius();
        notifyListeners();
    }

    /**
     * Returns the radius of the circle.
     *
     * @return the radius of the circle
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the circle to the given value.
     *
     * @param radius the radius of the circle to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Returns the color of the circle's outline.
     *
     * @return the color of the circle's outline
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Sets the color of the circle's outline to the given value.
     *
     * @param lineColor the color of the circle's outline to set
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        notifyListeners();
    }

    /**
     * Constructs a {@link Circle} from a given string.
     *
     * @param s the string containing circle data
     * @return a {@link Circle} parsed from a given string
     */
    public static Circle fromString(String s) {
        String[] parts = s.split(" ");

        Point center = new Point(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        );

        int radius = Integer.parseInt(parts[2]);

        Color lineColor = new Color(
                Integer.parseInt(parts[3]),
                Integer.parseInt(parts[4]),
                Integer.parseInt(parts[5])
        );

        return new Circle(center, radius, lineColor);
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new CircleEditor(this);
    }

    @Override
    public String toString() {
        return "Circle (" + center.x + "," + center.y + "), " + radius;
    }

    /**
     * A helper method for calculating the circle's radius.
     */
    private void setRadius() {
        radius = (int) Math.sqrt(Math.pow(radiusPoint.x - center.x, 2)
                + Math.pow(radiusPoint.y - center.y, 2));
    }
}
