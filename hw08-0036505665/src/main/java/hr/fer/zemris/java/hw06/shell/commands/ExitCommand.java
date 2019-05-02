package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.Collections;
import java.util.List;

/**
 * This class represents the exit command which terminates the program. It accepts no
 * arguments.
 *
 * @author Bruna Dujmović
 *
 */
public class ExitCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "exit";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "exit\n",
            "Terminates the program."
    );

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.isEmpty()) {
            env.writeln("Exit accepts no arguments!");
            return ShellStatus.CONTINUE;
        }

        return ShellStatus.TERMINATE;
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
