package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * This {@link Command} implementation draws a colored line from the turtle's current
 * position to the position calculated from a given step.
 *
 * @see SkipCommand
 * @author Bruna Dujmović
 *
 */
public class DrawCommand implements Command {

    /**
     * The step that is used to calculate the end of the line to draw.
     */
    private double step;

    /**
     * Constructs a {@link DrawCommand} given a specified step.
     *
     * @param step the step that is used to calculate the end of the line to draw
     */
    public DrawCommand(double step) {
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

        painter.drawLine(
                currentPosition.getX(), currentPosition.getY(),
                nextPosition.getX(), nextPosition.getY(),
                currentState.getColor(), 1
        );
        currentState.setCurrentPostition(nextPosition);
    }
}
