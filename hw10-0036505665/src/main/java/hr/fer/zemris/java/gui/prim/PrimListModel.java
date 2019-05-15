package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a model of a list containing prime numbers. The prime numbers
 * are generated by calling the {@link #next()} method. Upon object creation, only the
 * number 1 is stored in the model.
 *
 * @author Bruna Dujmović
 *
 */
public class PrimListModel implements ListModel<Integer> {
    /**
     * The underlying collection of primes.
     */
    private List<Integer> primes;

    /**
     * A list of listeners for changes in this model.
     */
    private List<ListDataListener> listeners;

    /**
     * The last generated prime.
     */
    private int currentPrime;

    /**
     * Constructs and initializes a {@link PrimListModel} object.
     */
    public PrimListModel() {
        primes = new ArrayList<>();
        listeners = new ArrayList<>();

        primes.add(1);
    }

    /**
     * Generates the next prime number and adds it to this model's underlying primes
     * collection.
     */
    public void next() {
        do {
            currentPrime++;
        } while (!isPrime(currentPrime));

        int position = primes.size();
        primes.add(currentPrime);

        ListDataEvent event = new ListDataEvent(
                this, ListDataEvent.INTERVAL_ADDED, position, position
        );

        listeners.forEach(l -> l.intervalAdded(event));
    }

    @Override
    public int getSize() {
        return primes.size();
    }

    @Override
    public Integer getElementAt(int index) {
        if (index < 0 || index >= primes.size()) {
            throw new ArrayIndexOutOfBoundsException(
                    "Model does not contain that many primes!");
        }

        return primes.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    /**
     * Returns {@code true} if the given number is a prime number.
     *
     * @param number the number to check
     * @return {@code true} if the given number is a prime number
     */
    private boolean isPrime(int number) {
        if (number <= 3) {
            return number > 1;
        } else if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }

        for (int i = 5; i*i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }
}
