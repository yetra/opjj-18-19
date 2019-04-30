package searching.slagalica;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a {@link Slagalica} configuration. It is constructed from an
 * integer array of values 0-9, where 0 represents the empty space in the configuration.
 *
 * @author Bruna Dujmović
 *
 */
public class KonfiguracijaSlagalice {

    /**
     * The array representing this configuration.
     */
    private int[] polje;

    /**
     * Creates a {@link KonfiguracijaSlagalice} object based on a given integer array.
     *
     * @param polje the array representing this configuration
     */
    public KonfiguracijaSlagalice(int[] polje) {
        this.polje = checkPolje(polje);
    }

    /**
     * Returns the array representing this configuration.
     *
     * @return the array representing this configuration
     */
    public int[] getPolje() {
        return polje;
    }

    /**
     * Returns the index of the space in this configuration, or -1 if there is no space.
     *
     * @return the index of the space in this configuration, or -1 if there is no space
     */
    public int indexOfSpace() {
        for (int i = 0; i < polje.length; i++) {
            if (polje[i] == 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Checks if the given integer array is a valid representation of a configuration.
     *
     * @param polje the array to check
     * @throws IllegalArgumentException if the array has illegal values, is of wrong
     *         length, or doesn't contain an empty space
     */
    private int[] checkPolje(int[] polje) {
        Objects.requireNonNull(polje);
        if (polje.length != 9) {
            throw new IllegalArgumentException("Array length must be equal to 9!");
        }

        boolean hasSpace = false;
        for (int value : polje) {
            if (value < 0 || value > 8) {
                throw new IllegalArgumentException(
                        "Invalid configuration value " + value + ".");
            }

            if (value == 0) {
                hasSpace = true;
            }
        }

        if (!hasSpace) {
            throw new IllegalArgumentException(
                    "Configuration must have an empty space!");
        }

        return polje;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            sb.append(polje[i] == 0 ? "*" : polje[i]);
            sb.append(" ");

            if ((i + 1) % 3 == 0) {
                sb.append("\n");
            }
        }

        return sb.substring(0, sb.length() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KonfiguracijaSlagalice that = (KonfiguracijaSlagalice) o;
        return Arrays.equals(polje, that.polje);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(polje);
    }
}
