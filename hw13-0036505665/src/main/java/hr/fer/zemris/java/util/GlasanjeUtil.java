package hr.fer.zemris.java.util;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A class containing utility methods containing functionality for favorite band voting.
 *
 * @author Bruna Dujmović
 *
 */
public class GlasanjeUtil {

    /**
     * Reads the band voting config files and returns a list of band data parsed from
     * it.
     *
     * @param req the request that wants the band data
     * @return a list of band data parsed from the band voting config files
     * @throws IOException if an issue occurred with reading the file
     */
    public static List<BandData> getVotingResults(HttpServletRequest req) throws IOException {
        Path resPath = Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt"));
        Path defPath = Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"));

        if (!Files.exists(resPath)) {
            Files.createFile(resPath);
        }

        Map<Integer, BandData> results = new HashMap<>();

        List<String> lines = Files.readAllLines(defPath);
        lines.forEach(line -> {
            String[] parts = line.split("\t");
            int ID = Integer.parseInt(parts[0]);

            results.put(ID, new BandData(ID, parts[1], parts[2], 0));
        });

        lines = Files.readAllLines(resPath);
        lines.forEach(line -> {
            String[] parts = line.split("\t");
            int ID = Integer.parseInt(parts[0]);
            int score = Integer.parseInt(parts[1]);

            results.get(ID).setScore(score);
        });

        List<BandData> values = new ArrayList<>(results.values());
        values.sort(Comparator.reverseOrder());

        return values;
    }

    /**
     * Returns a list of band data for the winners found in a given results list.
     *
     * @param results the list of all band data
     * @return a list of band data for the winners found in the given results list
     */
    public static List<BandData> getWinners(List<BandData> results) {
        int bestScore = results.get(0).getScore();

        return results.stream()
                .filter(r -> r.getScore() == bestScore)
                .collect(Collectors.toList());
    }


}
