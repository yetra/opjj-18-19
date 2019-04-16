package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class represents the cat command that opens a given file and prints its
 * contents to the console.
 *
 * It accepts one or two arguments. The first argument is the path to the file to
 * print. The second argument (optional) is the name of the charset that should be
 * used to interpret the given file.
 *
 * @author Bruna Dujmović
 *
 */
public class CatCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);

        if (parsed.length < 1 || parsed.length > 2) {
            env.writeln("Cat accepts one or two arguments, " + parsed.length
                    + " were given.");
            return ShellStatus.CONTINUE;
        }

        try {
            Path filePath = Paths.get(parsed[0]);
            Charset charset = parsed.length == 1 ? Charset.defaultCharset()
                                                 : Charset.forName(parsed[1]);
            printFile(filePath, charset, env);

        } catch (InvalidPathException e) {
            env.writeln("Illegal path string \"" + parsed[0] + "\".");
        } catch (IllegalArgumentException e) {
            env.writeln("Illegal charset name \"" + parsed[1] + "\".");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * A helper method that opens and reads the given file using the specified charset
     * and then prints it to the {@link Environment}.
     *
     * @param filePath the path of the file to read and print
     * @param charset the charset to use to interpret the file
     * @param env the {@link Environment} to print the file to
     */
    private void printFile(Path filePath, Charset charset, Environment env) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                                Files.newInputStream(filePath)), charset))) {

            String line;
            while ((line = reader.readLine()) != null) {
                env.writeln(line);
            }

        } catch (IOException e) {
            env.writeln("Cannot print the given file.");
        }
    }

    @Override
    public String getCommandName() {
        return "cat";
    }

    @Override
    public List<String> getCommandDescription() {
        return List.of(
                "cat file_path [charset_name]",
                "file_path -- the path to the file that will be printed to the console",
                "charset_name -- the charset that will be used to interpret the file",
                "Opens the given file and writes its content to the console."
        );
    }
}
