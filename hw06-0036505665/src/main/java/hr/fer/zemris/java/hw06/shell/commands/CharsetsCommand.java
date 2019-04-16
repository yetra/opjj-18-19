package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * This class represents the charsets command that lists the names of the supported
 * charsets for the user's Java platform. It accepts no arguments. A single charset
 * name is written per line.
 *
 * @author Bruna Dujmović
 *
 */
public class CharsetsCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.isEmpty()) {
            env.writeln("Charsets accepts no arguments!");
            return ShellStatus.CONTINUE;
        }

        Map<String, Charset> charsets = Charset.availableCharsets();

        for (String charset : charsets.keySet()) {
            env.writeln(charset);
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "charsets";
    }

    @Override
    public List<String> getCommandDescription() {
        return List.of(
                "charsets",
                "Lists the names of the supported charsets for the user's Java platform."
        );
    }
}
