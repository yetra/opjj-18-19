package hr.fer.zemris.java.p12.servlets;

import hr.fer.zemris.java.p12.dao.DAOProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet that registers a poll option vote and updates the votingDB database
 * accordingly.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        long pollID = Long.parseLong(req.getParameter("pollID"));
        long id = Long.parseLong(req.getParameter("id"));

        DAOProvider.getDao().updateScoreFor(id);

        resp.sendRedirect(req.getContextPath()
                + "/servleti/glasanje-rezultati?pollID=" + pollID);
    }
}
