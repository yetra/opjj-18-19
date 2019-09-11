package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * An abstract implementation of {@link Tool} where all methods do nothing by default.
 *
 * @see Tool
 * @author Bruna Dujmović
 */
public class AbstractTool implements Tool {

    /**
     * The outline color provider.
     */
    IColorProvider fgColorProvider;

    /**
     * The model containing geometrical objects.
     */
    DrawingModel model;

    /**
     * {@code true} if the canvas has already been clicked.
     */
    boolean alreadyClicked = false;

    public AbstractTool(IColorProvider fgColorProvider, DrawingModel model) {
        this.fgColorProvider = fgColorProvider;
        this.model = model;
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void paint(Graphics2D g2d) {}
}
