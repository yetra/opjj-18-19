package hr.fer.zemris.java.hw07.observer2;

/**
 * This {@link IntegerStorageObserver} implementation prints to the standard output the
 * doubled (i.e. value * 2) value of a given {@link IntegerStorage} object's stored
 * value but only for a limited number of times specified in the constructor.
 *
 * After printing the double value for the n-th time, this observer will automatically
 * de-register itself from the subject.
 *
 * @author Bruna Dujmović
 *
 */
public class DoubleValue implements IntegerStorageObserver {

    /**
     * The number of times to double a given {@link IntegerStorage}'s value before
     * deregistration.
     */
    private int timeUntilDeregistration;

    /**
     * Constructs a {@link DoubleValue} object that will print the doubled stored value
     * for a specified number of times before deregistration.
     *
     * @param timeUntilDeregistration the number of times to print until deregistration
     */
    public DoubleValue(int timeUntilDeregistration) {
        this.timeUntilDeregistration = timeUntilDeregistration;
    }

    @Override
    public void valueChanged(IntegerStorageChange change) {
        if (timeUntilDeregistration < 1) {
            change.getIntegerStorage().removeObserver(this);
        }

        System.out.println("Double value: " + change.getNewValue() * 2);
        timeUntilDeregistration--;
    }
}
