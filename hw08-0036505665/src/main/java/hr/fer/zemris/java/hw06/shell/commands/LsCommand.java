package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.utility.Utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This class represents the ls command that prints a non-recursive listing of a
 * given directory to the console. It accepts one argument - the path to the directory.
 *
 * @author Bruna Dujmović
 *
 */
public class LsCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "ls";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "ls dir_path",
            "\tdir_path -- path to the directory to be listed\n",
            "Prints a non-recursive listing of the specified directory."
    );

    /**
     * The date format for formatting a file's creation date and time.
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);

        if (parsed.length != 1) {
            env.writeln("Ls accepts one argument, " + parsed.length + " were given.");
            return ShellStatus.CONTINUE;
        }

        try {
            File directory = Paths.get(parsed[0]).relativize(env.getCurrentDirectory()).toFile();

            if (directory.isDirectory()) {
                printDirectoryListing(directory, env);
            } else {
                env.writeln("The given file is not a directory.");
            }

        } catch (InvalidPathException e) {
            env.writeln("Illegal path string \"" + parsed[0] + "\".");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * A helper method that prints the non-recursive listing of a specified directory
     * using the given {@link Environment}.
     *
     * @param directory the directory whose listing should be printed
     * @param env the {@link Environment} to print the file to
     */
    private void printDirectoryListing(File directory, Environment env) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                env.write(getTypeAndPermissionsOf(file) + " ");
                env.write(getSizeOf(file) + " ");
                env.write(getCreationDateTimeOf(file) + " ");
                env.writeln(file.getName());
            }
        }
    }

    /**
     * Returns a string containing the type (directory/file) and permissions
     * (readable/writable/executable) of a given file.
     *
     * @param file the file whose type and permissions should be returned
     * @return a string containing the type and permissions of a given file
     */
    private String getTypeAndPermissionsOf(File file) {
        return (file.isDirectory() ? "d" : "-") +
                (file.canRead() ? "r" : "-") +
                (file.canWrite() ? "w" : "-") +
                (file.canExecute() ? "x" : "-");
    }

    /**
     * Returns the formatted size (in bytes) of a given file.
     *
     * @param file the file whose size should be returned
     * @return the formatted size (in bytes) of a given file
     */
    private String getSizeOf(File file) {
        return String.format("%10d", file.length());
    }

    /**
     * Returns the formatted creation date/time of a given file.
     *
     * @param file the file whose date/time should be returned
     * @return the formatted creation date/time of a given file
     */
    private String getCreationDateTimeOf(File file) {
        try {
            BasicFileAttributeView faView = Files.getFileAttributeView(
                    file.toPath(), BasicFileAttributeView.class,
                    LinkOption.NOFOLLOW_LINKS
            );
            BasicFileAttributes attributes = faView.readAttributes();

            FileTime fileTime = attributes.creationTime();
            return DATE_FORMAT.format(new Date(fileTime.toMillis()));

        } catch (IOException e) {
            return "unknown";
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
