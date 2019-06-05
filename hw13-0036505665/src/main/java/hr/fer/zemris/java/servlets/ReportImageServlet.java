package hr.fer.zemris.java.servlets;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A servlet that generates a PNG image of a pie chart showing OS usage data.
 */
@WebServlet("/reportImage")
public class ReportImageServlet extends HttpServlet {

    /**
     * The width of the pie chart.
     */
    private static final int CHART_WIDTH = 500;
    /**
     * The height of the pie chart.
     */
    private static final int CHART_HEIGHT = 350;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");

        OutputStream outputStream = resp.getOutputStream();

        JFreeChart usageChart = getUsageChart();

        ChartUtils.writeChartAsPNG(
                outputStream, usageChart, CHART_WIDTH, CHART_HEIGHT
        );
    }

    /**
     * Returns a {@link JFreeChart} showing OS usage data.
     *
     * @return a {@link JFreeChart} showing OS usage data
     */
    private JFreeChart getUsageChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue("Windows", 88);
        dataset.setValue("macOS", 10);
        dataset.setValue("Linux", 2);

        JFreeChart chart = ChartFactory.createPieChart3D(
                "OS usage", dataset, true, true, false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        return chart;
    }
}
