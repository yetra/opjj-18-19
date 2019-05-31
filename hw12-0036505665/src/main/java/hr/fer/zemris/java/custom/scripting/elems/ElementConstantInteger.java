package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents a constant integer in an expression.
 *
 * @author Bruna Dujmović
 *
 */
public class ElementConstantInteger extends Element {

    /**
     * The value of the constant integer element.
     */
    private int value;

    /**
     * Constructs a constant integer element based on its value.
     *
     * @param value the value of the constant integer element
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Returns the value of this constant integer element.
     *
     * @return the value of this constant integer element
     */
    public int getValue() {
        return value;
    }

    @Override
    public String asText() {
        return Integer.toString(value);
    }

    @Override
    public String toString() {
        return asText();
    }
}
