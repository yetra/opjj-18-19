package hr.fer.zemris.java.gui.charts;

/**
 * This class represents a chart value to be used in the {@link BarChart} class.
 *
 * @author Bruna Dujmović
 *
 */
public class XYValue {

    /**
     * The x component of the value.
     */
    private int x;
    /**
     * The y component of the value.
     */
    private int y;

    /**
     * Constructs a {@link XYValue} of the given x and y components.
     *
     * @param x the x component of the value
     * @param y the y component of the value
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x component of the value.
     *
     * @return the x component of the value
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y component of the value.
     *
     * @return the y component of the value
     */
    public int getY() {
        return y;
    }

    /**
     * Returns an {@link XYValue} object parsed from a given string.
     *
     * @param valueString the string to parse
     * @return an {@link XYValue} object parsed from the given string
     * @throws IllegalArgumentException if the given string isn't properly formatted
     * @throws NumberFormatException if the string's elements cannot be parsed into Integers
     */
    public static XYValue fromString(String valueString) {
        String[] parts = valueString.split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid string format!");
        }

        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);

        return new XYValue(x, y);
    }
}
