package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class is an extension of {@link JComponent} that paints a given {@link BarChart}.
 *
 * @author Bruna Dujmović
 *
 */
public class BarChartComponent extends JComponent {

    /**
     * The space between the axis and the caption.
     */
    private static final int AXIS_SPACING = 20;

    /**
     * The line between the last tick mark and the end of the arrow.
     */
    private static final int AXIS_EXTENSION = 5;

    /**
     * The length of a tick mark on the axis.
     */
    private static final int TICK_LENGTH = 8;

    /**
     * The chart to paint.
     */
    private BarChart chart;

    /**
     * Constructs a {@link BarChartComponent} for the given chart.
     *
     * @param chart the chart to paint
     */
    public BarChartComponent(BarChart chart) {
        this.chart = chart;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        List<XYValue> values = chart.getValues();

        Dimension size = getSize();
        Insets insets = getInsets();
        FontMetrics fm = g2d.getFontMetrics();

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.GRAY);

        int maxYNumberWidth = fm.stringWidth(Integer.toString(chart.getMaxY()));

        int xAxisStart = insets.left + fm.getHeight() + AXIS_SPACING + maxYNumberWidth + AXIS_SPACING;
        int xAxisEnd = size.width - insets.right;

        int yAxisStart = size.height - (insets.bottom + fm.getHeight() + AXIS_SPACING + fm.getHeight() + AXIS_SPACING);
        int yAxisEnd = insets.top;

        int rowHeight = (yAxisStart - yAxisEnd) * chart.getySpacing() / (chart.getMaxY() - chart.getMinY());
        int columnWidth = (xAxisEnd - xAxisStart) / values.size();

        // X-AXIS
        // axis line
        g2d.drawLine(xAxisStart, yAxisStart, xAxisEnd, yAxisStart);
        // initial tick
        g2d.drawLine(xAxisStart, yAxisStart, xAxisStart, yAxisStart + TICK_LENGTH);
        // x-axis caption
        g2d.drawString(
                chart.getxCaption(),
                xAxisStart + (xAxisEnd - AXIS_EXTENSION - xAxisStart) / 2 - fm.stringWidth(chart.getxCaption()) / 2,
                yAxisStart + AXIS_SPACING + fm.getHeight() + AXIS_SPACING
        );
        for (int col = 1, colCount = values.size(); col <= colCount; col++) {
            // x-axis number
            String xNumber = Integer.toString(values.get(col - 1).getX());
            g2d.drawString(
                    xNumber,
                    xAxisStart + col * columnWidth - columnWidth / 2 - fm.stringWidth(xNumber) / 2,
                    yAxisStart + AXIS_SPACING
            );

            // tick mark
            g2d.drawLine(
                    xAxisStart + col * columnWidth, yAxisStart,
                    xAxisStart + col * columnWidth, yAxisStart + TICK_LENGTH
            );

            // vertical grid line
            g2d.drawLine(
                    xAxisStart + col * columnWidth, yAxisStart,
                    xAxisStart + col * columnWidth, yAxisEnd + AXIS_EXTENSION
            );
        }
        g2d.drawLine(xAxisEnd - AXIS_EXTENSION, yAxisStart, xAxisEnd, yAxisStart);

        // y-axis
        int rowCount = (chart.getMaxY() - chart.getMinY()) / chart.getySpacing();
        g2d.drawLine(xAxisStart, yAxisStart, xAxisStart, yAxisEnd);
        g2d.drawLine(xAxisStart - TICK_LENGTH, yAxisStart, xAxisStart, yAxisStart);
        for (int row = 1; row <= rowCount; row++) {
            // y-axis number
            String yNumber = Integer.toString(chart.getMinY() + (row - 1) * chart.getySpacing());
            g2d.drawString(
                    yNumber,
                    xAxisStart - TICK_LENGTH - AXIS_SPACING - fm.stringWidth(yNumber),
                    yAxisStart - (row - 1) * rowHeight + fm.getAscent() / 2 - 1 // brush stroke
            );

            // tick mark
            g2d.drawLine(
                    xAxisStart - TICK_LENGTH, yAxisStart - row * rowHeight,
                    xAxisStart, yAxisStart - row * rowHeight
            );

            // horizontal grid line
            g2d.drawLine(
                    xAxisStart, yAxisStart - row * rowHeight,
                    xAxisEnd - AXIS_EXTENSION, yAxisStart - row * rowHeight
            );
        }
        g2d.drawLine(xAxisEnd - AXIS_EXTENSION, yAxisStart, xAxisEnd, yAxisStart);

        // bars
        int ySpacing = chart.getySpacing();
        for (int x = 0, maxX = values.size(); x < maxX; x++) {
            int xValue = values.get(x).getX();
            int yValue = values.get(x).getY();

            g2d.setColor(Color.ORANGE);
            g2d.fillRect(
                    xAxisStart + x * columnWidth , yAxisStart - rowHeight  * (yValue / ySpacing),
                    columnWidth, rowHeight * (yValue / ySpacing)
            );
            g2d.setColor(Color.WHITE);
            g2d.drawRect(
                    xAxisStart + x * columnWidth , yAxisStart - rowHeight  * (yValue / ySpacing),
                    columnWidth, rowHeight * (yValue / ySpacing)
            );
        }
    }
}
