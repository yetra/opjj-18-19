package hr.fer.zemris.java.hw05.db;

/**
 * This functional interface is used for comparing two {@link String} values.
 *
 * @author Bruna Dujmović
 * 
 */
@FunctionalInterface
public interface IComparisonOperator {

    /**
     * Returns {@code true} if the given values satisfy a specified comparison.
     *
     * @param value1 the first value to compare
     * @param value2 the second value to compare
     * @return {@code true} if the given values satisfy a specified comparison
     */
    public boolean satisfied(String value1, String value2);
}
