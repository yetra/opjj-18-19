package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.commands.*;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class is an implementation of the {@link Environment} interface that uses a
 * {@link Scanner} object to communicate with the user.
 *
 * @author Bruna Dujmović
 *
 */
public class EnvironmentImpl implements Environment {

    /**
     * A map of all supported commands in {@link MyShell}.
     */
    private static SortedMap<String, ShellCommand> COMMANDS;

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
     * The {@link Scanner} object used for communicating with the user.
     */
    private Scanner scanner;

    /**
     * The path to the current directory.
     */
    private Path currentDirectory;

    /**
     * A map of all the commands' shared data.
     */
    private Map<String, Object> sharedData;

    static {
        COMMANDS = new TreeMap<>();

        COMMANDS.put("symbol", new SymbolCommand());
        COMMANDS.put("charsets", new CharsetsCommand());
        COMMANDS.put("cat", new CatCommand());
        COMMANDS.put("ls", new LsCommand());
        COMMANDS.put("tree", new TreeCommand());
        COMMANDS.put("copy", new CopyCommand());
        COMMANDS.put("mkdir", new MkdirCommand());
        COMMANDS.put("hexdump", new HexdumpCommand());
        COMMANDS.put("pwd", new PwdCommand());
        COMMANDS.put("cd", new CdCommand());
        COMMANDS.put("pushd", new PushdCommand());
        COMMANDS.put("popd", new PopdCommand());
        COMMANDS.put("listd", new ListdCommand());
        COMMANDS.put("dropd", new DropdCommand());
        COMMANDS.put("exit", new ExitCommand());
        COMMANDS.put("help", new HelpCommand());
    }

    /**
     * Default constructior for the {@link EnvironmentImpl} class.
     */
    public EnvironmentImpl() {
        scanner = new Scanner(System.in);
        currentDirectory = Paths.get(".");
        sharedData = new HashMap<>();
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
        return Collections.unmodifiableSortedMap(COMMANDS);
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

    @Override
    public Path getCurrentDirectory() {
        return currentDirectory.toAbsolutePath().normalize();
    }

    @Override
    public void setCurrentDirectory(Path path) {
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IllegalArgumentException(
                    "The given path does not point to an existing directory.");
        }

        currentDirectory = path;
    }

    @Override
    public Object getSharedData(String key) {
        return sharedData.get(key);
    }

    @Override
    public void setSharedData(String key, Object value) {
        Objects.requireNonNull(key);
        
        sharedData.put(key, value);
    }
}
