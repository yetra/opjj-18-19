package hr.fer.zemris.java.hw05.db;

/**
 * This functional interface represents a filter that can determine if a given
 * {@link StudentRecord} is acceptable based on some condition.
 *
 * @author Bruna Dujmović
 */
@FunctionalInterface
public interface IFilter {

    /**
     * Returns {@code true} if the given record is accepted by this filter.
     *
     * @param record the record to accept
     * @return {@code true} if the given record is accepted by this filter
     */
    public boolean accepts(StudentRecord record);
}
