package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.utility.Utility;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the tree command that prints a recursive, tree-like listing
 * of a given directory to the console. It accepts one argument - the path to the
 * directory.
 *
 * @author Bruna Dujmović
 *
 */
public class TreeCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "tree";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "tree dir_path",
            "\tdir_path -- path to the directory to be listed\n",
            "Prints a recursive, tree-like listing of the specified directory."
    );

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);

        if (parsed.length != 1) {
            env.writeln("Tree accepts one argument, " + parsed.length + " were given.");
            return ShellStatus.CONTINUE;
        }

        try {
            Path directoryPath = env.getCurrentDirectory().resolve(Paths.get(parsed[0]));

            if (Files.isDirectory(directoryPath)) {
                Files.walkFileTree(directoryPath, new TreeFileVisitor(env));
            } else {
                env.writeln("The given file is not a directory.");
            }

        } catch (InvalidPathException e) {
            env.writeln("Illegal path string \"" + parsed[0] + "\".");
        } catch (IOException e) {
            env.writeln("I/O error occurred.");
        }

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

    /**
     * A {@link FileVisitor} implementation that will print a tree-like listing of
     * the given root directory.
     */
    private class TreeFileVisitor extends SimpleFileVisitor<Path> {

        /**
         * The {@link Environment} to be used for printing the listing.
         */
        private Environment env;

        /**
         * The current depth of the directory tree.
         */
        private int depth = 0;

        /**
         * Constructs a new {@link TreeFileVisitor} object with a given
         * {@link Environment}.
         *
         * @param env the {@link Environment} to be used for printing the listing
         * @throws NullPointerException if the given {@link Environment} is {@code null}
         */
        TreeFileVisitor(Environment env) {
            this.env = Objects.requireNonNull(env);
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            env.writeln("  ".repeat(depth) + dir.getFileName().toString());
            depth++;

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            env.writeln("  ".repeat(depth) + file.getFileName().toString());

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            depth--;

            return FileVisitResult.CONTINUE;
        }
    }
}
