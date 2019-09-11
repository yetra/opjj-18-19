package hr.fer.zemris.java.hw17.jvdraw.components;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.function.Supplier;

/**
 * Models a canvas for drawing geometrical objects.
 *
 * @author Bruna Dujmović
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

    /**
     * The model used for storing geometrical objects.
     */
    private DrawingModel model;

    /**
     * The currently selected tool.
     */
    private Tool tool;

    /**
     * Constructs a {@link JDrawingCanvas} of the given parameters.
     *
     * @param model the model used for storing geometrical objects
     * @param toolSupplier a supplier for the currently selected tool
     */
    public JDrawingCanvas(DrawingModel model, Supplier<Tool> toolSupplier) {
        this.model = model;
        this.tool = toolSupplier.get();

        model.addDrawingModelListener(this);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tool.mouseClicked(e);
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                tool.mouseMoved(e);
            }
        });

    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);

        for (int i = 0, size = model.getSize(); i < size; i++) {
            GeometricalObject object = model.getObject(i);
            object.accept(painter);
        }

        tool.paint(g2d);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 500);
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        repaint();
    }
}
