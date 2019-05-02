package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.utility.Utility;

import java.util.Collections;
import java.util.List;

/**
 * This class represents the symbol command that prints the current symbol that is
 * set in the {@link Environment} for a given symbol_type. If two arguments are given,
 * it will replace the current symbol_type symbol with the second argument.
 *
 * It accepts one or two arguments. The first argument is the the type of the symbol
 * i.e. PROMPT, MULTILINE, or MORELINES. The second argument (optional) is the symbol
 * that will replace the current one.
 *
 * @author Bruna Dujmović
 *
 */
public class SymbolCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "symbol";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "symbol symbol_type [new_symbol]",
            "\tsymbol_type -- the type of the symbol i.e. PROMPT, MULTILINE, or MORELINES",
            "\tnew_symbol (optional) -- the symbol that will replace the current one\n",
            "Prints the current symbol for the given symbol_type.",
            "If new_symbol is given, the current symbol for symbol_type will be replaced by it."
    );

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);

        if (parsed.length < 1 || parsed.length > 2) {
            env.writeln("Symbol accepts one or two arguments, " + parsed.length
                    + " were given.");
            return ShellStatus.CONTINUE;
        }

        if (parsed.length == 1) {
            getSymbol(parsed[0], env);
        } else {
            setSymbol(parsed[0], parsed[1], env);
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * A helper method that prints the current symbol for a given symbol type.
     *
     * @param symbolType the type of the symbol to print
     * @param env the {@link Environment} on which to print the symbol
     */
    private void getSymbol(String symbolType, Environment env) {
        switch (symbolType.toUpperCase()) {
            case "PROMPT":
                env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol()
                        + "'");
                break;
            case "MORELINES":
                env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol()
                        + "'");
                break;
            case "MULTILINE":
                env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol()
                        + "'");
                break;
            default:
                env.writeln("Unknown symbol type \"" + symbolType + "\"");
        }
    }

    /**
     * A helper method that changes the current symbol to a given new symbol.
     *
     * @param symbolType the type of the symbol to set
     * @param newSymbol the new symbol to set
     * @param env the {@link Environment} on which to print the results
     */
    private void setSymbol(String symbolType, String newSymbol, Environment env) {
        if (newSymbol.length() != 1) {
            env.writeln("Invalid symbol \"" + newSymbol + "\"");
            return;
        }

        switch (symbolType.toUpperCase()) {
            case "PROMPT":
                env.writeln("Symbol for PROMPT changed from '"
                        + env.getPromptSymbol() + "' to '" + newSymbol + "'");
                env.setPromptSymbol(newSymbol.charAt(0));
                break;
            case "MORELINES":
                env.writeln("Symbol for MORELINES changed from '"
                        + env.getMorelinesSymbol() + "' to '" + newSymbol + "'");
                env.setMorelinesSymbol(newSymbol.charAt(0));
                break;
            case "MULTILINE":
                env.writeln("Symbol for MULTILINE changed from '"
                        + env.getMultilineSymbol() + "' to '" + newSymbol + "'");
                env.setMultilineSymbol(newSymbol.charAt(0));
                break;
            default:
                env.writeln("Unknown symbol type \"" + symbolType + "\"");
        }
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
