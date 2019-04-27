package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.utility.Utility;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the hexdump command that prints a hexadecimal representation
 * of the contents of a given file. It accepts one argument - the path of the file to
 * print.
 *
 * @author Bruna Dujmović
 *
 */
public class HexdumpCommand implements ShellCommand {

    /**
     * The name of this command.
     */
    private static final String NAME = "hexdump";

    /**
     * The description of this command.
     */
    private static final List<String> DESCRIPTION = List.of(
            "hexdump file_path",
            "\tfile_path -- the path of the file to print\n",
            "Prints a hexadecimal representation of the contents of the specified file."
    );

    /**
     * The default size of the byte array used for reading the contents of a given file.
     */
    private static final int DEFAULT_BUFFER_SIZE = 16;

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] parsed = Utility.parseArguments(arguments);

        if (parsed.length != 1) {
            env.writeln("Hexdump accepts one argument, " + parsed.length
                    + " were given.");
            return ShellStatus.CONTINUE;
        }

        try {
            Path filePath = Paths.get(parsed[0]).resolve(env.getCurrentDirectory());

            if (filePath.toFile().isFile()) {
                printHexOutput(filePath, env);
            } else {
                env.writeln("The given path does not point to a file.");
            }

        } catch (InvalidPathException e) {
            env.writeln("Illegal path string \"" + parsed[0] + "\".");
        } catch (IOException e) {
            env.writeln("I/O error occurred.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * A helper method that prints the hex output of a given file using the
     * {@link Environment} object.
     *
     * @param filePath the path of the file to print
     * @param env the {@link Environment} object to use for printing
     * @throws IOException if and I/O error occurs during the reading of the file
     */
    private void printHexOutput(Path filePath, Environment env) throws IOException {
        try (InputStream stream = new BufferedInputStream(
                Files.newInputStream(filePath))) {

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int bytesRead;

            int lineCount = 0x0;
            while ((bytesRead = stream.read(buffer)) != -1) {
                StringBuilder sb = new StringBuilder(String.format("%08X: ", lineCount));

                for (int i = 0; i < DEFAULT_BUFFER_SIZE; i++) {
                    sb.append(i < bytesRead ? String.format("%02X ", buffer[i])
                                            : "   ");

                    if (buffer[i] < 32 || buffer[i] > 127) {
                        buffer[i] = '.';
                    }
                    if (i == 7) {
                        sb.append("| ");
                    }
                }

                sb.append("| ").append(new String(buffer, 0, bytesRead));

                lineCount += 0x10;
                env.writeln(sb.toString());
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
