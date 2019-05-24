package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A visitor for all SmartScript node types.
 *
 * @author Bruna Dujmović
 *
 */
public interface INodeVisitor {

    /**
     * Visits a given {@link TextNode}.
     *
     * @param node the {@link TextNode} to visit
     */
    void visitTextNode(TextNode node);

    /**
     * Visits a given {@link ForLoopNode}.
     *
     * @param node the {@link ForLoopNode} to visit
     */
    void visitForLoopNode(ForLoopNode node);

    /**
     * Visits a given {@link EchoNode}.
     *
     * @param node the {@link EchoNode} to visit
     */
    void visitEchoNode(EchoNode node);

    /**
     * Visits a given {@link DocumentNode}.
     *
     * @param node the {@link DocumentNode} to visit
     */
    void visitDocumentNode(DocumentNode node);
}
