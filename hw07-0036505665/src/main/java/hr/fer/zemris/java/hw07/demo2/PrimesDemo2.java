package hr.fer.zemris.java.hw07.demo2;

/**
 * This program demostrates the {@link PrimesCollection} class.
 */
public class PrimesDemo2 {

    /*
     * "which should produce the following output:
     * Got prime pair: 2, 2
     * Got prime pair: 2, 3
     * Got prime pair: 3, 2
     * Got prime pair: 3, 3"
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(2);

        for(Integer prime : primesCollection) {
            for(Integer prime2 : primesCollection) {
                System.out.println("Got prime pair: " + prime + ", " + prime2);
            }
        }
    }
}
