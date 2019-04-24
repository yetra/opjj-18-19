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
     * The method to execute when a given {@link IntegerStorageChange} occurs.
     *
     * @param change a reference to the {@link IntegerStorageChange} object
     */
     void valueChanged(IntegerStorageChange change);
}