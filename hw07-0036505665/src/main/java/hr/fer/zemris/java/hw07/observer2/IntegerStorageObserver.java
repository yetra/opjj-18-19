package hr.fer.zemris.java.hw07.observer2;

/**
 * This class models an observer that listens for a change in an {@link IntegerStorage}
 * object's stored value.
 *
 * @author Bruna Dujmović
 *
 */
public interface IntegerStorageObserver {

    /**
     * The method to execute when a given {@link IntegerStorage} object's stored
     * value changes.
     *
     * @param istorage the {@link IntegerStorage} object whose stored value changed
     */
    public void valueChanged(IntegerStorage istorage);
}