package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A servlet that registers a favorite band vote and updates the glasanje-rezultati.txt
 * file accordingly.
 *
 * @author Bruna Dujmović
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        Path filePath = Paths.get(fileName);

        String votedFor = req.getParameter("id");

        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        updateResults(filePath, votedFor);

        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }

    /**
     * Updates the voting results file entry for the specified band. If an entry for
     * the band didn't previously exist in the file, this method will add one to the
     * end of the file.
     *
     * @param filePath the path to the voting results file
     * @param votedFor the ID of the band that was voted for
     * @throws IOException if there was an issue with modifying the file
     */
    private void updateResults(Path filePath, String votedFor) throws IOException {
        List<String> lines = Files.readAllLines(filePath);

        boolean updated = false;
        for (int i = 0, size = lines.size(); i < size && !updated; i++) {
            String[] parts = lines.get(i).split("\t");

            int score = Integer.parseInt(parts[1]);

            if (votedFor.equals(parts[0])) {
                parts[1] = Integer.toString(++score);
                lines.set(i, parts[0] + "\t" + parts[1]);
                updated = true;
            }
        }

        if (!updated) {
            lines.add(votedFor + "\t" + "1");
        }

        Files.write(filePath, lines);
    }
}
