package hr.fer.zemris.java.hw07.observer1;

import java.util.List;

public class IntegerStorage {

    private int value;

    private List<IntegerStorageObserver> observers; // use ArrayList here!!!

    public IntegerStorage(int initialValue) {
        this.value = initialValue;
    }

    public void addObserver(IntegerStorageObserver observer) {
        // add the observer in observers if not already there ... // ... your code ...
    }

    public void removeObserver(IntegerStorageObserver observer) {
        // remove the observer from observers if present ...
        // ... your code ...
    }

    public void clearObservers() {
        // remove all observers from observers list ... // ... your code ...
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        // Only if new value is different than the current value: if(this.value!=value) {
    }
}
