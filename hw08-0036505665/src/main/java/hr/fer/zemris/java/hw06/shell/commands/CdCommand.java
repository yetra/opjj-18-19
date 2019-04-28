package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.utility.Utility;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the cd command which changes an {@link Environment} object's
 * current directory to a given directory.
 *
 * @author Bruna Dujmović
 *
 */
public class CdCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "cd";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "cd dir_path\n",
            "\tdir_path -- path to the new current directory\n",
            "Changes the environment's current directory to the directory specified by dir_path."
    );

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);

        if (parsed.length != 1) {
            env.writeln("Cd accepts one argument, " + parsed.length + " were given.");
            return ShellStatus.CONTINUE;
        }

        try {
            Path dirPath = env.getCurrentDirectory().resolve(Paths.get(parsed[0]));
            env.setCurrentDirectory(dirPath);

        } catch (IllegalArgumentException e) {
            env.writeln(e.getMessage());
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
