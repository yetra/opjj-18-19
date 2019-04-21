package hr.fer.zemris.java.custom.scripting.exec;

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

    }

    public void subtract(Object decValue) {

    }

    public void multiply(Object mulValue) {

    }

    public void divide(Object divValue) {

    }

    public int numCompare(Object withValue) {
        return 0;
    }
}
