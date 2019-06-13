package hr.fer.zemris.java.p12.servlets;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * A servlet that displays a list of all the polls that a user can vote on.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/servleti/index.html")
public class PollListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Poll> polls = DAOProvider.getDao().getPolls();
        req.setAttribute("polls", polls);

        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
    }
}
