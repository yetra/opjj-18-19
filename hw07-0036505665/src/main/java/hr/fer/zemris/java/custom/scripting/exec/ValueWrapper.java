package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BinaryOperator;

/**
 * This class represents a wrapper of a given object value. It supports arithmetic
 * operations on values that can be parsed to {@link Double} or {@link Integer}.
 *
 * @author Bruna Dujmović
 * 
 */
public class ValueWrapper {

    /**
     * The value object that is wrapped by this {@link ValueWrapper}.
     */
    private Object value;

    /**
     * Constructs a {@link ValueWrapper} for the given value.
     *
     * @param value the value object to wrap
     */
    public ValueWrapper(Object value) {
        this.value = value;
    }

    /**
     * Returns the value that is wrapped by this {@link ValueWrapper}.
     *
     * @return the value that is wrapped by this {@link ValueWrapper}
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value wrapped by this {@link ValueWrapper} to the given new value.
     *
     * @param value the new value to wrap
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Adds this wrapper's value with a given value and stores the result.
     *
     * @param incValue the value to add with
     * @throws RuntimeException if the operation cannot be performed on the given operands
     */
    public void add(Object incValue) {
        performOperation(incValue, Operations.ADD);
    }

    /**
     * Subtracts a given value from this wrapper's value and stores the result.
     *
     * @param decValue the value to subtract
     * @throws RuntimeException if the operation cannot be performed on the given operands
     */
    public void subtract(Object decValue) {
        performOperation(decValue, Operations.SUB);
    }

    /**
     * Multiplies this wrapper's value with a given value and stores the result.
     *
     * @param mulValue the value to multiply with
     * @throws RuntimeException if the operation cannot be performed on the given operands
     */
    public void multiply(Object mulValue) {
        performOperation(mulValue, Operations.MUL);
    }

    /**
     * Divides this wrapper's value with a given value and stores the result.
     *
     * @param divValue the value to divide with
     * @throws RuntimeException if the operation cannot be performed on the given operands
     */
    public void divide(Object divValue) {
        performOperation(divValue, Operations.DIV);
    }

    /**
     * Compares this wrapper's value with a given value.
     *
     * This method returns an integer less than zero if the currently stored value
     * is smaller than the argument, an integer greater than zero if the currently
     * stored value is larger than the argument, or zero if they are equal.
     *
     * @param withValue the value to compare with
     * @return zero if the values are equal, less than zero if this value is smaller,
     *         greater than zero if the given value is larger than this value
     * @throws RuntimeException if the operation cannot be performed on the given operands
     */
    public int numCompare(Object withValue) {
        if (typeOf(this.value) == OperandType.INTEGER
                && typeOf(withValue) == OperandType.INTEGER) {
            // 2 ints, 1 null & 1 int, 2 nulls
            return Integer.compare(toDouble(this.value).intValue(), toDouble(this.value).intValue());
        } else {
             // 2 doubles
            return Double.compare(toDouble(this.value), toDouble(withValue));
        }
    }

    /**
     * Performs an operation specified by a {@link BinaryOperator} on this wrapper's
     * value and a given value.
     *
     * @param value the second operand of the operation
     * @param operation the {@link BinaryOperator} to apply on the operands
     * @throws RuntimeException if the operation cannot be performed on the given operands
     */
    private void performOperation(Object value, BinaryOperator<Double> operation) {
        Double result = operation.apply(toDouble(this.value), toDouble(value));

        if (typeOf(this.value) == OperandType.INTEGER
                && typeOf(value) == OperandType.INTEGER) {
            this.value = result.intValue();
        } else {
            this.value = result;
        }
    }

    /**
     * Converts a given object to {@link Double}.
     *
     * @param value the value to convert
     * @return the {@link Double} representation of the given object
     * @throws IllegalArgumentException if the given object's type is not appropriate
     * @throws NumberFormatException if the given object is a string and it cannot be
     *         parsed to a {@link Double}
     */
    private Double toDouble(Object value) {
        if (value == null) {
            return 0.0;
        } else if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value instanceof String) {
            return Double.parseDouble((String) value);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the operand type of a given object.
     *
     * @param value the object to check
     * @return the operand type of a given object
     */
    private OperandType typeOf(Object value) {
        if (value == null || value instanceof Integer) {
            return OperandType.INTEGER;
        }
        if (value instanceof Double) {
            return OperandType.DOUBLE;
        }

        if (value instanceof String) {
            String valueString = (String) value;
            if (valueString.matches("^\\d+$")) {
                return OperandType.INTEGER;
            } else if (valueString.matches("^[+-]?\\d+(\\.\\d+)?(E-?\\d+)?$")) {
                return OperandType.DOUBLE;
            } else {
                throw new IllegalArgumentException("Invalid string format!");
            }
        } else {
            throw new IllegalArgumentException("Invalid value type!");
        }
    }
}
