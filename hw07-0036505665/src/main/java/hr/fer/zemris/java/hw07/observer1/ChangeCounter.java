package hr.fer.zemris.java.hw07.observer1;

/**
 * This {@link IntegerStorageObserver} implementation counts (and prints to the
 * standard output) the number of times that a given {@link IntegerStorage} object's
 * stored value has been changed since this observer's registration.
 *
 * @author Bruna Dujmović
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

    /**
     * The internal counter of stored value changes.
     */
    private int changeCounter = 0;

    @Override
    public void valueChanged(IntegerStorage istorage) {
        changeCounter++;
        
        System.out.println("Number of value changes since tracking: " + changeCounter);
    }
}
