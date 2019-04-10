package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.*;
import hr.fer.zemris.java.hw05.db.lexer.QueryLexerException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A program that demonstrates the {@link StudentDatabase} and related classes.
 * It reads the student data provided in the database.txt file and creates a new
 * instance of {@link StudentDatabase} for it.
 *
 * The user is prompted to write database queries in the console.
 * Each query must begin with the word "query".
 * Allowed query operators are >, <, >=, <=, =, !=, LIKE.
 * Student record fields that can be queried are firstName, lastName, and jmbag.
 * If multiple conditional expressions wish to be used in a single query, they must
 * be separated by the logical operator AND.
 *
 * The program ends when the user types "exit".
 *
 * @author Bruna Dujmović
 *
 */
public class StudentDB {

    /**
     * Main method. Creates a {@link StudentDatabase} object and handles user-input
     * database queries.
     *
     * @param args command-line arguments, not used
     * @throws IOException if the database.txt file cannot be read
     */
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(
                Paths.get("./database.txt"),
                StandardCharsets.UTF_8
        );

        StudentDatabase db = null;

        try {
            db = new StudentDatabase(lines);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        try (Scanner sc = new Scanner(System.in)) {
            System.out.format("> ");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.equals("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }

                String[] queryParts = line.split("\\s+", 2);

                if (!(queryParts[0].equals("query"))) {
                    System.out.println("Invalid commad \"" + queryParts[0] + "\".");
                    System.out.format("> ");
                    continue;
                }

                try {
                    QueryParser parser = new QueryParser(queryParts[1]);
                    printFilteredDatabase(db, parser);
                } catch (IllegalArgumentException | QueryLexerException e) {
                    System.out.println(e.getMessage());
                    System.out.format("> ");
                    continue;
                }

                System.out.format("> ");
            }
        }
    }

    /**
     * Prints the filtered database results to the console.
     *
     * @param db the database whose filtered results will be printed
     * @param parser the parser that parsed the filtering query
     */
    private static void printFilteredDatabase(StudentDatabase db, QueryParser parser) {
        List<StudentRecord> filtered;

        if (parser.isDirectQuery()) {
            filtered = new ArrayList<>();
            filtered.add(db.forJMBAG(parser.getQueriedJMBAG()));
            System.out.println("Using index for record retrieval.");

        } else {
            filtered = db.filter(new QueryFilter(parser.getQuery()));
        }

        List<String> output = new RecordFormatter(filtered).format();
        output.forEach(System.out::println);
    }
}
