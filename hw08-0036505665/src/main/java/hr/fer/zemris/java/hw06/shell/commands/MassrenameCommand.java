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

public class MassrenameCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "massrename";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "massrename src_dir_path dest_dir_path subcommand mask [other]",
            "\tsrc_dir_path -- path to the source directory",
            "\tdest_dir_path -- path to the destination directory",
            "\tsubcommand -- name of the subcommand to perform:",
            "\t\tfilter -- prints all file names that match the given mask",
            "\t\tgroups -- prints all the capturing groups found in each file name " +
                    "that matches the given mask",
            "\t\tshow",
            "\t\texecute",
            "\tmask -- the pattern for matching the file names",
            "\tother (optional) -- \n",
            "Performs an operation of all files of the source directory, based on the" +
                    "given subcommand."
    );

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);
        Path srcPath;
        Path destPath;

        if (parsed.length < 4 || parsed.length > 5) {
            env.writeln("Massrename accepts four or five arguments, " + parsed.length + " were given.");
            return ShellStatus.CONTINUE;
        }

        try {
            srcPath = env.getCurrentDirectory().resolve(Paths.get(parsed[0]));
            destPath = env.getCurrentDirectory().resolve(Paths.get(parsed[1]));

            if (!Files.isDirectory(srcPath) || !Files.isDirectory(destPath)) {
                env.writeln("The source and destination paths must point to a directory.");
                return ShellStatus.CONTINUE;
            }

            if (parsed.length == 4) {
                if (parsed[2].equalsIgnoreCase("filter")) {
                    filterSubcommand(srcPath, parsed[3], env);
                } else if (parsed[2].equalsIgnoreCase("groups")) {
                    groupsSubcommand(srcPath, parsed[3], env);
                } else {
                    env.writeln("Unknown subcommand \"" + parsed[2] + "\".");
                }

            } else {
                if (parsed[2].equalsIgnoreCase("show")) {
                    showSubcommand(srcPath, parsed[3], parsed[4], env);
                } else if (parsed[2].equalsIgnoreCase("execute")) {
                    executeCommand(srcPath, destPath, parsed[3], parsed[4], env);
                } else {
                    env.writeln("Unknown subcommand \"" + parsed[2] + "\".");
                }
            }

        } catch (InvalidPathException e) {
            env.writeln("Illegal path string given!");
        } catch (IOException e) {
            env.writeln("Cannot perform massrename on the given paths.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Prints the file names in the source directory that match the specified pattern.
     *
     * @param src the path to the source directory that contains the files
     * @param pattern the pattern for matching the file names
     * @param env the {@link Environment} object to print to
     * @throws IOException if there is an issue with the given directory
     */
    private void filterSubcommand(Path src, String pattern, Environment env) throws IOException {
        filter(src, pattern).forEach(result -> env.writeln(result.toString()));
    }

    /**
     * Prints all the capturing groups found in each file name that matches the given
     * pattern.
     *
     * @param src the path to the source directory that contains the files
     * @param pattern the pattern for matching the file names
     * @param env the {@link Environment} object to print to
     * @throws IOException if there is an issue with the given directory
     */
    private void groupsSubcommand(Path src, String pattern, Environment env) throws IOException {
        filter(src, pattern).forEach(result -> {
            env.write(result.toString());

            int groupCount = result.numberOfGroups();
            for (int i = 0; i <= groupCount; i++) {
                env.write(" " + i + ": " + result.group(i));
            }

            env.write("\n");
        });
    }

    private void showSubcommand(Path src, String pattern, String newPattern, Environment env) throws IOException {
        NameBuilderParser parser = new NameBuilderParser(newPattern);
        NameBuilder builder = parser.getNameBuilder();

        filter(src, pattern).forEach((result) -> {
            StringBuilder sb = new StringBuilder();
            builder.execute(result, sb);
            env.writeln(result.toString() + " => " + sb.toString());
        });
    }

    private void executeCommand(Path src, Path dest, String pattern, String newPattern, Environment env) throws IOException {
        NameBuilderParser parser = new NameBuilderParser(newPattern);
        NameBuilder builder = parser.getNameBuilder();

        List<FilterResult> files = filter(src, pattern);
        for (FilterResult result : files) {
            StringBuilder sb = new StringBuilder();
            builder.execute(result, sb);

            Path srcFilePath = Paths.get(src.toString() + "/" + result.toString());
            Path destFilePath = Paths.get(dest.toString() + "/" + sb.toString());
            Files.move(srcFilePath, destFilePath);

            env.writeln(srcFilePath.toString() + " => " + destFilePath.toString());
        }
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


    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(DESCRIPTION);
    }
}
