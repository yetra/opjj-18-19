package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * This class represents the listd command which prints all the directory paths from
 * the shared command data stack. It accepts no arguments.
 *
 * @author Bruna Dujmović
 *
 */
public class ListdCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "listd";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "pushd\n",
            "Prints all the directory paths from the shared command data stack."
    );

    /**
     * The map key for accessing the stack of directory paths.
     */
    private static final String STACK_KEY = "cdstack";

    @Override
    @SuppressWarnings("unchecked")
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.isEmpty()) {
            env.writeln("Listd accepts no arguments!");
            return ShellStatus.CONTINUE;
        }

        Stack<Path> sharedStack = (Stack<Path>) env.getSharedData(STACK_KEY);
        if (sharedStack == null || sharedStack.isEmpty()) {
            env.writeln("Nema pohranjenih direktorija.");
        } else {
            sharedStack.forEach(System.out::println);
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
