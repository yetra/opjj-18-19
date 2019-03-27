package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node that represents an entire document.
 * It inherits from the {@code Node} class.
 *
 * @author Bruna Dujmović
 *
 */
public class DocumentNode extends Node {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0, end = numberOfChildren(); i < end; i++) {
            sb.append(getChild(i).toString());
        }

        return sb.toString();
    }
}
