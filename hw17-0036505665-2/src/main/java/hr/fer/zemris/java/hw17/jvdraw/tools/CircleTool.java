package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

import java.awt.event.MouseEvent;

/**
 * A tool for drawing circles.
 *
 * @see Tool
 * @author Bruna Dujmović
 *
 */
public class CircleTool extends AbstractTool {

    /**
     * The circle to draw.
     */
    private Circle circle;

    /**
     * Creates a new {@link LineTool} of the given parameters.
     *
     * @param fgColorProvider the foreground color provider
     * @param model the model containing geometrical objects
     */
    public CircleTool(IColorProvider fgColorProvider, DrawingModel model) {
        super(fgColorProvider, model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!alreadyClicked) {
            circle = new Circle(e.getPoint(), e.getPoint(),
                    fgColorProvider.getCurrentColor());

            model.add(circle);
            alreadyClicked = true;

        } else {
            circle.setRadiusPoint(e.getPoint());

            alreadyClicked = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (alreadyClicked) {
            circle.setRadiusPoint(e.getPoint());
        }
    }
}
