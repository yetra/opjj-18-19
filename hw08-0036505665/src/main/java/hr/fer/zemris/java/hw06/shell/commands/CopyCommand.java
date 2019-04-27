package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.utility.Utility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the copy command that copies a given source file to a
 * specified destination file.
 *
 * It accepts two arguments. The first argument is the path of the file to copy from.
 * The second argument is the path of the file to copy to.
 *
 * @author Bruna Dujmović
 *
 */
public class CopyCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "copy";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "copy src_file_path dest_file_path",
            "\tsrc_file_path -- the source file to copy from",
            "\tdest_file_path -- the destination file to copy to\n",
            "Copies the contents of the given source file to the specified " +
                    "destination file.",
            "If the destination file already exists, the user will be asked if" +
                    " they wish to overwrite it.",
            "If the destination file is a directory, the source file will be" +
                    " copied into that directory using the original file name."
    );

    /**
     * The default input/output stream buffer size.
     */
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);

        if (parsed.length < 1 || parsed.length > 2) {
            env.writeln("Copy accepts two arguments, " + parsed.length + " were given.");
            return ShellStatus.CONTINUE;
        }

        try {
            Path srcPath = Paths.get(parsed[0]).relativize(env.getCurrentDirectory());
            Path destPath = Paths.get(parsed[1]).relativize(env.getCurrentDirectory());

            if (Files.isDirectory(srcPath)) {
                env.writeln("The source file cannot be a directory.");
                return ShellStatus.CONTINUE;
            }

            if (Files.isDirectory(destPath)) {
                copyToDirectory(srcPath, destPath, env);
            } else if (Files.exists(destPath)) {
                overwriteIfAllowed(srcPath, destPath, env);
            } else {
                copyFile(srcPath, destPath);
            }

        } catch (InvalidPathException e) {
            env.writeln("Illegal path string \"" + parsed[0] + "\".");
        } catch (IOException e) {
            env.writeln("Cannot perform copy on the given arguments.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Reads from the given source file and copies its content to the destination
     * directory. If a file with the same name already exists in the directory, this
     * method will ask if the user if they wish to overwrite it.
     *
     * @param src the source file to read from
     * @param dest the destination directory to copy to
     * @param env the {@link Environment} used for communicating with the user
     * @throws IOException if and I/O error occurs during the reading/writing of the files
     */
    private void copyToDirectory(Path src, Path dest, Environment env) throws IOException {
        if (!Files.exists(dest)) {
            env.writeln("The given destination directory does not exist.");
            return;
        }

        Path destFilePath = Paths.get(dest.toAbsolutePath() + "/" + src.getFileName());
        if (destFilePath.toFile().exists()) {
            overwriteIfAllowed(src, destFilePath, env);
        } else {
            copyFile(src, destFilePath);
        }
    }

    /**
     * Prompts the user if they wish to overwrite the destination file, and overwrites
     * it if allowed.
     *
     * @param src the source file to read from
     * @param dest the destination file to overwrite
     * @param env the {@link Environment} used for communicating with the user
     * @throws IOException if and I/O error occurs during the reading/writing of the files
     */
    private void overwriteIfAllowed(Path src, Path dest, Environment env) throws IOException {
        env.writeln("The specified destination file already exists." +
                " Do you wish to overwrite it [y/n]?");
        String response = env.readLine();

        if (response.equalsIgnoreCase("y")) {
            copyFile(src, dest);

        } else if (!response.equalsIgnoreCase("n")) {
            env.writeln("Unknown response \"" + response + "\".");
        }
    }

    /**
     * Reads from the given source file and writes its content to the destination file.
     *
     * @param src the source file to read from
     * @param dest the destination file to write to
     * @throws IOException if and I/O error occurs during the reading/writing of the files
     */
    private void copyFile(Path src, Path dest) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(src.toFile()));
             OutputStream os = new BufferedOutputStream(new FileOutputStream(dest.toFile()))) {

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
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
