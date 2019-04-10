package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This {@link Command} implementation copies the turtle state that's on the top of the
 * {@link Context} stack and pushes the copy back to the stack.
 *
 * @author Bruna Dujmović
 *
 */
public class PushCommand implements Command {

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.pushState(ctx.getCurrentState().copy());
    }
}
