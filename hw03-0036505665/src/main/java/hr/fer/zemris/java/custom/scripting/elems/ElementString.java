package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents a string in an expression.
 *
 * @author Bruna Dujmović
 *
 */
public class ElementString extends Element {

    /**
     * The value of the string element.
     */
    private String value;

    /**
     * Constructs a string element based on its value.
     *
     * @param value the value of the string element
     */
    public ElementString(String value) {
        this.value = value;
    }

    /**
     * Returns the value of this string element.
     *
     * @return the value of this string element
     */
    public String getValue() {
        return value;
    }

    @Override
    public String asText() {
        return value;
    }
}
