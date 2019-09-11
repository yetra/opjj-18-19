package hr.fer.zemris.java.hw17.trazilica;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Models a document.
 *
 * @author Bruna Dujmović
 *
 */
class Document {

    /**
     * A regex for matching words.
     */
    private static final String WORD_REGEX = "[a-zA-Z_šđčćžŠĐČĆŽ]+";

    /**
     * The {@link Pattern} for matching words.
     */
    private static final Pattern PATTERN = Pattern.compile(WORD_REGEX);

    /**
     * The path to the document.
     */
    private Path path;

    /**
     * The tf values of the document.
     */
    private Map<String, Integer> tfs = new HashMap<>();

    /**
     * The tfidf vector of the document.
     */
    private Vector tfidf;

    /**
     * The similarity between this document an another document.
     */
    private double similarity;

    /**
     * Constructs an empty {@link Document}.
     */
    Document() {}

    /**
     * Constructs a {@link Document} of the given path.
     *
     * @param path the path to the document.
     */
    Document(Path path) {
        this.path = path;
    }

    /**
     * Constructs a document from its file.
     *
     * @param vocabulary the vocabulary
     * @throws IOException if there's an issue with the file
     */
    void fromFile(Set<String> vocabulary) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;

            while ((line = br.readLine()) != null) {
                fromWords(line, vocabulary);
            }
        }
    }

    /**
     * Constructs a document from a string of words.
     *
     * @param words the string of words
     * @param vocabulary the vocabulary
     */
    void fromWords(String words, Set<String> vocabulary) {
        Matcher matcher = PATTERN.matcher(words);

        while (matcher.find()) {
            String word = matcher.group().toLowerCase();

            if (vocabulary.contains(word)) {
                tfs.merge(word, 1, Integer::sum);
            }
        }
    }

    /**
     * Calculates the tfidf vector.
     *
     * @param idfs a map of idf values
     */
    void createTfidf(Map<String, Double> idfs) {
        List<Double> tfidfList = new ArrayList<>();

        idfs.forEach((word, idf) -> tfidfList.add(tfs.getOrDefault(word, 0) * idf));

        tfidf = new Vector(tfidfList);
    }

    /**
     * Returns the tfidf vector.
     *
     * @return the tfidf vector
     */
    Vector getTfidf() {
        return tfidf;
    }

    /**
     * Returns the similarity between this document an another document.
     *
     * @return the similarity between this document an another document
     */
    double getSimilarity() {
        return similarity;
    }

    /**
     * Sets the similarity between this document an another document.
     *
     * @param similarity the new similarity
     */
    void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    /**
     * Returns the path to the document.
     *
     * @return the path to the document
     */
    Path getPath() {
        return path;
    }
}
