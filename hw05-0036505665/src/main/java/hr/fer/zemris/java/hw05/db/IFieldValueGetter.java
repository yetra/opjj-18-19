package hr.fer.zemris.java.hw05.db;

/**
 * This functional interface can return a specified field value from a student record in the
 * database.
 *
 * @author Bruna Dujmović
 *
 */
@FunctionalInterface
public interface IFieldValueGetter {

    /**
     * Returns a requested field value from a given student record.
     *
     * @param record the record from which the value will be returned
     * @return a requested field value from a given student record
     */
    public String get(StudentRecord record);
}
