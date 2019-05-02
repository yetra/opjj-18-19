package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.utility.FilterResult;
import hr.fer.zemris.java.hw06.shell.utility.NameBuilder;
import hr.fer.zemris.java.hw06.shell.utility.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.utility.Utility;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class represents the massrename command that performs an operation on all the
 * files in a given source directory, depending on the specified subcommand (filter/
 * groups/show/execute).
 *
 * @author Bruna Dujmović
 *
 */
public class MassrenameCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "massrename";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "massrename src_dir_path dest_dir_path subcommand mask [rename_expr]",
            "\tsrc_dir_path -- path to the source directory",
            "\tdest_dir_path -- path to the destination directory",
            "\tsubcommand -- name of the subcommand to perform:",
            "\t\tfilter -- prints all file names that match the given mask",
            "\t\tgroups -- prints all the capturing groups found in each file name " +
                    "that matches the given mask",
            "\t\tshow -- prints the file names before and after renaming according to" +
                    "the rename_expr expression",
            "\t\texecute -- performs the rename on the files",
            "\tmask -- the pattern for matching the file names",
            "\trename_expr (optional) -- the file name pattern for renaming\n",
            "Performs an operation on all the files of the source directory, depending " +
                    "on the specified subcommand (filter/groups/show/execute)."
    );

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);
        Path srcPath;
        Path destPath;

        if (parsed.length < 4 || parsed.length > 5) {
            env.writeln("Massrename accepts four or five arguments, "
                    + parsed.length + " were given.");
            return ShellStatus.CONTINUE;
        }

        try {
            srcPath = env.getCurrentDirectory().resolve(Paths.get(parsed[0]));
            destPath = env.getCurrentDirectory().resolve(Paths.get(parsed[1]));

            if (!Files.isDirectory(srcPath) || !Files.isDirectory(destPath)) {
                env.writeln(
                        "The source and destination paths must point to a directory.");
                return ShellStatus.CONTINUE;
            }

            String subcommand = parsed[2].toLowerCase();
            if (subcommand.equals("filter") || subcommand.equals("groups")) {
                checkLengthIs(4, parsed);
                printFiltered(subcommand.equals("groups"), srcPath, parsed[3], env);

            } else if (subcommand.equals("show") || subcommand.equals("execute")) {
                checkLengthIs(5, parsed);
                showOrExecute(subcommand.equalsIgnoreCase("execute"), srcPath, destPath,
                        parsed[3], parsed[4], env);
            } else {
                env.writeln("Unknown subcommand \"" + parsed[2] + "\".");
            }

        } catch (InvalidPathException e) {
            env.writeln("Illegal path string given!");
        } catch (IOException e) {
            env.writeln("Cannot perform massrename on the given paths.");
        } catch (IllegalArgumentException e) {
            env.writeln("Illegal argument: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            env.writeln("Index out of bounds: " + e.getMessage());
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
     * Iterates over the files in a given directory and returns a list of those whose
     * name matches the specified pattern.
     *
     * @param dir the path to the directory to iterate
     * @param pattern the pattern for matching file names
     * @return a list of files whose name matches the specified pattern
     * @throws IOException if there is an issue with the given directory
     */
    private static List<FilterResult> filter(Path dir, String pattern) throws IOException {
        return Files.list(dir)
                .filter(path -> Files.isRegularFile(path) &&
                        Pattern.matches(pattern, path.getFileName().toString()))
                .map(path -> new FilterResult(path, pattern))
                .collect(Collectors.toList());
    }

    /*
     * -----------------------------------------------------------------------------
     * ------------------------------ HELPER METHODS -------------------------------
     * -----------------------------------------------------------------------------
     */

    /**
     * Prints all the file names that match the given pattern. If the {@code groups}
     * parameter is {@code true}, this method will also print the capturing groups
     * found in each file name.
     *
     * @param groups {@code true} if capturing groups should also be printed
     * @param src the path to the source directory that contains the files
     * @param pattern the pattern for matching the file names
     * @param env the {@link Environment} object to print to
     * @throws IOException if there is an issue with the given directory
     */
    private void printFiltered(boolean groups, Path src, String pattern,
                               Environment env) throws IOException {

        List<FilterResult> filtered = filter(src, pattern);

        for (FilterResult result : filtered) {
            env.write(result.toString());

            if (groups) {
                int groupCount = result.numberOfGroups();
                for (int i = 0; i <= groupCount; i++) {
                    env.write(" " + i + ": " + result.group(i));
                }
            }

            env.write("\n");
        }
    }

    /**
     * Prints all the new file names after applying a given renaming pattern. If the
     * {@code execute} parameter is {@code true}, this method will also perform the
     * actual renaming on the files.
     *
     * @param execute {@code true} if capturing groups should also be printed
     * @param src the path to the source directory that contains the files
     * @param dest the path to the destination directory for the files
     * @param pattern the pattern for matching the file names
     * @param newPattern the pattern for renaming
     * @param env the {@link Environment} object to print to
     * @throws IOException if there is an issue with the given directory
     */
    private void showOrExecute(boolean execute, Path src, Path dest, String pattern,
                               String newPattern, Environment env) throws IOException {

        NameBuilderParser parser = new NameBuilderParser(newPattern);
        NameBuilder builder = parser.getNameBuilder();
        List<FilterResult> filtered = filter(src, pattern);

        for (FilterResult result : filtered) {
            StringBuilder sb = new StringBuilder();
            builder.execute(result, sb);

            if (execute) {
                Path srcFilePath = Paths.get(src.toString() + "/" + result.toString());
                Path destFilePath = Paths.get(dest.toString() + "/" + sb.toString());
                Files.move(srcFilePath, destFilePath);

                env.writeln(srcFilePath.toString() + " => " + destFilePath.toString());

            } else {
                env.writeln(result.toString() + " => " + sb.toString());
            }
        }
    }

    /**
     * Checks if the expected length matches the length of the given array.
     *
     * @param length the expected length
     * @param arguments the array to check
     * @throws IllegalArgumentException if the given array's length does not match the
     *         expected length
     */
    private void checkLengthIs(int length, String[] arguments) {
        if (length != arguments.length) {
            throw new IllegalArgumentException("Expected " + length + "arguments, "
                    + arguments.length + " were given.");
        }
    }
}
