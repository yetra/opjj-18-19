package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * An enumeration of {@link SmartScriptLexer} token types.
 */
public enum SmartScriptTokenType {

    /**
     * End of the input.
     */
    EOF,

    /**
     * A name is a valid variable, function or tag name.
     */
    NAME,

    /**
     * A mathematical operator: "+" (plus), "-" (minus), "*" (multiplication), "/"
     * (division), "^" (power).
     */
    OPERATOR,

    /**
     * A string which can contain escapings. Each {@link SmartScriptToken} string
     * token must begin and end with a quotation mark.
     */
    STRING,

    /**
     * An integer or negative integer (represented in -digit or digit form).
     */
    INTEGER,

    /**
     * A decimal number (represented in digit-dot-digit form).
     */
    DECIMAL,

    /**
     * The start of the tag: {$.
     */
    TAG_START,

    /**
     * The end of the tag: $}.
     */
    TAG_END
}