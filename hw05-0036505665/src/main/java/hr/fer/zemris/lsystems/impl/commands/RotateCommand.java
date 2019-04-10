package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This {@link Command} implementation changes the direction vector of a turtle's
 * current state by rotating it by a given angle.
 *
 * @author Bruna Dujmović
 *
 */
public class RotateCommand implements Command {

    /**
     * The angle by which to rotate a turtle's direction vector.
     */
    private double angle;

    /**
     * Constructs a {@link RotateCommand} given a specified rotation angle.
     *
     * @param angle the angle by which to rotate a turtle's direction vector
     */
    public RotateCommand(double angle) {
        this.angle = angle;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.getCurrentState().getDirection().rotate(angle);
    }
}
