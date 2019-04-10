package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * A functional interface for turtle command execution.
 *
 * @author Bruna Dujmović
 *
 */
@FunctionalInterface
public interface Command {

    /**
     * Executes a command.
     *
     * @param ctx the current context to execute a command on
     * @param painter the painter that will reflect the changes after executing a
     *                command
     */
    void execute(Context ctx, Painter painter);
}
