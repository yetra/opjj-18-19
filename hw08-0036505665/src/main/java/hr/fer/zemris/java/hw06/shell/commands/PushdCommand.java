package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.utility.Utility;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * This class represents the pushes the current directory to the shared command data
 * stack and sets the directory specified by dir_path as the new current directory.
 *
 * @author Bruna Dujmović
 *
 */
public class PushdCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "pushd";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "pushd dir_path",
            "\tdir_path -- path to the new current directory\n",
            "Pushes the current directory to the shared command data stack and sets " +
                    "the directory specified by dir_path as the new current directory."
    );

    /**
     * The map key for accessing the stack of directory paths.
     */
    private static final String STACK_KEY = "cdstack";

    @Override
    @SuppressWarnings("unchecked")
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);

        if (parsed.length != 1) {
            env.writeln("Pushd accepts one argument, " + parsed.length + " were given.");
            return ShellStatus.CONTINUE;
        }

        try {
            Path dirPath = env.getCurrentDirectory().resolve(Paths.get(parsed[0]));
            if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
                env.writeln("The given path does not point to an existing directory.");
                return ShellStatus.CONTINUE;
            }

            Stack<Path> sharedStack = (Stack<Path>) env.getSharedData(STACK_KEY);
            if (sharedStack == null) {
                sharedStack = new Stack<>();
                env.setSharedData(STACK_KEY, sharedStack);
            }

            sharedStack.push(env.getCurrentDirectory());
            env.setCurrentDirectory(dirPath);

        } catch (IllegalArgumentException e) {
            env.writeln(e.getMessage());
            return ShellStatus.CONTINUE;
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
