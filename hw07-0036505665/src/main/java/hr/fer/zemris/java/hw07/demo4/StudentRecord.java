package hr.fer.zemris.java.hw07.demo4;

import java.util.Objects;

/**
 * This class represents a student record.
 *
 * @author Bruna Dujmović
 * 
 */
public class StudentRecord {

    /**
     * The jmbag of the student.
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
     * The midterm exam score of the student.
     */
    private int midtermScore;

    /**
     * The final exam score of the student.
     */
    private int finalScore;

    /**
     * The lab score of the student.
     */
    private int labScore;

    /**
     * The grade of the student.
     */
    private int grade;

    /**
     * Constructs a new {@link StudentRecord} given all the student data.
     *
     * @param jmbag the jmbag of the student
     * @param lastName the last name of the student
     * @param firstName the first name of the student
     * @param midtermScore the midterm exam score of the student
     * @param finalScore the final exam score of the student
     * @param labScore the lab score of the student
     * @param grade the grade of the student
     * @throws NullPointerException if the given jmbag, last name, or first name are
     *         {@code null}
     * @throws IllegalArgumentException if the given scores or grade are negative
     */
    public StudentRecord(String jmbag, String lastName, String firstName,
                         int midtermScore, int finalScore, int labScore, int grade) {
        if (midtermScore < 0 || finalScore < 0 || labScore < 0 || grade < 0) {
            throw new IllegalArgumentException("Scores and grade cannot be negative.");
        }

        this.jmbag = Objects.requireNonNull(jmbag);
        this.lastName = Objects.requireNonNull(lastName);
        this.firstName = Objects.requireNonNull(firstName);
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
        this.labScore = labScore;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return String.format(
                "%s\t%s\t%s\t%d\t%d\t%d\t%s",
                jmbag, lastName, firstName, midtermScore, finalScore, labScore, grade
        );
    }
}
