package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node that represents an entire document.
 *
 * @author Bruna Dujmović
 *
 */
public class DocumentNode extends Node {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitDocumentNode(this);
    }
}
