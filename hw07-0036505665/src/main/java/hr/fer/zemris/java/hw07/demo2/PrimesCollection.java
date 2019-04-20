package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents a collection of prime numbers. The primes are not stored
 * internally, but generated on an as-needed basis during iteration.
 *
 * @author Bruna Dujmović
 *
 */
public class PrimesCollection implements Iterable<Integer> {

    /**
     * The size of this collection (the number of primes to generate).
     */
    private int size;

    /**
     * Constructs a new {@link PrimesCollection} of the given size.
     *
     * @param size the size of the collection
     * @throws IllegalArgumentException if the given size is less than 1
     */
    public PrimesCollection(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size cannot be less than 1.");
        }
        this.size = size;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new PrimesIterator();
    }

    /**
     * This {@link Iterator} implementation returns the next prime number.
     */
    private class PrimesIterator implements Iterator<Integer> {

        /**
         * The current prime number.
         */
        private int currentPrime;

        /**
         * The number of generated primes.
         */
        private int generatedPrimes = 0;

        @Override
        public boolean hasNext() {
            return generatedPrimes < size;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            do {
                currentPrime++;
            } while (!isPrime(currentPrime));

            generatedPrimes++;
            return currentPrime;
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
}
