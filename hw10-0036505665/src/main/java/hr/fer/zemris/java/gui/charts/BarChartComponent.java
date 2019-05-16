package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
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
    private static final int SPACING = 20;
    /**
     * The line between the last tick mark and the end of the arrow.
     */
    private static final int ARROW_LENGTH = 8;
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
     * The color of the font used on the chart.
     */
    private static final Color FONT_COLOR = Color.BLACK;

    /**
     * The chart to paint.
     */
    private BarChart chart;

    /**
     * The start coordinate of the x-axis.
     */
    private int xAxisStart;
    /**
     * The end coordinate of the x-axis.
     */
    private int xAxisEnd;

    /**
     * The start coordinate of the y-axis.
     */
    private int yAxisStart;
    /**
     * The end coordinate of the y-axis.
     */
    private int yAxisEnd;

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

        xAxisStart = insets.left + fm.getHeight() + SPACING + maxYNumberWidth + SPACING;
        xAxisEnd = component.width - insets.right - ARROW_LENGTH;

        yAxisStart = component.height - insets.bottom - fm.getHeight() - SPACING - fm.getHeight() - SPACING;
        yAxisEnd = insets.top + ARROW_LENGTH;

        cell = new Dimension(
                (xAxisEnd - xAxisStart) / values.size(),
                (yAxisStart - yAxisEnd) * chart.getySpacing() / (chart.getMaxY() - chart.getMinY())
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
                    xAxisStart + x * cell.width, yAxisStart,
                    xAxisStart + x * cell.width, yAxisEnd
            );
        }

        // horizontal grid lines
        int yCount = (chart.getMaxY() - chart.getMinY()) / chart.getySpacing();
        for (int y = 1; y <= yCount; y++) {
            g2d.drawLine(
                    xAxisStart, yAxisStart - y * cell.height,
                    xAxisEnd, yAxisStart - y * cell.height
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
                    xAxisStart + x * cell.width,
                    yAxisStart - cell.height * ((yValue - chart.getMinY()) / ySpacing),
                    cell.width,
                    cell.height * ((yValue - chart.getMinY()) / ySpacing)
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

        // caption
        g2d.setColor(FONT_COLOR);
        g2d.drawString(
                chart.getxCaption(),
                xAxisStart + (xAxisEnd - ARROW_LENGTH - xAxisStart) / 2 - fm.stringWidth(chart.getxCaption()) / 2,
                yAxisStart + SPACING + fm.getHeight() + SPACING
        );
        g2d.setColor(AXIS_COLOR);
        
        // axis line
        g2d.drawLine(xAxisStart, yAxisStart, xAxisEnd + ARROW_LENGTH, yAxisStart);
        
        // initial tick mark
        g2d.drawLine(xAxisStart, yAxisStart, xAxisStart, yAxisStart + TICK_LENGTH);
        
        for (int x = 1; x <= xCount; x++) {
            // value
            g2d.setColor(FONT_COLOR);
            String value = Integer.toString(values.get(x - 1).getX());
            g2d.drawString(
                    value,
                    xAxisStart + x * cell.width - cell.width / 2 - fm.stringWidth(value) / 2,
                    yAxisStart + SPACING
            );
            g2d.setColor(AXIS_COLOR);

            // tick mark
            g2d.drawLine(
                    xAxisStart + x * cell.width, yAxisStart,
                    xAxisStart + x * cell.width, yAxisStart + TICK_LENGTH
            );
        }

        // arrow
        g2d.drawLine(xAxisEnd + ARROW_LENGTH / 2, yAxisStart - TICK_LENGTH / 2, xAxisEnd + ARROW_LENGTH, yAxisStart);
        g2d.drawLine(xAxisEnd + ARROW_LENGTH / 2, yAxisStart + TICK_LENGTH / 2, xAxisEnd + ARROW_LENGTH, yAxisStart);
    }

    /**
     * Paints the y axis of the bar chart.
     *
     * @param g2d the {@link Graphics2D} object to use
     * @param fm the corresponding {@link FontMetrics} object
     */
    private void paintYAxis(Graphics2D g2d, FontMetrics fm, int maxYNumberWidth) {
        int yCount = (chart.getMaxY() - chart.getMinY()) / chart.getySpacing();
        String yCaption = chart.getyCaption();

        g2d.setStroke(AXIS_STROKE);

        // caption
        g2d.setColor(FONT_COLOR);
        AffineTransform saveAT = g2d.getTransform();
        AffineTransform rotateAT = AffineTransform.getQuadrantRotateInstance(3);
        g2d.setTransform(rotateAT);
        g2d.drawString(
                yCaption,
                (yAxisEnd - yAxisStart) / 2 - yAxisEnd - fm.stringWidth(yCaption) / 2 - TICK_LENGTH - ARROW_LENGTH,
                xAxisStart - SPACING - maxYNumberWidth - SPACING
        );
        g2d.setTransform(saveAT);
        g2d.setColor(AXIS_COLOR);

        // axis line
        g2d.drawLine(xAxisStart, yAxisStart, xAxisStart, yAxisEnd - ARROW_LENGTH);
        

        for (int row = 0; row <= yCount; row++) {
            // value
            g2d.setColor(FONT_COLOR);
            String value = Integer.toString(chart.getMinY() + row * chart.getySpacing());
            g2d.drawString(
                    value,
                    xAxisStart - TICK_LENGTH - SPACING - fm.stringWidth(value),
                    yAxisStart - row * cell.height + fm.getAscent() / 2 - (int) AXIS_STROKE.getLineWidth() / 2
            );
            g2d.setColor(AXIS_COLOR);

            // tick mark
            g2d.drawLine(
                    xAxisStart - TICK_LENGTH, yAxisStart - row * cell.height,
                    xAxisStart, yAxisStart - row * cell.height
            );
        }

        // arrow
        g2d.drawLine(xAxisStart - TICK_LENGTH / 2, yAxisEnd - ARROW_LENGTH / 2, xAxisStart, yAxisEnd - ARROW_LENGTH);
        g2d.drawLine(xAxisStart + TICK_LENGTH / 2, yAxisEnd - ARROW_LENGTH / 2, xAxisStart, yAxisEnd - ARROW_LENGTH);
    }
}
