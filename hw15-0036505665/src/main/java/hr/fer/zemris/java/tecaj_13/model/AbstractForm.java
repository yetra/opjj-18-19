package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A base implementation of a form helper class that matches HTML form element. It
 * knows how to handle form data errors, but should be extended to provide getters
 * and setters for specific properties of a given database object.
 *
 * @author Bruna Dujmović
 *
 */
public class AbstractForm {

    /**
     * A map of errors that have occurred during login validation. The keys should
     * be the names of properties where the error occurred, and the values should be
     * error messages.
     */
    private Map<String, String> errors = new HashMap<>();

    /**
     * Returns the error message for the specified property or {@code null} if no such
     * error exists.
     *
     * @param name the name of the property where the error occurred
     * @return the error message or {@code null} if no error exists
     */
    public String getError(String name) {
        return errors.get(name);
    }

    /**
     * Returns {@code true} if errors have occurred during login validation.
     *
     * @return {@code true} if errors have occurred during login validation
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Returns {@code true} if the specified property had an error during login
     * validation.
     *
     * @param name the name of the property where the error occurred
     * @return {@code true} if the specified property has an error
     */
    public boolean hasError(String name) {
        return errors.containsKey(name);
    }

    /**
     * Adds an error for the specified property to this form's {@link #errors} map.
     *
     * @param name the name of the property that has an error
     * @param message the error message
     */
    public void setError(String name, String message) {
        errors.put(name, message);
    }

    /**
     * Validates this form. This method assumes that the form has been previously
     * filled and check the syntactic correctness of all the properties it contains.
     * If a property is invalid, an error will be registered in the {@link #errors}
     * map.
     */
    public void validate() {
        errors.clear();
    }

    /**
     * A helper method for converting {@code null} strings into empty strings.
     *
     * @param s the string to convert
     * @return the given string if it isn't {@code null} or an empty string
     */
    String prepare(String s) {
        return (s == null) ? "" : s.trim();
    }
}
