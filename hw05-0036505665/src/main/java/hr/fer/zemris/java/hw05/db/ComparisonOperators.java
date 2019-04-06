package hr.fer.zemris.java.hw05.db;

/**
 * This class provides implementations of the {@link IComparisonOperator} interface to be used
 * while parsing database queries.
 *
 * @author Bruna Dujmović
 * 
 */
public class ComparisonOperators {

    /**
     * Returns {@code} true if the value of the first string is less than the value of the second
     * string.
     */
    public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;

    /**
     * Returns {@code} true if the value of the first string is less than or equal to the value of
     * the second string.
     */
    public static final IComparisonOperator LESS_OR_EQUAL = (v1, v2) -> v1.compareTo(v2) <= 0;

    /**
     * Returns {@code} true if the value of the first string is greater than the value of the
     * second string.
     */
    public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;

    /**
     * Returns {@code} true if the value of the first string is greater or equal to the value
     * of the second string.
     */
    public static final IComparisonOperator GREATER_OR_EQUAL = (v1, v2) -> v1.compareTo(v2) >= 0;

    /**
     * Returns {@code} true if the value of the first string is equal to the value of the second
     * string.
     */
    public static final IComparisonOperator EQUALS = (v1, v2) -> v1.equals(v2);

    /**
     * Returns {@code} true if the value of the first string is not equal to the value of the
     * second string.
     */
    public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> !v1.equals(v2);

    /**
     * Returns {@code} true if the value of the first string is like the value of the second
     * string - specifically, if the first string has the same start and/or end as the second
     * string.
     */
    public static final IComparisonOperator LIKE = (v1, v2) -> {
        String[] parts = v2.split("\\*");

        if (parts.length != 2) {
            throw new IllegalArgumentException(
                    "Too many wildcards in LIKE comparison.");
        }

        return v1.startsWith(parts[0]) && v1.endsWith(parts[1]);
    };
}
