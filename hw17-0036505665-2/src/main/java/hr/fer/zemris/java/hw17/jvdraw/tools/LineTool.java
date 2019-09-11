package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

import java.awt.event.MouseEvent;

/**
 * A tool for drawing lines.
 *
 * @see Tool
 * @author Bruna Dujmović
 *
 */
public class LineTool extends AbstractTool {

    /**
     * The line to draw.
     */
    private Line line;

    /**
     * Creates a new {@link LineTool} of the given parameters.
     *
     * @param fgColorProvider the foreground color provider
     * @param model the model containing geometrical objects
     */
    public LineTool(IColorProvider fgColorProvider, DrawingModel model) {
        super(fgColorProvider, model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!alreadyClicked) {
            line = new Line(e.getPoint(), e.getPoint(),
                    fgColorProvider.getCurrentColor());

            model.add(line);
            alreadyClicked = true;

        } else {
            line.setEnd(e.getPoint());

            alreadyClicked = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (alreadyClicked) {
            line.setEnd(e.getPoint());
        }
    }
}
