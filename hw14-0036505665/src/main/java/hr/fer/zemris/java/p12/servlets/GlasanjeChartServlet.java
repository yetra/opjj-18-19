package hr.fer.zemris.java.p12.servlets;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;
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
import java.util.List;

/**
 * A servlet that generates a PNG image of a pie chart showing the poll voting results.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/servleti/glasanje-grafika")
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("image/png");

        long pollID = Long.parseLong(req.getParameter("pollID"));

        List<PollOption> options = DAOProvider.getDao().getPollOptionsFor(pollID);
        JFreeChart usageChart = getGlasanjeChart(options);

        ChartUtils.writeChartAsPNG(
                resp.getOutputStream(), usageChart, CHART_WIDTH, CHART_HEIGHT
        );
    }

    /**
     * Returns a {@link JFreeChart} showing the poll voting results.
     *
     * @param options a list of poll options
     * @return a {@link JFreeChart} showing the poll voting results
     */
    private static JFreeChart getGlasanjeChart(List<PollOption> options) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        options.forEach(
                result -> dataset.setValue(
                        result.getOptionTitle(), result.getVotesCount()
                )
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
