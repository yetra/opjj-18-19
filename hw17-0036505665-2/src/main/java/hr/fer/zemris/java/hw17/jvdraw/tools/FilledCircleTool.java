package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

import java.awt.event.MouseEvent;

/**
 * A tool for drawing filled circles.
 *
 * @see Tool
 * @author Bruna Dujmović
 *
 */
public class FilledCircleTool extends AbstractTool {

    /**
     * The circle fill color provider.
     */
    IColorProvider bgColorProvider;

    /**
     * The circle to draw.
     */
    private FilledCircle filledCircle;

    /**
     * Creates a new {@link LineTool} of the given parameters.
     *
     * @param fgColorProvider the foreground color provider
     * @param model the model containing geometrical objects
     */
    public FilledCircleTool(IColorProvider fgColorProvider,
                            IColorProvider bgColorProvider,
                            DrawingModel model) {

        super(fgColorProvider, model);
        this.bgColorProvider = bgColorProvider;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!alreadyClicked) {
            filledCircle = new FilledCircle(e.getPoint(), e.getPoint(),
                    fgColorProvider.getCurrentColor(),
                    bgColorProvider.getCurrentColor());

            model.add(filledCircle);
            alreadyClicked = true;

        } else {
            filledCircle.setRadiusPoint(e.getPoint());

            alreadyClicked = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (alreadyClicked) {
            filledCircle.setRadiusPoint(e.getPoint());
        }
    }
}
