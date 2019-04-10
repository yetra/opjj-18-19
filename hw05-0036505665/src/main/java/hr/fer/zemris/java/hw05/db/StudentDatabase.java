package hr.fer.zemris.java.hw05.db;

import java.util.*;

/**
 * This class models a database of student records. It accepts a list of String objects
 * in its constructor. Each string in the given list represents one row of the database.
 *
 * @author Bruna Dujmović
 *
 */
public class StudentDatabase {

    /**
     * A list of all the student records in the database.
     */
    private List<StudentRecord> records = new ArrayList<>();

    /**
     * A index for fast retrieval of student records when jmbag is known.
     */
    private Map<String, StudentRecord> index = new HashMap<>();

    /**
     * Constructs a database by parsing the given rows into student records.
     *
     * @param rows a list of rows which contain student record data
     * @throws NullPointerException if the given list of rows is {@code null}
     * @throws IllegalArgumentException if the given data is invalid or if it already
     *         exists in the database
     */
    public StudentDatabase(List<String> rows) {
        Objects.requireNonNull(rows);

        for (String row : rows) {
            String[] data = row.split("\t+");

            if (data.length != 4) {
                throw new IllegalArgumentException(
                        "Student record must have 4 parts, " + data.length + " were given.");
            }

            int finalGrade = Integer.parseInt(data[3]);
            if (finalGrade < 1 || finalGrade > 5) {
                throw new IllegalArgumentException(
                        "Final grade is not in range [1, 5].");
            }

            String jmbag = data[0];
            if (index.get(jmbag) != null) {
                throw new IllegalArgumentException(
                        "The record " + jmbag + "already exists in the database");
            }

            StudentRecord record = new StudentRecord(jmbag, data[1], data[2], finalGrade);
            records.add(record);
            index.put(jmbag, record);
        }
    }

    /**
     * Returns a student record specified by the given jmbag in O(1). If such a record
     * does not exist in the database, this method will return {@code null}.
     *
     * @param jmbag the jmbag of the student
     * @return a student record specified by the given jmbag or {@code null} if no
     *         such record exists
     * @throws NullPointerException if the given jmbag is {@code null}
     */
    public StudentRecord forJMBAG(String jmbag) {
        Objects.requireNonNull(jmbag);

        return index.get(jmbag);
    }

    /**
     * Returns a list of student records filtered by a given {@link IFilter}.
     *
     * @param filter the filter by which to filter the student records
     * @return a list of student records filtered by the given {@link IFilter}
     * @throws NullPointerException if the given filter is {@code null}
     */
    public List<StudentRecord> filter(IFilter filter) {
        Objects.requireNonNull(filter);

        List<StudentRecord> accepted = new ArrayList<>();

        for (StudentRecord record : records) {
            if (filter.accepts(record)) {
                accepted.add(record);
            }
        }

        return accepted;
    }
}
