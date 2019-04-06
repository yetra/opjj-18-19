package hr.fer.zemris.java.hw05.db;

/**
 * This class provides implementations of the {@link IFieldValueGetter} interface to be used
 * while parsing database queries.
 *
 * @author Bruna Dujmović
 *
 */
public class FieldValueGetters {

    /**
     * Returns the first name of the student.
     */
    public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;

    /**
     * Returns the last name of the student.
     */
    public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;

    /**
     * Returns the JMBAG of the student.
     */
    public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
}
