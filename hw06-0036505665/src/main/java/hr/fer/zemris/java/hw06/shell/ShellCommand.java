package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * This interface models a shell command that can be executed on a given argument
 * with the specified command arguments.
 *
 * @author Bruna Dujmović
 *
 */
public interface ShellCommand {

    /**
     * Performs this command on a given {@link Environment} with the specified string
     * of command arguments.
     *
     * @param env the {@link Environment} on which to perform the command
     * @param arguments the arguments of the command (everything that the user entered
     *                  after the command name)
     * @return the status that the shell should be in after performing this command
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * Returns the name of this command.
     *
     * @return the name of this command.
     */
    String getCommandName();

    /**
     * Returns a description (usage instructions) of this command in a read-only list.
     *
     * @return a description (usage instructions) of this command in a read-only list
     */
    List<String> getCommandDescription();

    /**
     * Returns the string representation of this command.
     *
     * @return the string representation of this command
     */
    default String getCommandAsString() {
        StringBuilder builder = new StringBuilder();

        builder.append("NAME\n\t").append(getCommandName());
        builder.append("\n\nDESCRIPTION");

        getCommandDescription().forEach(
                (line) -> builder.append("\n\t").append(line)
        );

        return builder.toString();
    }
}


