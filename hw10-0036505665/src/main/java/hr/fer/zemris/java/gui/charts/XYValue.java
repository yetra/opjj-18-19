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
}
