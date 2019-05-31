package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents an operator in an expression.
 *
 * @author Bruna Dujmović
 *
 */
public class ElementOperator extends Element {

    /**
     * The the symbol that this operator element represents.
     */
    private String symbol;

    /**
     * Constructs an operator element based on the symbol that it represents.
     *
     * @param symbol the symbol that the operator element represents
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol that this operator element represents.
     *
     * @return the symbol that this operator element represents
     */
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String asText() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
