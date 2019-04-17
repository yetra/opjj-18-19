package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.utility.Utility;

import java.util.List;

/**
 * This class represents the symbol command the current symbol that is set in the
 * {@link Environment} for a given symbol_type. If two arguments are given, it will
 * replace the current symbol_type symbol with the second argument.
 *
 * It accepts one or two arguments. The first argument is the the type of the symbol
 * i.e. PROMPT, MULTILINE, or MORELINES. The second argument (optional) is the symbol
 * that will replace the current one.
 *
 * @author Bruna Dujmović
 *
 */
public class SymbolCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);

        if (parsed.length < 1 || parsed.length > 2) {
            env.writeln("Symbol accepts one or two arguments, " + parsed.length
                    + " were given.");
            return ShellStatus.CONTINUE;
        }

        if (parsed.length == 1) {
            switch (parsed[0].toUpperCase()) {
                case "PROMPT":
                    env.writeln("Symbol for PROMPT is '"
                            + env.getPromptSymbol() + "'");
                    break;
                case "MORELINES":
                    env.writeln("Symbol for MORELINES is '"
                            + env.getMorelinesSymbol() + "'");
                    break;
                case "MULTILINE":
                    env.writeln("Symbol for MULTILINE is '"
                            + env.getMultilineSymbol() + "'");
                    break;
                default:
                    env.writeln("Unknown symbol type \"" + parsed[0] + "\"");
            }

        } else {
            if (parsed[1].length() != 1) {
                env.writeln("Invalid symbol \"" + parsed[1] + "\"");
                return ShellStatus.CONTINUE;
            }

            switch (parsed[0].toUpperCase()) {
                case "PROMPT":
                    env.writeln("Symbol for PROMPT changed from '"
                            + env.getPromptSymbol() + "' to '" + parsed[1] + "'");
                    env.setPromptSymbol(parsed[1].charAt(0));
                    break;
                case "MORELINES":
                    env.writeln("Symbol for MORELINES changed from '"
                            + env.getMorelinesSymbol() + "' to '" + parsed[1] + "'");
                    env.setMorelinesSymbol(parsed[1].charAt(0));
                    break;
                case "MULTILINE":
                    env.writeln("Symbol for MULTILINE changed from '"
                            + env.getMultilineSymbol() + "' to '" + parsed[1] + "'");
                    env.setMultilineSymbol(parsed[1].charAt(0));
                    break;
                default:
                    env.writeln("Unknown symbol type \"" + parsed[0] + "\"");
            }
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "symbol";
    }

    @Override
    public List<String> getCommandDescription() {
        return List.of(
                "symbol symbol_type [new_symbol]",
                "symbol_type -- the type of the symbol i.e. PROMPT, MULTILINE, or " +
                        "MORELINES",
                "new_symbol (optional) -- the symbol that will replace the current one",
                "Prints the current symbol for the given symbol_type.",
                "If new_symbol is given, the current symbol for symbol_type will be " +
                        "replaced by it."
        );
    }
}
