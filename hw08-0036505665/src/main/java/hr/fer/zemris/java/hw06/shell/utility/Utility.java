package hr.fer.zemris.java.hw06.shell.utility;

import hr.fer.zemris.java.hw06.shell.Environment;

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
     * @throws IllegalArgumentException if the given arguments cannot be properly parsed
     */
    public static String[] parseArguments(String argumentsString) {
        Objects.requireNonNull(argumentsString);

        ArgumentsParser parser = new ArgumentsParser(argumentsString);
        List<String> arguments = new ArrayList<>();

        while (parser.hasNextArgument()) {
            arguments.add(parser.nextArgument());
        }

        return arguments.toArray(new String[0]);
    }

    /**
     * A helper method that reads a line using an {@link Environment} object. If the
     * line ends with a multiline symbol, the method will read the entire multi-line
     * input and concatenate it into a single string.
     *
     * @param env the {@link Environment} object that is used for reading
     * @return a string containing the line(s) read from the {@link Environment}
     */
    public static String readLineOrLines(Environment env) {
        String morelinesSymbol = env.getMorelinesSymbol().toString();
        String multilineSymbolSymbol = env.getMultilineSymbol().toString();

        String line = env.readLine().trim();
        if (!line.endsWith(morelinesSymbol)) {
            return line;
        }

        StringBuilder sb = new StringBuilder();
        while (line.endsWith(morelinesSymbol)) {
            sb.append(line, 0, line.length() - 1).append(" ");

            env.write(multilineSymbolSymbol + " ");
            line = env.readLine().trim();
        }

        return sb.append(line).toString();
    }
}
