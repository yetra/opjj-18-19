package hr.fer.zemris.java.gui.layouts;

/**
 * An exception to be thrown in the {@link CalcLayout} class.
 *
 * @author Bruna Dujmović
 *
 */
public class CalcLayoutException extends RuntimeException {

    /**
     * Constructs an {@link CalcLayoutException} object.
     */
    public CalcLayoutException() {
    }

    /**
     * Constructs an {@link CalcLayoutException} object of the given message.
     *
     * @param message the message about the exception
     */
    public CalcLayoutException(String message) {
        super(message);
    }
}
