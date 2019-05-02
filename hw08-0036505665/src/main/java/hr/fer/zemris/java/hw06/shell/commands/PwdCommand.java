package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.Collections;
import java.util.List;

/**
 * This class represents the pwd command which prints the absolute path of the current
 * directory. It accepts no arguments.
 *
 * @author Bruna Dujmović
 *
 */
public class PwdCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "pwd";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "pwd\n",
            "Prints the absolute path to the current directory."
    );

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.isEmpty()) {
            env.writeln("Pwd accepts no arguments!");
            return ShellStatus.CONTINUE;
        }
        env.writeln(env.getCurrentDirectory().toString());

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
