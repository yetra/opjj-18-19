package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

import java.awt.*;

/**
 * This {@link Command} implementation changes the current tutle state's color to a
 * given color.
 *
 * @author Bruna Dujmović
 *
 */
public class ColorCommand implements Command {

    /**
     * The new color of the current turtle state.
     */
    private Color color;

    /**
     * Constructs a {@link ColorCommand} given a specified color.
     *
     * @param color the new color of the current turtle state
     */
    public ColorCommand(Color color) {
        this.color = color;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.getCurrentState().setColor(color);
    }
}
