package hr.fer.zemris.java.hw17.jvdraw.components;

import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom color picker component.
 *
 * @author Bruna Dujmović
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

    /**
     * The selected color.
     */
    private Color selectedColor;

    /**
     * A list of registered {@link ColorChangeListener} objects.
     */
    private List<ColorChangeListener> listeners = new ArrayList<>();

    /**
     * Constructs a {@link JColorArea} of the given color.
     *
     * @param selectedColor the selected color
     */
    public JColorArea(Color selectedColor) {
        this.selectedColor = selectedColor;

        this.addMouseListener(new ChooseColorListener());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(15, 15);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Color getCurrentColor() {
        return selectedColor;
    }

    @Override
    public void addColorChangeListener(ColorChangeListener l) {
        listeners.add(l);
    }

    @Override
    public void removeColorChangeListener(ColorChangeListener l) {
        listeners.remove(l);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Dimension dimension = getSize();
        Insets insets = getInsets();

        g2d.setColor(selectedColor);
        g2d.fillRect(insets.left, insets.top,
                dimension.width, dimension.height);
    }

    /**
     * Listens for the user's mouse click and displays a
     * {@link JColorChooser} for selecting a new color.
     */
    private class ChooseColorListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            Color newColor = JColorChooser.showDialog(
                    JColorArea.this, "Choose new color", selectedColor);

            if (newColor != null) {
                listeners.forEach(l -> l.newColorSelected(
                        JColorArea.this, selectedColor, newColor));

                selectedColor = newColor;
                repaint();
            }
        }
    }
}
