package hr.fer.zemris.java.hw17.jvdraw.components;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;

import javax.swing.*;
import java.awt.*;

/**
 * An extension of {@link JLabel} for displaying foreground and background color info.
 *
 * @author Bruna Dujmović
 */
public class JColorLabel extends JLabel {

    /**
     * The foreground color.
     */
    private Color fgColor;

    /**
     * The background color.
     */
    private Color bgColor;

    /**
     * Constructs a new {@link JColorLabel} and registers it as a listener on
     * the given {@link IColorProvider} objects.
     *
     * @param fgColorProvider the foreground color provider
     * @param bgColorProvider the background color provider
     */
    public JColorLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
        fgColor = fgColorProvider.getCurrentColor();
        bgColor = bgColorProvider.getCurrentColor();
        updateLabel();

        fgColorProvider.addColorChangeListener((source, oldColor, newColor) -> {
            fgColor = newColor;
            updateLabel();
        });

        bgColorProvider.addColorChangeListener((source, oldColor, newColor) -> {
            bgColor = newColor;
            updateLabel();
        });
    }

    /**
     * Update's the labels text with the current foreground and background colors.
     */
    private void updateLabel() {
        String text = "Foreground color: " + colorToRGBString(fgColor);
        text += ", background color: " + colorToRGBString(bgColor) + ".";

        setText(text);
    }

    /**
     * Returns a string representation of the given {@link Color}.
     *
     * @param color the color to represent as a string
     * @return a string representation of the given color
     */
    private static String colorToRGBString(Color color) {
        return "(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")";
    }
}
