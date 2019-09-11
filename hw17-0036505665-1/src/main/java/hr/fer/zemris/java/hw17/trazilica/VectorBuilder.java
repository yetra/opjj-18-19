package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class VectorBuilder extends SimpleFileVisitor<Path> {

    private Set<String> vocabulary;

    private Map<String, Double> idfs;

    private List<Document> documents = new ArrayList<>();

    VectorBuilder(Set<String> vocabulary, Map<String, Double> idfs) {
        this.vocabulary = vocabulary;
        this.idfs = idfs;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            throws IOException {

        Document document = new Document(file);
        document.fromFile(vocabulary);
        document.createTfidf(idfs);

        documents.add(document);

        return FileVisitResult.CONTINUE;
    }

    List<Document> getDocuments() {
        return documents;
    }

}
