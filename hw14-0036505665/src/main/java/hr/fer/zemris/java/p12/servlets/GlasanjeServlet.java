package hr.fer.zemris.java.p12.servlets;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * A servlet that displays all the poll options a user can vote for by retrieving
 * them from the votesDB database.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        long pollID = Long.parseLong(req.getParameter("pollID"));

        Poll poll = DAOProvider.getDao().getPollDataFor(pollID);
        List<PollOption> options = DAOProvider.getDao().getPollOptionsFor(pollID);

        req.setAttribute("poll", poll);
        req.setAttribute("options", options);

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
