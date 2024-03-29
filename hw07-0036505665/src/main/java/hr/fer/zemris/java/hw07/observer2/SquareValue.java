package hr.fer.zemris.java.hw07.observer2;

/**
 * This {@link IntegerStorageObserver} implementation prints the square of the integer
 * stored in the {@link IntegerStorage} object to the standard output. The stored
 * integer itself is not modified.
 *
 * @author Bruna Dujmović
 *
 */
public class SquareValue implements IntegerStorageObserver {

    @Override
    public void valueChanged(IntegerStorageChange change) {
        int value = change.getNewValue();

        System.out.format("Provided new value: %d, square is %d%n", value, value*value);
    }
}
