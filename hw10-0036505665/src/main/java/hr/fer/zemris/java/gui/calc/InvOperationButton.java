package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * An extension of the {@link OperationButton} class for operation buttons that support
 * inverse operations.
 *
 * @author Bruna Dujmović
 *
 */
public class InvOperationButton extends OperationButton {

    /**
     * A flag for checking whether this button is in inverse operation mode.
     */
    private boolean isInverse = false;

    /**
     * The text written on this button when {@link #isInverse} is {@code true}.
     */
    private String invText;

    /**
     * The listener for button clicks when {@link #isInverse} is {@code true}.
     */
    private ActionListener invListener;

    /**
     * Constructs and {@link InvOperationButton} of the given texts and listeners.
     *
     * @param text the text of the button
     * @param listener the action listener for button clicks
     * @param invText the text of the button when it is in inverse mode
     * @param invListener the action listener for button clicks in inverse mode
     */
    public InvOperationButton(String text, ActionListener listener,
                              String invText, ActionListener invListener) {
        super(text, listener);

        this.invText = Objects.requireNonNull(invText);
        this.invListener = Objects.requireNonNull(invListener);
    }

    /**
     * Switches this button from regular mode to inverse mode and vice versa, based on
     * the state of the {@link #isInverse} flag.
     */
    public void inverse() {
        if (isInverse) {
            removeActionListener(invListener);
            addActionListener(listener);
            setText(text);

        } else {
            removeActionListener(listener);
            addActionListener(invListener);
            setText(invText);
        }

        isInverse = !isInverse;
    }
}
