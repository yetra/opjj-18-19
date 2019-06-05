package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * A servlet that updates the pickedBgCol attribute based on query string parameters.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/setcolor")
public class BgColorServlet extends HttpServlet {

    /**
     * The default background color.
     */
    private static final String DEFAULT_COLOR = "white";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String color = req.getParameter("col");

        String pickedBgCol = Objects.requireNonNullElse(color, DEFAULT_COLOR);
        req.getSession().setAttribute("pickedBgCol", pickedBgCol);

        req.getRequestDispatcher("/colors.jsp").forward(req, resp);
    }
}
