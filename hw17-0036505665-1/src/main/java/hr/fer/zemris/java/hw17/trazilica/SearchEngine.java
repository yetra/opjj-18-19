package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class SearchEngine {

    private static final String STOPWORDS = "hrvatski_stoprijeci.txt";

    private List<String> stopwords;

    private Set<String> vocabulary;

    private Map<String, Double> idfs = new HashMap<>();

    private List<Document> documents = new ArrayList<>();

    private Path root;

    SearchEngine(Path root) {
        this.root = root;

        loadStopwords();
        initVocabulary();
        createVectors();
    }

    private void loadStopwords() {
        try {
            stopwords = Files.readAllLines(Paths.get(STOPWORDS));

        } catch (IOException e) {
            System.out.println("Invalid stopwords document!");
            System.exit(1);
        }
    }

    private void initVocabulary() {
        try {
            VocabularyBuilder builder = new VocabularyBuilder(stopwords);
            Files.walkFileTree(root, builder);

            vocabulary = builder.getVocabulary();
            idfs = builder.getIdfs();

        } catch (IOException e) {
            System.out.println("Invalid documents - can't build vocabulary!");
            System.exit(1);
        }
    }

    private void createVectors() {
        try {
            VectorBuilder builder = new VectorBuilder(vocabulary, idfs);
            Files.walkFileTree(root, builder);

            documents = builder.getDocuments();

        } catch (IOException e) {
            System.out.println("Invalid documents - can't create vectors!");
            System.exit(1);
        }
    }

    List<Document> getDocuments() {
        return documents;
    }

    Map<String, Double> getIdfs() {
        return idfs;
    }

    Set<String> getVocabulary() {
        return vocabulary;
    }

    int getVocabularySize() {
        return vocabulary.size();
    }
}
