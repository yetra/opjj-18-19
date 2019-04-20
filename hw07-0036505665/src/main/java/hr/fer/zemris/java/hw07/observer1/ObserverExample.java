package hr.fer.zemris.java.hw07.observer1;

/**
 * Demonstrates the {@link IntegerStorage} class and the implementations of the
 * {@link IntegerStorageObserver} interface.
 */
public class ObserverExample {

    /**
     * Main method. Calls the demos.
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {
        demo1();

//        demo2();
    }

    /*
     * "The output of the previous code should be as follows:
     * Provided new value: 5, square is 25
     * Provided new value: 2, square is 4
     * Provided new value: 25, square is 625
     * Number of value changes since tracking: 1
     * Double value: 26
     * Number of value changes since tracking: 2
     * Double value: 44
     * Number of value changes since tracking: 3
     * Double value: 30"
     */
    private static void demo1() {
        IntegerStorage istorage = new IntegerStorage(20);

        IntegerStorageObserver observer = new SquareValue();

        istorage.addObserver(observer);
        istorage.setValue(5);
        istorage.setValue(2);
        istorage.setValue(25);

        istorage.removeObserver(observer);

        istorage.addObserver(new ChangeCounter());
        istorage.addObserver(new DoubleValue(5));
        istorage.setValue(13);
        istorage.setValue(22);
        istorage.setValue(15);
    }

    /*
     * Should not throw ConcurrentModificationException.
     */
    private static void demo2() {
        IntegerStorage istorage = new IntegerStorage(20);

        IntegerStorageObserver observer = new SquareValue();

        istorage.addObserver(observer);
        istorage.setValue(5);
        istorage.setValue(2);
        istorage.setValue(25);

        istorage.removeObserver(observer);

        istorage.addObserver(new ChangeCounter());
        istorage.addObserver(new DoubleValue(1));
        istorage.addObserver(new DoubleValue(2));
        istorage.addObserver(new DoubleValue(2));
        istorage.setValue(13);
        istorage.setValue(22);
        istorage.setValue(15);
    }
}
