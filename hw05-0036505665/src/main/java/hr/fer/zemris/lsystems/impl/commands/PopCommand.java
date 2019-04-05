package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This {@link Command} implementation removes a turtle state from the top of the
 * {@link Context} stack.
 *
 * @author Bruna Dujmović
 *
 */
public class PopCommand implements Command {

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.popState();
    }
}
