package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents a variable in an expression.
 *
 * @author Bruna Dujmović
 *
 */
public class ElementVariable extends Element {

    /**
     * The name of the variable element.
     */
    private String name;

    /**
     * Constructs a variable element based on its name.
     *
     * @param name the name of the variable element
     */
    public ElementVariable(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this variable element.
     *
     * @return the name of this variable element
     */
    public String getName() {
        return name;
    }

    @Override
    public String asText() {
        return name;
    }
}
