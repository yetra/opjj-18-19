package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class represents a bar chart to be drawn using {@link BarChartComponent}. Its
 * values, axis captions, and the scale of the y-axis are set through the constructor.
 *
 * @author Bruna Dujmović
 *
 */
public class BarChart {

    /**
     * The values to be displayed on this chart.
     */
    private List<XYValue> values;

    /**
     * The x-axis caption.
     */
    private String xCaption;
    /**
     * The y-axis caption.
     */
    private String yCaption;

    /**
     * The minimum y value to be displayed on the vertical axis.
     */
    private int minY;
    /**
     * The maximum y value to be displayed on the vertical axis.
     */
    private int maxY;
    /**
     * The spacing between two tick marks on the vertical axis.
     */
    private int ySpacing;

    /**
     * Constructs a {@link BarChart} of the given values, axis captions, and scale of
     * the y-axis.
     *
     * @param values the values to be displayed on the chart
     * @param xCaption the x-axis caption
     * @param yCaption the y-axis caption
     * @param minY the minimum y value to be displayed on the vertical axis
     * @param maxY the maximum y value to be displayed on the vertical axis
     * @param ySpacing the spacing between two tick marks on the vertical axis
     */
    public BarChart(List<XYValue> values, String xCaption, String yCaption,
                    int minY, int maxY, int ySpacing) {
        if (minY < 0) {
            throw new IllegalArgumentException("Minimum y value cannot be negative!");
        }
        if (maxY <= minY) {
            throw new IllegalArgumentException("Maximum y value must be larger than the minimum!");
        }

        // if the difference isn't evenly divisible by the spacing
        // change maxY to the first larger int value so that it is evenly divisible
        if ((maxY - minY) % ySpacing != 0) {
            maxY = ySpacing * ((maxY - minY) / ySpacing + 1);
        }

        for (XYValue value : values) {
            if (value.getY() < minY) {
                throw new IllegalArgumentException(
                        "List contains y values smaller than the minimum y.");
            }
        }

        this.values = values;
        this.xCaption = xCaption;
        this.yCaption = yCaption;
        this.minY = minY;
        this.maxY = maxY;
        this.ySpacing = ySpacing;
    }

    /**
     * Returns a list of values to be displayed on this chart.
     *
     * @return a list of values to be displayed on this chart
     */
    public List<XYValue> getValues() {
        return values;
    }

    /**
     * Returns the x-axis caption.
     *
     * @return the x-axis caption
     */
    public String getxCaption() {
        return xCaption;
    }

    /**
     * Returns the y-axis caption.
     *
     * @return the y-axis caption
     */
    public String getyCaption() {
        return yCaption;
    }

    /**
     * Returns the minimum y value to be displayed on the vertical axis.
     *
     * @return the minimum y value to be displayed on the vertical axis
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Returns the maximum y value to be displayed on the vertical axis.
     *
     * @return the maximum y value to be displayed on the vertical axis
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Returns the spacing between two tick marks on the vertical axis.
     *
     * @return the spacing between two tick marks on the vertical axis
     */
    public int getySpacing() {
        return ySpacing;
    }
}
