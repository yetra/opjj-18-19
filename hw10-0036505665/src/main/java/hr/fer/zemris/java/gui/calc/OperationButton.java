package hr.fer.zemris.java.gui.calc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * An extension of the {@link JButton} class that performs a mathematical operation
 * when clicked.
 *
 * @author Bruna Dujmović
 *
 */
public class OperationButton extends JButton {

    /**
     * The background color of this button.
     */
    private static final Color BACKGROUND_COLOR = new Color(0xDCDFFE);

    /**
     * The text written on this button.
     */
    String text;

    /**
     * The action listener for button clicks.
     */
    ActionListener listener;

    /**
     * Constructs a new {@link OperationButton} of the given text and action listener.
     *
     * @param text the text of the button
     * @param listener the action listener for button clicks
     */
    public OperationButton(String text, ActionListener listener) {
        this.text = Objects.requireNonNull(text);
        this.listener = Objects.requireNonNull(listener);

        setOpaque(true);
        setBackground(BACKGROUND_COLOR);
        setText(text);
        addActionListener(listener);
    }
}
