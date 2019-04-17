package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.List;

/**
 * This class represents the exit command which terminates the program. It accepts no
 * arguments.
 *
 * @author Bruna Dujmović
 *
 */
public class ExitCommand implements ShellCommand {

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
        return "exit";
    }

    @Override
    public List<String> getCommandDescription() {
        return List.of("exit",
                "Terminates the program."
        );
    }
}
