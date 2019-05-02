package hr.fer.zemris.java.hw06.shell;

/**
 * An enum of possible statuses of {@link MyShell}.
 *
 * @author Bruna Dujmović
 *
 */
public enum ShellStatus {
    /**
     * If {@link MyShell} is in this status, it will continue to read the user input
     * from the console.
     */
    CONTINUE,

    /**
     * If {@link MyShell} is in this status, it will stop reading the user input and
     * the program will terminate.
     */
    TERMINATE
}
