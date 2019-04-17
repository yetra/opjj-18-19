package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utility;

import java.util.List;

/**
 * This class represents the help command that prints the name and description of a
 * specified command, or a list of all supported commands if no command is specified.
 * It accepts zero or one arguments - the name of the command.
 *
 * @author Bruna Dujmović
 *
 */
public class HelpCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);

        if (parsed.length == 0) {
            env.commands().forEach(
                    (name, command) -> env.writeln(command.getCommandAsString())
            );

        } else if (parsed.length == 1) {
            ShellCommand command = env.commands().get(parsed[0]);

            if (command != null) {
                env.writeln(command.getCommandAsString());
            } else {
                env.writeln("Unknown command name \"" + parsed[0] + "\".");
            }

        } else {
            env.writeln("Help accepts zero or one argument, " + parsed.length
                    + " were given.");
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public List<String> getCommandDescription() {
        return List.of(
                "help [command_name]",
                "command_name (optional) -- name of the command whose info should be" +
                        "printed",
                "Prints the name and description of the specified command.",
                "If a command name is not specified, prints a list of all supported " +
                        "commands"
        );
    }
}
