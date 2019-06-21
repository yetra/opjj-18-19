package hr.fer.zemris.java.tecaj_13.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet that sends a redirect response to the client using the servleti/main
 * redirect location.
 *
 * @author Bruna Dujmović
 */
@WebServlet("/index.jsp")
public class RedirectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.sendRedirect("servleti/main");
    }
}
