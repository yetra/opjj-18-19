package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
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
    private static final int CHART_SPACING = 20;
    /**
     * The line between the last tick mark and the end of the arrow.
     */
    private static final int AXIS_EXTENSION = 5;
    /**
     * The length of a tick mark on the axis.
     */
    private static final int TICK_LENGTH = 8;

    /**
     * The color of the axes.
     */
    private static final Color AXIS_COLOR = new Color(0xB4B2B2);
    /**
     * The thickness of the axis lines.
     */
    private static final BasicStroke AXIS_STROKE = new BasicStroke(2);

    /**
     * The color of the grid.
     */
    private static final Color GRID_COLOR = new Color(0xF2E6D2);
    /**
     * The thickness of the grid lines.
     */
    private static final BasicStroke GRID_STROKE = new BasicStroke(2);

    /**
     * The fill color of the chart bars.
     */
    private static final Color BAR_FILL = new Color(0xE78154);
    /**
     * The outline color of the chart bars.
     */
    private static final Color BAR_OUTLINE = Color.WHITE;
    /**
     * The thickness of the bar outline.
     */
    private static final BasicStroke BAR_OUTLINE_STROKE = new BasicStroke(1);

    /**
     * The chart to paint.
     */
    private BarChart chart;

    /**
     * A container for x-axis data.
     */
    private Axis xAxis;
    /**
     * A container for y-axis data.
     */
    private Axis yAxis;

    /**
     * The dimensions of a grid cell.
     */
    private Dimension cell;

    /**
     * Constructs a {@link BarChartComponent} for the given chart.
     *
     * @param chart the chart to paint
     */
    public BarChartComponent(BarChart chart) {
        this.chart = chart;

        setOpaque(true);
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        List<XYValue> values = chart.getValues();

        Dimension component = getSize();
        Insets insets = getInsets();
        FontMetrics fm = g2d.getFontMetrics();

        int maxYNumberWidth = fm.stringWidth(Integer.toString(chart.getMaxY()));

        xAxis = new Axis(
                insets.left + fm.getHeight() + CHART_SPACING + maxYNumberWidth + CHART_SPACING,
                component.width - insets.right - AXIS_EXTENSION
        );

        yAxis = new Axis(
                component.height - (insets.bottom + fm.getHeight() + CHART_SPACING + fm.getHeight() + CHART_SPACING),
                insets.top + AXIS_EXTENSION
        );

        cell = new Dimension(
                (xAxis.end - xAxis.start) / values.size(),
                (yAxis.start - yAxis.end) * chart.getySpacing() / (chart.getMaxY() - chart.getMinY())
        );

        paintGrid(g2d);
        paintBars(g2d);
        paintXAxis(g2d, fm);
        paintYAxis(g2d, fm, maxYNumberWidth);

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.GRAY);
    }

    /**
     * Paints the grid lines of the bar chart.
     *
     * @param g2d the {@link Graphics2D} object to use
     */
    private void paintGrid(Graphics2D g2d) {
        g2d.setStroke(GRID_STROKE);
        g2d.setColor(GRID_COLOR);

        // vertical grid lines
        int xCount = chart.getValues().size();
        for (int x = 1; x <= xCount; x++) {
            g2d.drawLine(
                    xAxis.start + x * cell.width, yAxis.start,
                    xAxis.start + x * cell.width, yAxis.end
            );
        }

        // horizontal grid lines
        int yCount = (chart.getMaxY() - chart.getMinY()) / chart.getySpacing();
        for (int y = 1; y <= yCount; y++) {
            g2d.drawLine(
                    xAxis.start, yAxis.start - y * cell.height,
                    xAxis.end, yAxis.start - y * cell.height
            );
        }
    }

    /**
     * Paints the bars of the bar chart.
     *
     * @param g2d the {@link Graphics2D} object to use
     */
    private void paintBars(Graphics2D g2d) {
        List<XYValue> values = chart.getValues();
        int ySpacing = chart.getySpacing();

        g2d.setStroke(BAR_OUTLINE_STROKE);

        for (int x = 0, maxX = values.size(); x < maxX; x++) {
            int yValue = values.get(x).getY();

            Rectangle r = new Rectangle(
                    xAxis.start + x * cell.width,
                    yAxis.start - cell.height  * (yValue / ySpacing),
                    cell.width,
                    cell.height * (yValue / ySpacing)
            );

            g2d.setColor(BAR_FILL);
            g2d.fillRect(r.x, r.y, r.width, r.height);

            g2d.setColor(BAR_OUTLINE);
            g2d.drawRect(r.x, r.y, r.width, r.height);
        }
    }

    /**
     * Paints the x axis of the bar chart.
     *
     * @param g2d the {@link Graphics2D} object to use
     * @param fm the corresponding {@link FontMetrics} object
     */
    private void paintXAxis(Graphics2D g2d, FontMetrics fm) {
        List<XYValue> values = chart.getValues();
        int xCount = values.size();
        
        g2d.setStroke(AXIS_STROKE);
        g2d.setColor(AXIS_COLOR);
        
        // axis line
        g2d.drawLine(xAxis.start, yAxis.start, xAxis.end, yAxis.start);
        
        // caption
        g2d.drawString(
                chart.getxCaption(),
                xAxis.start + (xAxis.end - AXIS_EXTENSION - xAxis.start) / 2 - fm.stringWidth(chart.getxCaption()) / 2,
                yAxis.start + CHART_SPACING + fm.getHeight() + CHART_SPACING
        );
        
        // initial tick mark
        g2d.drawLine(xAxis.start, yAxis.start, xAxis.start, yAxis.start + TICK_LENGTH);
        
        for (int x = 1; x <= xCount; x++) {
            // value
            String value = Integer.toString(values.get(x - 1).getX());
            g2d.drawString(
                    value,
                    xAxis.start + x * cell.width - cell.width / 2 - fm.stringWidth(value) / 2,
                    yAxis.start + CHART_SPACING
            );

            // tick mark
            g2d.drawLine(
                    xAxis.start + x * cell.width, yAxis.start,
                    xAxis.start + x * cell.width, yAxis.start + TICK_LENGTH
            );
        }
    }

    /**
     * Paints the y axis of the bar chart.
     *
     * @param g2d the {@link Graphics2D} object to use
     * @param fm the corresponding {@link FontMetrics} object
     */
    private void paintYAxis(Graphics2D g2d, FontMetrics fm, int maxYNumberWidth) {
        int yCount = (chart.getMaxY() - chart.getMinY()) / chart.getySpacing();

        g2d.setStroke(AXIS_STROKE);
        g2d.setColor(AXIS_COLOR);
        
        // axis line
        g2d.drawLine(xAxis.start, yAxis.start, xAxis.start, yAxis.end);

        // caption
        AffineTransform saveAT = g2d.getTransform();
        AffineTransform rotateAT = AffineTransform.getQuadrantRotateInstance(3);
        g2d.setTransform(rotateAT);
        g2d.drawString(
                chart.getyCaption(),
                - (yAxis.start / 2 + fm.stringWidth(chart.getyCaption()) / 2 + AXIS_EXTENSION),
                xAxis.start - CHART_SPACING - maxYNumberWidth - CHART_SPACING

        );
        g2d.setTransform(saveAT);
        

        for (int row = 0; row <= yCount; row++) {
            // value
            String value = Integer.toString(chart.getMinY() + row * chart.getySpacing());
            g2d.drawString(
                    value,
                    xAxis.start - TICK_LENGTH - CHART_SPACING - fm.stringWidth(value),
                    yAxis.start - row * cell.height + fm.getAscent() / 2 - (int) AXIS_STROKE.getLineWidth() / 2
            );

            // tick mark
            g2d.drawLine(
                    xAxis.start - TICK_LENGTH, yAxis.start - row * cell.height,
                    xAxis.start, yAxis.start - row * cell.height
            );
        }
    }

    /**
     * A helper class that serves as a container for the axes' start and end coordinates.
     */
    private class Axis {

        /**
         * The start coordinate of the axis.
         */
        private int start;

        /**
         * The end coordinate of the axis.
         */
        private int end;

        /**
         * Constructs an {@link Axis} of the given start and end coordinates.
         *
         * @param start the start coordinate of the axis
         * @param end the end coordinate of the axis
         */
        public Axis(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
