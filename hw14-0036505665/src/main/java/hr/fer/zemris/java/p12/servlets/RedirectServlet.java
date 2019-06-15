package hr.fer.zemris.java.p12.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet that sends a redirect response to the client using the servleti/index.html
 * redirect location.
 *
 * @author Bruna Dujmović
 */
@WebServlet("/index.html")
public class RedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.sendRedirect("servleti/index.html");
    }
}
