package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class models a formatter of student records. It accepts a list of
 * {@link StudentRecord} objects through the constructor and returns a list of
 * strings that represents a table-like formatted output of the given records.
 *
 * @author Bruna Dujmović
 *
 */
public class RecordFormatter {

    /**
     * The maximum length of a JMBAG.
     */
    private int maxJMBAGLength = 0;

    /**
     * The maximum length of a last name.
     */
    private int maxLastNameLength = 0;

    /**
     * The maximum length of a first name.
     */
    private int maxFirstNameLength = 0;

    /**
     * A list of student records to format.
     */
    private List<StudentRecord> records;

    /**
     * A list of formatted student record strings.
     */
    List<String> formatted = new ArrayList<>();

    /**
     * Constructs a {@link RecordFormatter} for the given student records list.
     *
     * @param records the list of student records to format
     * @throws NullPointerException if the given student records list is {@code null}
     */
    public RecordFormatter(List<StudentRecord> records) {
        Objects.requireNonNull(records);

        this.records = records;
        getMaxLengths();
    }

    /**
     * Returns a list of formatted student record strings.
     *
     * @return a list of formatted student record strings
     */
    public List<String> format() {
        if (records.size() == 0) {
            formatted.add("Records selected: 0");
            return formatted;
        }

        StringBuilder sb = new StringBuilder("+=");

        sb.append("=".repeat(maxJMBAGLength)).append("=+=")
            .append("=".repeat(maxLastNameLength)).append("=+=")
            .append("=".repeat(maxFirstNameLength)).append("=+=")
            .append("==+");

        formatted.add(sb.toString());

        for (StudentRecord r : records) {
            formatStudentRecord(r);
        }

        formatted.add(sb.toString());
        formatted.add("Records selected: " + records.size());

        return formatted;
    }

    /**
     * Formats a given student record.
     *
     * @param record the student record to format
     */
    private void formatStudentRecord(StudentRecord record) {
        StringBuilder sb = new StringBuilder("| ");

        int lastNameLength = record.getLastName().length();
        int firstNameLength = record.getFirstName().length();

        sb.append(record.getJmbag())
            .append(" | ")
            .append(record.getLastName())
            .append(" ".repeat(maxLastNameLength - lastNameLength))
            .append(" | ")
            .append(record.getFirstName())
            .append(" ".repeat(maxFirstNameLength - firstNameLength))
            .append(" | ")
            .append(record.getFinalGrade())
            .append(" |");

        formatted.add(sb.toString());
    }

    /**
     * Initializes the {@link #maxFirstNameLength}, {@link #maxLastNameLength} and
     * {@link #maxJMBAGLength} variables.
     */
    private void getMaxLengths() {
        for (StudentRecord r : records) {
            int currentJMBAGLength = r.getJmbag().length();
            int currentLastNameLength = r.getLastName().length();
            int currentFirstNameLength = r.getFirstName().length();

            if (currentJMBAGLength > maxJMBAGLength) {
                maxJMBAGLength = currentJMBAGLength;
            }
            if (currentLastNameLength > maxLastNameLength) {
                maxLastNameLength = currentLastNameLength;
            }
            if (currentFirstNameLength > maxFirstNameLength) {
                maxFirstNameLength = currentFirstNameLength;
            }
        }
    }
}
