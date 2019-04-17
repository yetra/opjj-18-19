package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.commands.*;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class is an implementation of the {@link Environment} interface that uses a
 * {@link Scanner} object to communicate with the user.
 *
 * @author Bruna Dujmović
 *
 */
public class EnvironmentImpl implements Environment {

    /**
     * The symbol which denotes the start of each shell prompt.
     */
    private char promptSymbol = '>';

    /**
     * The symbol that must be at the end of each line (except the last) of a
     * multi-line command to inform the shell that more lines are expected.
     */
    private char moreLinesSymbol = '\\';

    /**
     * The symbol which denotes the start of each new line in a multi-line command.
     */
    private char multiLineSymbol = '|';

    /**
     * A map of all supported commands in {@link MyShell}.
     */
    private SortedMap<String, ShellCommand> commands;

    /**
     * The {@link Scanner} object used for communicating with the user.
     */
    private Scanner scanner;

    /**
     * Default constructior for the {@link EnvironmentImpl} class.
     */
    public EnvironmentImpl() {
        scanner = new Scanner(System.in);

        commands = new TreeMap<>();
        commands.put("charsets", new CharsetsCommand());
        commands.put("cat", new CatCommand());
        commands.put("ls", new LsCommand());
        commands.put("tree", new TreeCommand());
        commands.put("copy", new CopyCommand());
        commands.put("mkdir", new MkdirCommand());
        commands.put("hexdump", new HexdumpCommand());
        commands.put("exit", new ExitCommand());
        commands.put("help", new HelpCommand());
    }

    @Override
    public String readLine() throws ShellIOException {
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException | IllegalStateException e) {
            throw new ShellIOException();
        }
    }

    @Override
    public void write(String text) throws ShellIOException {
        System.out.print(text);
    }

    @Override
    public void writeln(String text) throws ShellIOException {
        System.out.println(text);
    }

    @Override
    public SortedMap<String, ShellCommand> commands() {
        return commands;
    }

    @Override
    public Character getMultilineSymbol() {
        return multiLineSymbol;
    }

    @Override
    public void setMultilineSymbol(Character symbol) {
        multiLineSymbol = symbol;
    }

    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    @Override
    public void setPromptSymbol(Character symbol) {
        promptSymbol = symbol;
    }

    @Override
    public Character getMorelinesSymbol() {
        return moreLinesSymbol;
    }

    @Override
    public void setMorelinesSymbol(Character symbol) {
        moreLinesSymbol = symbol;
    }
}
