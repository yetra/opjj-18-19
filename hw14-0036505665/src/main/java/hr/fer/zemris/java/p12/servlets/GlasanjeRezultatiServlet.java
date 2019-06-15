package hr.fer.zemris.java.p12.servlets;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A servlet that retrieves and displays the poll voting resutls.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        long pollID = Long.parseLong(req.getParameter("pollID"));

        List<PollOption> options = DAOProvider.getDao().getPollOptionsFor(pollID);
        options.sort(Comparator.comparingInt(PollOption::getVotesCount).reversed());

        req.setAttribute("options", options);
        req.setAttribute("winners", getWinners(options));

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }

    /**
     * Returns a list of winners ({@link PollOption}s with the highest votes count)
     * found in the given {@link PollOption}s list.
     *
     * @param options a list of poll options
     * @return a list of poll winners
     */
    private static List<PollOption> getWinners(List<PollOption> options) {

        int bestScore = options.get(0).getVotesCount();

        return options.stream()
                .filter(r -> r.getVotesCount() == bestScore)
                .collect(Collectors.toList());
    }
}
