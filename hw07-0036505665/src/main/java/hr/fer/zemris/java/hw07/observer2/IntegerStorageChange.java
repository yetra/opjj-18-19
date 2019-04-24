package hr.fer.zemris.java.hw07.observer2;

/**
 * This class models a change of a {@link IntegerStorage} object's stored value.
 *
 * @author Bruna Dujmović
 */
public class IntegerStorageChange {

    /**
     * A reference to the {@link IntegerStorage} object whose stored value has changed.
     */
    private IntegerStorage istorage;

    /**
     * The value of the stored integer before the change occurred.
     */
    private int oldValue;

    /**
     * The new value of the stored integer.
     */
    private int newValue;

    /**
     * Constructs a new {@link IntegerStorageChange} object given a reference to a
     * {@link IntegerStorage} object, and the old and new values of the stored integer.
     *
     * @param istorage a reference to the {@link IntegerStorage} object whose stored
     *                 value has changed
     * @param oldValue the value of the stored integer before the change occurred
     * @param newValue the new value of the stored integer
     */
    public IntegerStorageChange(IntegerStorage istorage, int oldValue, int newValue) {
        this.istorage = istorage;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Returns the reference to the {@link IntegerStorage} object whose value changed.
     *
     * @return the reference to the {@link IntegerStorage} object whose value changed
     */
    public IntegerStorage getIntegerStorage() {
        return istorage;
    }

    /**
     * Returns the old value of the stored integer.
     *
     * @return the old value of the stored integer
     */
    public int getOldValue() {
        return oldValue;
    }

    /**
     * Returns the new value of the stored integer.
     *
     * @return the new value of the stored integer
     */
    public int getNewValue() {
        return newValue;
    }
}
