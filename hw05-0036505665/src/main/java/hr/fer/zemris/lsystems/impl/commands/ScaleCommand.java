package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This {@link Command} implementation scales the current tutle state's effective
 * move length by a given factor.
 *
 * @author Bruna Dujmović
 *
 */
public class ScaleCommand implements Command {

    /**
     * The factor by which to scale the current effective move length.
     */
    private double factor;

    /**
     * Constructs a {@link ScaleCommand} given a specified factor.
     *
     * @param factor the factor by which to scale the current effective move length
     */
    public ScaleCommand(double factor) {
        this.factor = factor;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState currentState = ctx.getCurrentState();

        double scaledLength = factor * currentState.getEffectiveMoveLength();
        currentState.setEffectiveMoveLength(scaledLength);
    }
}
