package hr.fer.zemris.java.custom.scripting.exec;

/**
 * An enum of all valid operand types for {@link ValueWrapper} arithmetic operations.
 *
 * @author Bruna Dujmović
 *
 */
enum OperandType {

    /**
     * A double value.
     */
    DOUBLE,

    /**
     * An integer value or {@code null}.
     */
    INTEGER,

    /**
     * If the value cannot be parsed to double or integer, or is of invalid type.
     */
    INVALID
}
