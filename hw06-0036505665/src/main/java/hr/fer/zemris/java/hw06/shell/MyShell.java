package hr.fer.zemris.java.hw06.shell;

/**
 * {@link MyShell} is a command-line program that models a shell.
 *
 * When started, it prints a greeting message to the user, prints a prompt symbol,
 * and then waits for the user to enter a command.
 *
 * The program supports the following commands:
 *
 *     symbol symbol_type [new_symbol]
 *         - prints the current symbol for the given symbol_type (PROMPT, MULTILINE,
 *           or MORELINES), or changes it to the given new_symbol if it is specified
 *
 *     charsets
 *         - lists the names of the supported charsets for the user's Java platform
 *
 *     cat file_path [charset_name]
 *         - opens the given file and writes its content to the console
 *         - if charset_name is given, the file be interpreted using that charset
 *           instead of the default platform charset
 *
 *     ls dir_path
 *         - prints a non-recursive listing of the specified directory
 *
 *     tree dir_path
 *         - prints a recursive, tree-like listing of the specified directory
 *
 *     copy src_file_path dest_file_path
 *         - copies the specified source file to the destination file
 *         - if the destination file already exists, the user will be asked if they
 *           want to overwrite it
 *
 *     mkdir dir_path
 *         - creates the appropriate directory structure at the location specified by
 *           dir_path
 *
 *     hexdump file_path
 *         - prints a hex representation of the specified file
 *
 *     help [command_name]
 *         - prints the name and description of the specified command, or a list of
 *           all supported commands if command_name is not given
 *
 *     exit
 *         - terminates the program
 *
 *
 * Commands can span across multiple lines if each line that is not the last ends with
 * the appropriate MORELINES symbol.
 *
 * @author Bruna Dujmović
 *
 */ 
public class MyShell {
    /*
     *   build environment
     *   repeat {
     *
     *      l = readLineOrLines
     *      String commandName = extract from l
     *      String arguments = extract from l
     *      command = commands.get(commandName)
     *      status = command.executeCommand(environment, arguments)
     *   } until status!=TERMINATE
     */
}
