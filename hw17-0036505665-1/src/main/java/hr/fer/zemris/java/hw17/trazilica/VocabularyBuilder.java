package hr.fer.zemris.java.hw17.trazilica;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@link SimpleFileVisitor} that builds the vocabulary and returns it
 * in a set.
 *
 * @author Bruna Dujmović
 *
 */
public class VocabularyBuilder extends SimpleFileVisitor<Path> {

    /**
     * A regex for matching words.
     */
    private static final String WORD_REGEX = "[a-zA-Z_šđčćžŠĐČĆŽ]+";

    /**
     * The {@link Pattern} for matching words.
     */
    private static final Pattern PATTERN = Pattern.compile(WORD_REGEX);

    /**
     * A list of words not to be included in the vocabulary.
     */
    private List<String> stopwords;

    /**
     * The number of documents.
     */
    private int documentCount = 0;

    /**
     * The vocabulary.
     */
    private Set<String> vocabulary = new HashSet<>();

    /**
     * A map of word frequencies.
     */
    private Map<String, Integer> wordFrequencies = new HashMap<>();

    /**
     * Constructs a {@link VocabularyBuilder} for the given stopwords.
     *
     * @param stopwords the words to not be included in the vocabulary
     */
    VocabularyBuilder(List<String> stopwords) {
        this.stopwords = stopwords;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            throws IOException {

        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;

            while ((line = br.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line);

                while (matcher.find()) {
                    String word = matcher.group().toLowerCase();

                    if (!stopwords.contains(word)) {
                        vocabulary.add(word);
                        wordFrequencies.merge(word, 1, Integer::sum);
                    }
                }
            }
        }

        documentCount++;

        return FileVisitResult.CONTINUE;
    }

    /**
     * Returns the vocabulary.
     *
     * @return the vocabulary
     */
    Set<String> getVocabulary() {
        return vocabulary;
    }

    /**
     * Returns a map of idf vectors.
     *
     * @return a map of idf vectors
     */
    Map<String, Double> getIdfs() {
        Map<String, Double> idfs = new HashMap<>();

        wordFrequencies.forEach((k, v) ->
                idfs.put(k, Math.log((double) documentCount / v)));

        return idfs;
    }
}
