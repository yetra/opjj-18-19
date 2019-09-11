package hr.fer.zemris.java.hw17.trazilica;

import java.util.List;

/**
 * An n-dimensional vector whose values are represented in a list. To
 * be used for modelling tfidf word vectors and calculating document
 * similarity ({@link #similarity(Vector)}).
 *
 * @author Bruna Dujmović
 *
 */
class Vector {

    /**
     * A list of values of this vector.
     */
    private List<Double> values;

    /**
     * Constructs a vector from its values.
     *
     * @param values a list of vector values
     */
    Vector(List<Double> values) {
        this.values = values;
    }

    /**
     * Returns a list of values of this vector.
     *
     * @return a list of values of this vector
     */
    List<Double> getValues() {
        return values;
    }

    /**
     * Returns the absolute value of this vector.
     *
     * @return the absolute value of this vector
     */
    private double norm() {
        double squareSum = 0.0;

        for (Double value : values) {
            squareSum += value * value;
        }

        return Math.sqrt(squareSum);
    }

    /**
     * Computes the similarity between two vectors.
     *
     * @param other the other vector
     * @return the similarity between two vectors
     */
    double similarity(Vector other) {
        List<Double> otherValues = other.getValues();

        if (otherValues.size() != values.size()) {
            return 0;
        }

        double dotProduct = 0.0;
        for (int i = 0, size = values.size(); i < size; i++) {
            dotProduct += values.get(i) * otherValues.get(i);
        }

        return dotProduct / (this.norm() * other.norm());
    }

}
