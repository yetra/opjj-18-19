package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents a constant double in an expression.
 *
 * @author Bruna Dujmović
 *
 */
public class ElementConstantDouble extends Element {

    /**
     * The value of the constant double element.
     */
    private double value;

    /**
     * Constructs a constant double element based on its value.
     *
     * @param value the value of the constant double element
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Returns the value of this constant double element.
     *
     * @return the value of this constant double element
     */
    public double getValue() {
        return value;
    }

    @Override
    public String asText() {
        return Double.toString(value);
    }

    @Override
    public String toString() {
        return asText();
    }
}
