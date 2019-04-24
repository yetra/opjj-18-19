package hr.fer.zemris.java.hw07.observer2;

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
//        demo1();

        demo2();
    }

    private static void demo1() {
        IntegerStorage istorage = new IntegerStorage(20);

        IntegerStorageObserver observer = new SquareValue();
        istorage.addObserver(observer);
        istorage.addObserver(new ChangeCounter());
        istorage.addObserver(new DoubleValue(5));

        istorage.setValue(5);
        istorage.setValue(2);
        istorage.setValue(25);
        istorage.setValue(13);
        istorage.setValue(22);
        istorage.setValue(15);
    }

    private static void demo2() {
        IntegerStorage istorage = new IntegerStorage(20);

        IntegerStorageObserver observer = new SquareValue();
        istorage.addObserver(observer);
        istorage.addObserver(new ChangeCounter());
        istorage.addObserver(new DoubleValue(1));
        istorage.addObserver(new DoubleValue(2));
        istorage.addObserver(new DoubleValue(2));

        istorage.setValue(5);
        istorage.setValue(2);
        istorage.setValue(25);
        istorage.setValue(13);
        istorage.setValue(22);
        istorage.setValue(15);
    }
}
