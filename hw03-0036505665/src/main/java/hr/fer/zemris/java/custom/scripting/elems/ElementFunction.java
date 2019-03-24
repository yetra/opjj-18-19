package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents a function in an expression.
 *
 * @author Bruna Dujmović
 *
 */
public class ElementFunction extends Element {

    /**
     * The value of the string element.
     */
    private String name;

    /**
     * Constructs a function element based on its name.
     *
     * @param name the name of the function element
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this function element.
     *
     * @return the name of this function element
     */
    public String getName() {
        return name;
    }

    @Override
    public String asText() {
        return name;
    }
}
