package hr.fer.zemris.java.custom.collections;

public class EmptyStackException extends RuntimeException {

    public EmptyStackException() {
        super();
    }

    public EmptyStackException(String message) {
        super(message);
    }
}
