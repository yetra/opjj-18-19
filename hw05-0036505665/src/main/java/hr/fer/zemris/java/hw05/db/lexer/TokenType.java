package hr.fer.zemris.java.hw05.db.lexer;

/**
 * An enum of all possible token types.
 *
 * @author Bruna Dujmović
 *
 */
public enum TokenType {

    /**
     * End of the input.
     */
    EOF,

    /**
     * A name of a student record attribute.
     */
    FIELD_NAME,

    /**
     * A comparison operator: >, <, >=, <=, =, !=, LIKE.
     */
    COMPARISON_OPERATOR,

    /**
     * A string of characters. Always starts and ends with a quote.
     */
    STRING,

    /**
     * A logical operator: AND. Case-insensitive.
     */
    LOGICAL_OPERATOR
}
