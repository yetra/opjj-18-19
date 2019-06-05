package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.util.BandData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A servlet that displays all the bands a user can vote for by reading them from
 * the glasanje-definicija.txt config file.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

        req.setAttribute("index", getIndex(Paths.get(fileName)));

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }

    /**
     * Returns a list of band information parsed from the voting config file.
     *
     * @param filePath the path to the config file
     * @return a list of band information parsed from the config file
     * @throws IOException if there was an issue with reading the file
     */
    private List<BandData> getIndex(Path filePath) throws IOException {
        List<BandData> index = new ArrayList<>();
        List<String> lines = Files.readAllLines(filePath);

        lines.forEach(line -> {
            String[] parts = line.split("\t");

            index.add(new BandData(Integer.parseInt(parts[0]), parts[1], parts[2], 0));
        });

        return index;
    }
}
