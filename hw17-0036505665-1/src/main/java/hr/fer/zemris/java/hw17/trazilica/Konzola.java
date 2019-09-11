package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * A command line app for document searching.
 *
 * @author Bruna Dujmović
 *
 */
public class Konzola {

    /**
     * The engine used for document searching.
     */
    private static SearchEngine engine;

    /**
     * The top ten search results.
     */
    private static List<Document> top10 = new ArrayList<>();

    /**
     * Main method. Starts the command line app.
     *
     * @param args the command-line arguments - 1 expected (a path to the documents directory)
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Document root directory not given!");
            System.exit(1);
        }

        Path root = Paths.get(args[0]);
        engine = new SearchEngine(root);

        System.out.println("Veličina riječnika je " + engine.getVocabularySize() + " riječi.");

        boolean stop = false;

        try (Scanner sc = new Scanner(System.in)) {
            while (!stop) {
                System.out.print("\nEnter command > ");

                String[] commandParts = sc.nextLine().trim().split("\\s+", 2);
                String commandName = commandParts[0];

                switch (commandName) {
                    case "query":
                        queryCommand(commandParts[1]);
                        break;

                    case "type":
                        if (commandParts.length > 2) {
                            System.out.println("Pogrešan broj argumenata!");
                            break;
                        }

                        typeCommand(commandParts[1]);
                        break;

                    case "results":
                        if (commandParts.length > 1) {
                            System.out.println("Pogrešan broj argumenata!");
                            break;
                        }

                        displayResults();
                        break;

                    case "exit":
                        if (commandParts.length > 1) {
                            System.out.println("Pogrešan broj argumenata!");
                            break;
                        }

                        stop = true;
                        break;

                    default:
                        System.out.println("Nepoznata naredba.");
                }
            }
        }
    }

    /**
     * Executes the query command.
     *
     * @param arguments the query command arguments
     */
    private static void queryCommand(String arguments) {
        Document document = new Document();
        document.fromWords(arguments, engine.getVocabulary());
        document.createTfidf(engine.getIdfs());

        Vector tfidf = document.getTfidf();
        List<Document> documents = engine.getDocuments();

        documents.forEach(d -> {
            double similarity = tfidf.similarity(d.getTfidf());
            d.setSimilarity(similarity);
        });
        documents.sort(Comparator.comparingDouble(Document::getSimilarity).reversed());
        top10 = documents.subList(0, 10);

        displayWords(arguments);
        displayResults();
    }

    /**
     * Executes the type command.
     *
     * @param arguments the type command arguments
     */
    private static void typeCommand(String arguments) {
        if (top10.isEmpty()) {
            System.out.println("Nikad nije zadana query naredba!");
            return;
        }

        try {
            int index = Integer.parseInt(arguments);
            Document documentToType = top10.get(index);

            System.out.println("----------------------------------------------------------------");
            System.out.println("Dokument: " + documentToType.getPath().toString());
            System.out.println("----------------------------------------------------------------");
            System.out.println(Files.readString(documentToType.getPath()));

        } catch (IOException e) {
            System.out.println("Ne mogu ispisati dokument...");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Pogrešan indeks dokumenta!");
        }
    }

    /**
     * Displays the search results.
     */
    private static void displayResults() {
        if (top10.isEmpty()) {
            System.out.println("Nikad nije zadana query naredba!");
            return;
        }

        System.out.println("Najboljih 10 rezultata:");
        for (int i = 0; i < 10; i++) {
            Document result = top10.get(i);

            if (result.getSimilarity() > 0) {
                System.out.format("[%2d] (%.4f) %s%n",
                        i, result.getSimilarity(), result.getPath());
            }
        }
    }

    /**
     * Displays the query words if they are in the vocabulary.
     *
     * @param words the words to display
     */
    private static void displayWords(String words) {
        String[] splitWords = words.split("[\\d]|[\\W]");

        StringBuilder sb = new StringBuilder("Query is: [");
        for (String word : splitWords) {
            if (engine.getVocabulary().contains(word)) {
                sb.append(word).append(", ");
            }
        }

        sb.setLength(sb.length() - 2);
        System.out.println(sb.append("]").toString());
    }
}
