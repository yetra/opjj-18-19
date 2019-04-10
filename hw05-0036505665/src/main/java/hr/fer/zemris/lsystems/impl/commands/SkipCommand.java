package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * This {@link Command} implementation changes the turtle's current position to the
 * position calculated from a given step without drawing a colored line between them.
 *
 * @see DrawCommand
 * @author Bruna Dujmović
 *
 */
public class SkipCommand implements Command {

    /**
     * The step that is used to calculate the next turtle position.
     */
    private double step;

    /**
     * Constructs a {@link SkipCommand} given a specified step.
     *
     * @param step the step that is used to calculate the next turtle position
     */
    public SkipCommand(double step) {
        this.step = step;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState currentState = ctx.getCurrentState();
        Vector2D currentPosition = currentState.getCurrentPostition();
        Vector2D currentDirection = currentState.getDirection();

        double moveLength = step * currentState.getEffectiveMoveLength();
        Vector2D nextPosition = currentPosition.translated(
                currentDirection.scaled(moveLength));

        currentState.setCurrentPostition(nextPosition);
    }
}
