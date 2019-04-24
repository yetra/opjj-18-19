package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BinaryOperator;

/**
 * This class represents a wrapper of a given object value. It supports arithmetic
 * operations on values that can be parsed to {@code double} or {@code int}.
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

    public void add(Object incValue) {
        performOperation(incValue, Operations.ADD);
    }

    public void subtract(Object decValue) {
        performOperation(decValue, Operations.SUB);
    }

    public void multiply(Object mulValue) {
        performOperation(mulValue, Operations.MUL);
    }

    public void divide(Object divValue) {
        performOperation(divValue, Operations.DIV);
    }

    public int numCompare(Object withValue) {
        if (typeOf(this.value) == OperatorType.INTEGER
                && typeOf(withValue) == OperatorType.INTEGER) {
            // 2 ints, 1 null & 1 int, 2 nulls
            return Integer.compare(toDouble(this.value).intValue(), toDouble(this.value).intValue());
        } else {
             // 2 doubles
            return Double.compare(toDouble(this.value), toDouble(withValue));
        }
    }

    private void performOperation(Object value, BinaryOperator<Double> operation) {
        Double result = operation.apply(toDouble(this.value), toDouble(value));

        if (typeOf(this.value) == OperatorType.INTEGER
                && typeOf(value) == OperatorType.INTEGER) {
            this.value = result.intValue();
        } else {
            this.value = result;
        }
    }

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

    private OperatorType typeOf(Object value) {
        if (value == null || value instanceof Integer) {
            return OperatorType.INTEGER;
        }
        if (value instanceof Double) {
            return OperatorType.DOUBLE;
        }

        if (value instanceof String) {
            String valueString = (String) value;
            if (valueString.matches("^\\d+$")) {
                return OperatorType.INTEGER;
            } else if (valueString.matches("^[+-]?\\d+(\\.\\d+)?(E-?\\d+)?$")) {
                return OperatorType.DOUBLE;
            } else {
                throw new IllegalArgumentException("Invalid string format!");
            }
        } else {
            throw new IllegalArgumentException("Invalid value type!");
        }
    }
}
