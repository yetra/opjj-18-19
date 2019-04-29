package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.utility.FilterResult;
import hr.fer.zemris.java.hw06.shell.utility.Utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            "massrename src_dir_path dest_dir_path subcommand [other]",
            "\tsrc_dir_path -- ",
            "\tdest_dir_path -- ",
            "\tsubcommand -- ",
            "\tother (optional) -- \n",
            "description."
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
                env.writeln("TODO");
            }

        } catch (InvalidPathException e) {
            env.writeln("Illegal path string given!");
        } catch (IOException e) {
            env.writeln("Cannot perform massrename on the given paths.");
        }

        return ShellStatus.CONTINUE;
    }

    private void filterSubcommand(Path src, String pattern, Environment env) throws IOException {
        filter(src, pattern).forEach(
                (result) -> env.writeln(result.toString())
        );
    }

    private void groupsSubcommand(Path src, String pattern, Environment env) throws IOException {
        filter(src, pattern).forEach((result) -> {
            env.write(result.toString());

            for (int i = 0, groupCount = result.numberOfGroups(); i <= groupCount; i++) {
                env.write(" " + i + ": " + result.group(i));
            }

            env.write("\n");
        });
    }

    private List<FilterResult> filter(Path dir, String pattern) throws IOException {
        return Files.list(dir)
                .filter((path) -> Files.isRegularFile(path) &&
                        Pattern.matches(pattern, path.getFileName().toString()))
                .map((path) -> new FilterResult(path.getFileName().toString(), pattern))
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
