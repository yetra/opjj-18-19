package hr.fer.zemris.java.p12.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * A helper class for reading poll options data from a config file.
 *
 * @author Bruna Dujmović
 */
public class PollOptions {

    /**
     * Returns a list of {@link PollOption}s parsed from a given file.
     *
     * @param path the path to the file to parse
     * @return a list of {@link PollOption}s parsed from the given file
     */
    public static List<PollOption> fromFile(Path path) {
        List<PollOption> options = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> {
                String[] parts = line.split("\t");

                options.add(new PollOption(parts[0], parts[1], Integer.parseInt(parts[2])));
            });

            options.sort(Comparator.reverseOrder());

        } catch (IOException e) {
            throw new RuntimeException("Can't read poll options file!");
        }

        return options;
    }
}
