package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.utility.Utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the mkdir command creates the appropriate directory structure
 * at a specified location. It accepts one argument - the location (path) of the
 * directory structure that will be created.
 *
 * @author Bruna Dujmović
 *
 */
public class MkdirCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "mkdir";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "mkdir dir_path",
            "\tdir_path -- the location (path) of the directory structure that will be created\n",
            "Creates the appropriate directory structure at the specified location."
    );

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);

        if (parsed.length != 1) {
            env.writeln("Mkdir accepts one argument, " + parsed.length + " were given.");
            return ShellStatus.CONTINUE;
        }

        try {
            Path directoryPath = Paths.get(parsed[0]);

            Files.createDirectories(directoryPath);

        } catch (InvalidPathException e) {
            env.writeln("Illegal path string \"" + parsed[0] + "\".");
        } catch (IOException e) {
            env.writeln("I/O error occurred.");
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(DESCRIPTION);
    }
}
