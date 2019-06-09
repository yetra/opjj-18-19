package hr.fer.zemris.java.p12.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PollOptions {

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
