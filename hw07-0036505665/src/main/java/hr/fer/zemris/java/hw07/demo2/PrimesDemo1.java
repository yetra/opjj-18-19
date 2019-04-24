package hr.fer.zemris.java.hw07.demo2;

/**
 * This program demostrates the {@link PrimesCollection} class.
 */
public class PrimesDemo1 {

    /*
     * "The previous snippet should produce output:
     * Got prime: 2
     * Got prime: 3
     * Got prime: 5
     * Got prime: 7
     * Got prime: 11"
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them

        for(Integer prime : primesCollection) {
            System.out.println("Got prime: " + prime);
        }
    }
}
