package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.util.BandData;
import hr.fer.zemris.java.util.GlasanjeUtil;
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
import java.util.List;

/**
 * A servlet that generates a PNG image of a pie chart showing favorite band voting
 * results.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeChartServlet extends HttpServlet {

    /**
     * The width of the pie chart.
     */
    private static final int CHART_WIDTH = 400;
    /**
     * The height of the pie chart.
     */
    private static final int CHART_HEIGHT = 400;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");

        OutputStream outputStream = resp.getOutputStream();

        List<BandData> results = GlasanjeUtil.getVotingResults(req);
        JFreeChart usageChart = getGlasanjeChart(results);

        ChartUtils.writeChartAsPNG(
                outputStream, usageChart, CHART_WIDTH, CHART_HEIGHT
        );
    }

    /**
     * Returns a {@link JFreeChart} showing favorite band voting results.
     *
     * @return a {@link JFreeChart} showing favorite band voting results
     */
    private static JFreeChart getGlasanjeChart(List<BandData> results) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        results.forEach(
                result -> dataset.setValue(result.getName(), result.getScore())
        );

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Voting results", dataset, true, true, false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        return chart;
    }
}
