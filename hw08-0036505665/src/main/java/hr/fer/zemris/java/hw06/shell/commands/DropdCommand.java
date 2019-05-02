package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.file.Path;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * This class represents the dropd command which pops a directory from the top of the
 * shared command data stack without changing the current directory. It accepts no
 * arguments.
 *
 * @author Bruna Dujmović
 *
 */
public class DropdCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "dropd";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "dropd\n",
            "Pops a directory from the top of the shared command data stack without " +
                    "changing the current directory."
    );

    /**
     * The map key for accessing the stack of directory paths.
     */
    private static final String STACK_KEY = "cdstack";

    @Override
    @SuppressWarnings("unchecked")
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.isEmpty()) {
            env.writeln("Dropd accepts no arguments!");
            return ShellStatus.CONTINUE;
        }

        try {
            ((Stack<Path>) env.getSharedData(STACK_KEY)).pop();

        } catch (EmptyStackException | NullPointerException e) {
            env.writeln("Can't perform pop on empty/non-existing stack!");
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
