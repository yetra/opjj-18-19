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
 * This class represents the pops a directory from the top of the shared command data
 * stack and sets it as the current directory.
 *
 * @author Bruna Dujmović
 *
 */
public class PopdCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "popd";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "popd\n",
            "Pops a directory from the top of the shared command data stack and " +
                    "sets it as the current directory."
    );

    /**
     * The map key for accessing the stack of directory paths.
     */
    private static final String STACK_KEY = "cdstack";

    @Override
    @SuppressWarnings("unchecked")
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.isEmpty()) {
            env.writeln("Popd accepts no arguments!");
            return ShellStatus.CONTINUE;
        }

        try {
            Stack<Path> sharedStack = (Stack<Path>) env.getSharedData(STACK_KEY);
            env.setCurrentDirectory(sharedStack.pop());

        } catch (EmptyStackException | IllegalArgumentException e) {
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
