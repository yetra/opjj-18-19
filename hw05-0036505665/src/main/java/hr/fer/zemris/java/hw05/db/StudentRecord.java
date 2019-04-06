package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class models a student record in the database.
 *
 * @author Bruna Dujmović
 *
 */
public class StudentRecord {

    /**
     * The JMBAG of the student.
     */
    private String jmbag;

    /**
     * The last name of the student.
     */
    private String lastName;

    /**
     * The first name of the student.
     */
    private String firstName;

    /**
     * The final grade of the student. An integer between 1 and 5.
     */
    private int finalGrade;


    /**
     * Returns the he JMBAG of the student.
     *
     * @return the JMBAG of the student
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Returns the last name of the student.
     *
     * @return the last name of the student.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the first name of the student.
     *
     * @return the first name of the student.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the final grade of the student.
     *
     * @return the final grade of the student.
     */
    public int getFinalGrade() {
        return finalGrade;
    }

    /**
     * Constructs a student record with the given data.
     *
     * @param jmbag the jmbag of the student
     * @param lastName the last name of the student
     * @param firstName the first name of the student
     * @param finalGrade the final grade of the student
     * @throws NumberFormatException if the given jmbag, last name or first name are {@code null}
     * @throws IllegalArgumentException if the given final grade is not in range [1, 5]
     */
    public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
        Objects.requireNonNull(jmbag);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(firstName);

        if (finalGrade < 1 || finalGrade > 5) {
            throw new IllegalArgumentException("Final grade is not in range [1, 5].");
        }

        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return jmbag.equals(that.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }
}
