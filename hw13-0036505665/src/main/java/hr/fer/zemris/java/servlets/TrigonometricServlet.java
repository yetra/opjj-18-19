package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet for calculating the trigonometric function values for two angles given
 * through query string parameters.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet(name="trigonometric", urlPatterns = {"/trigonometric"})
public class TrigonometricServlet extends HttpServlet {

    /**
     * The default value of the "a" angle.
     */
    private static final int DEFAULT_A = 0;
    /**
     * The default value of the "b" angle.
     */
    private static final int DEFAULT_B = 360;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strA = req.getParameter("a");
        String strB = req.getParameter("b");

        int a = DEFAULT_A;
        int b = DEFAULT_B;

        try {
            a = Integer.parseInt(strA);
        } catch (NumberFormatException | NullPointerException ignorable) {}
        try {
            b = Integer.parseInt(strB);
        } catch (NumberFormatException | NullPointerException ignorable) {}

        if (a > b) {
            int varTemp = a;
            a = b;
            b = varTemp;
        }
        if (b > a + 720) {
            b = a + 720;
        }

        req.setAttribute("sinA", Math.sin(a));
        req.setAttribute("sinB", Math.sin(b));
        req.setAttribute("cosA", Math.cos(a));
        req.setAttribute("cosB", Math.cos(b));

        req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
    }
}
