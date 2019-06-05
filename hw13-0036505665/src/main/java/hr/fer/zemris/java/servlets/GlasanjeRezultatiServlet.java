package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.util.BandData;
import hr.fer.zemris.java.util.GlasanjeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BandData> results = GlasanjeUtil.getVotingResults(req);
        req.setAttribute("results", results);
        req.setAttribute("winners", GlasanjeUtil.getWinners(results));

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
