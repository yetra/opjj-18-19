package hr.fer.zemris.java.hw06.shell.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class of utility methods.
 *
 * @author Bruna Dujmović
 * 
 */
public class Utility {

    /**
     * Returns an array of arguments parsed from a given arguments string.
     *
     * @param argumentsString the string of arguments to parse
     * @return an array of arguments parsed from a given arguments string
     */
    public static String[] parseArguments(String argumentsString) {
        Objects.requireNonNull(argumentsString);

        ArgumentsParser parser = new ArgumentsParser(argumentsString);
        List<String> arguments = new ArrayList<>();

        while (parser.hasNextArgument()) {
            arguments.add(parser.nextArgument());
        }

        return (String[]) arguments.toArray();
    }
}
