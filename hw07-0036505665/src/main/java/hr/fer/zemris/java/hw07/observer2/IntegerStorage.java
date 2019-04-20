package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class stores a given integer value accepted through the constructor. It
 * allows the user to (un)register observers that listen for a change in the stored
 * value.
 *
 * @author Bruna Dujmović
 *
 */
public class IntegerStorage {

    /**
     * The value being stored in this {@link IntegerStorage} object.
     */
    private int value;

    /**
     * The list of registered observers.
     */
    private List<IntegerStorageObserver> observers = new ArrayList<>();

    /**
     * Constructs an {@link IntegerStorage} object that will store the given initial
     * value.
     *
     * @param initialValue the initial value to store
     */
    public IntegerStorage(int initialValue) {
        this.value = initialValue;
    }

    /**
     * Adds the given observer to the list of registered observers, if it is not
     * already present.
     *
     * @param observer the observer to add
     * @throws NullPointerException if the given observer is {@code null}
     */
    public void addObserver(IntegerStorageObserver observer) {
        Objects.requireNonNull(observer);

        observers.add(observer);
    }

    /**
     * Removes the given observer from the list of registered observers, if it is
     * present.
     *
     * @param observer the observer to remove
     */
    public void removeObserver(IntegerStorageObserver observer) {
        List<IntegerStorageObserver> observersCopy = new ArrayList<>(observers);
        observersCopy.remove(observer);
        observers = observersCopy;
    }

    /**
     * Clears the list of registered observers.
     */
    public void clearObservers() {
        observers.clear();
    }

    /**
     * Returns the value stored in this {@link IntegerStorage} object.
     *
     * @return the value stored in this {@link IntegerStorage} object
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of this {@link IntegerStorage} object, if it is different than
     * the current value.
     *
     * @param value the value to set
     */
    public void setValue(int value) {
        if (this.value != value) {
            this.value = value;

            observers.forEach((observer) -> observer.valueChanged(this));
        }
    }
}
