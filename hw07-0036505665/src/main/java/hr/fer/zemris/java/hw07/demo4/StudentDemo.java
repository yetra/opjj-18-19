package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This program processes a given file of student data using Java's Stream API and
 * prints the results to the console.
 *
 * @author Bruna Dujmović
 *
 */
public class StudentDemo {

    /**
     * Main method. Processes the given file of student data and prints the results
     * to the console.
     *
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"));
            List<StudentRecord> records = convert(lines);

            System.out.println("Zadatak 1\n=========");
            System.out.println(vratiBodovaViseOd25(records));

            System.out.println("Zadatak 2\n=========");
            System.out.println(vratiBrojOdlikasa(records));

            System.out.println("Zadatak 3\n=========");
            vratiListuOdlikasa(records).forEach(System.out::println);

            System.out.println("Zadatak 4\n=========");
            vratiSortiranuListuOdlikasa(records).forEach(System.out::println);

            System.out.println("Zadatak 5\n=========");
            vratiPopisNepolozenih(records).forEach(System.out::println);

            System.out.println("Zadatak 6\n=========");
            razvrstajStudentePoOcjenama(records).forEach(
                    (k, v) -> System.out.println(k + ": " + v)
            );

            System.out.println("Zadatak 7\n=========");
            vratiBrojStudenataPoOcjenama(records).forEach(
                    (k, v) -> System.out.println(k + ": " + v)
            );

            System.out.println("Zadatak 8\n=========");
            razvrstajProlazPad(records).forEach(
                    (k, v) -> System.out.println(k + ": " + v)
            );

        } catch (IOException e) {
            System.out.println("Cannot read the given file.");
            System.exit(1);

        } catch (NumberFormatException e) {
            System.out.println("Cannot parse the given file.");
            System.exit(1);
        }
    }

    /**
     * Converts a given list of lines to a list of {@link StudentRecord} objects.
     *
     * @param lines the list of lines to convert
     * @return a list of {@link StudentRecord} objects
     * @throws IllegalArgumentException if the string argument cannot be properly parsed
     *         or if the value of the string is invalid
     * @throws ArrayIndexOutOfBoundsException if the given string does not contain the
     *         appropriate amount of data
     */
    private static List<StudentRecord> convert(List<String> lines) {
        List<StudentRecord> records = new ArrayList<>(lines.size());

        try {
            for (String line : lines) {
                String[] parts = line.split("\t");

                records.add(new StudentRecord(
                        parts[0], parts[1], parts[2],
                        Double.parseDouble(parts[3]), Double.parseDouble(parts[4]),
                        Double.parseDouble(parts[5]), Integer.parseInt(parts[6])
                ));
            }
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid record input.");
            System.exit(1);
        }

        return records;
    }

    /**
     * Returns the number of students whose total score (midterm + final + lab) is
     * greater than 25 points.
     *
     * @param records the list of student records to filter
     * @return the number of students whose total score is greater than 25 points
     */
    private static long vratiBodovaViseOd25(List<StudentRecord> records) {

        return records.stream()
                .filter((student) -> student.getTotalScore() > 25)
                .count();
    }

    /**
     * Returns the number of student whose grade is equal to 5.
     *
     * @param records the list of student records to filter
     * @return the number of student whose grade is equal to 5
     */
    private static long vratiBrojOdlikasa(List<StudentRecord> records) {

        return records.stream()
                .filter((student) -> student.getGrade() == 5)
                .count();
    }

    /**
     * Returns a list of students records for students whose grade is equal to 5.
     *
     * @param records the list of student records to filter
     * @return a list of students records for students whose grade is equal to 5
     */
    private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {

        return records.stream()
                .filter((student) -> student.getGrade() == 5)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of students records for students whose grade is equal to 5
     * sorted by their final score (descending).
     *
     * @param records the list of student records to filter
     * @return a sorted list of students records for students whose grade is equal to 5
     */
    private static List<StudentRecord> vratiSortiranuListuOdlikasa(
            List<StudentRecord> records) {

        return records.stream()
                .filter((s) -> s.getGrade() == 5)
                .sorted(Comparator.comparing(StudentRecord::getTotalScore).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of jmbags of students who have not passed the class.
     *
     * @param records the list of student records to filter
     * @return a list of jmbags of students who have not passed the class
     */
    private static List<String> vratiPopisNepolozenih(
            List<StudentRecord> records) {

        return records.stream()
                .filter((student) -> student.getGrade() == 1)
                .map(StudentRecord::getJmbag)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Returns a map of student record lists grouped by grades.
     *
     * @param records the list of student records to filter
     * @return a map of student record lists grouped by grades
     */
    private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(
            List<StudentRecord> records) {

        return records.stream()
                .collect(Collectors.groupingBy(StudentRecord::getGrade));
    }

    /**
     * Returns a map of the number of students who have got a certain grade,
     * grouped by that grade.
     *
     * @param records the list of student records to filter
     * @return a map of the number of students who have got a certain grade, grouped
     *         by that grade
     */
    private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(
            List<StudentRecord> records) {

        return records.stream()
                .collect(Collectors.toMap(
                        StudentRecord::getGrade, (student) -> 1,
                        (student1, student2) -> student1 + student2
                ));
    }

    /**
     * Returns a map of students partitioned into two lists - student records of
     * students who have passes the class (the key is {@code true}), and of those
     * who have not passed the class (the key is {@code fale}).
     *
     * @param records the list of student records to filter
     * @return a map of students partitioned into two lists of students who have and
     *         have not passed the class
     */
    private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(
            List<StudentRecord> records) {

        return records.stream().collect(
                Collectors.partitioningBy((student) -> student.getGrade() > 1)
        );
    }
}
