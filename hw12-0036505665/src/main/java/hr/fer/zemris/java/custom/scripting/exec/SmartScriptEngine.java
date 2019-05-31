package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This class executes the smart script document whose parsed tree it obtains through
 * the constructor.
 *
 * @author Bruna Dujmović
 *
 */
public class SmartScriptEngine {

    /**
     * The root node of the smart script document to execute.
     */
    private DocumentNode documentNode;

    /**
     * A {@link RequestContext} for creating the document's header and output.
     */
    private RequestContext requestContext;

    /**
     * An {@link ObjectMultistack} for storing document variables.
     */
    private ObjectMultistack multistack = new ObjectMultistack();

    /**
     * A visitor used for executing the given document.
     */
    private INodeVisitor visitor = new INodeVisitor() {
        @Override
        public void visitTextNode(TextNode node) {
            try {
                requestContext.write(node.getText());
            } catch (IOException e) {
                System.out.println("Cannot write the given text node!");
            }
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            String variable = node.getVariable().getName();

            ValueWrapper value = new ValueWrapper(node.getStartExpression().asText());
            String endValue = node.getEndExpression().asText();
            String step = node.getStepExpression().asText();

            multistack.push(variable, value);

            while (value.numCompare(endValue) <= 0) {
                for (int i = 0, end = node.numberOfChildren(); i < end; i++) {
                    node.getChild(i).accept(this);
                }

                value = multistack.pop(variable);
                value.add(step);
                multistack.push(variable, value);
            }

            multistack.pop(variable);
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            Stack<ValueWrapper> stack = new Stack<>();
            Element[] elements = node.getElements();

            for (Element element : elements) {
                if (element instanceof ElementConstantDouble
                        || element instanceof ElementConstantInteger
                        || element instanceof ElementString) {
                    stack.push(new ValueWrapper(element.toString()));

                } else if (element instanceof ElementVariable) {
                    String variable = ((ElementVariable) element).getName();
                    stack.push(new ValueWrapper(multistack.peek(variable).getValue()));

                } else if (element instanceof ElementOperator) {
                    String operator = ((ElementOperator) element).getSymbol();

                    ValueWrapper firstOperand = stack.pop();
                    ValueWrapper secondOperand = stack.pop();

                    switch (operator) {
                        case "+":
                            firstOperand.add(secondOperand.toString());
                            break;
                        case "-":
                            firstOperand.subtract(secondOperand.toString());
                            break;
                        case "*":
                            firstOperand.multiply(secondOperand.toString());
                            break;
                        case "/":
                            firstOperand.divide(secondOperand.toString());
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }

                    stack.push(firstOperand);

                } else {
                    String function = ((ElementFunction) element).getName();

                    switch (function) {
                        case "sin":
                            ValueWrapper value = stack.pop();
                            double doubleValue = ValueWrapper.toDouble(value.getValue());
                            ValueWrapper sinValue = new ValueWrapper(
                                    Math.sin(Math.toRadians(doubleValue))
                            );

                            stack.push(sinValue);
                            break;

                        case "decfmt":
                            DecimalFormat form = new DecimalFormat(stack.pop().toString());
                            double number = ValueWrapper.toDouble(stack.pop().getValue());

                            stack.push(new ValueWrapper(form.format(number)));
                            break;

                        case "dup":
                            ValueWrapper top = stack.pop();

                            stack.push(top);
                            stack.push(top);
                            break;

                        case "swap":
                            ValueWrapper poppedFirst = stack.pop();
                            ValueWrapper poppedSecond = stack.pop();

                            stack.push(poppedFirst);
                            stack.push(poppedSecond);
                            break;

                        case "setMimeType":
                            String mimeType = stack.pop().toString();
                            requestContext.setMimeType(mimeType);
                            break;

                        case "paramGet":
                            ValueWrapper defValue = stack.pop();
                            String name = stack.pop().toString();

                            String valueString = requestContext.getParameter(name);

                            stack.push(valueString == null ?
                                    defValue : new ValueWrapper(valueString)
                            );
                            break;

                        case "pparamGet":
                            defValue = stack.pop();
                            name = stack.pop().toString();

                            valueString = requestContext.getPersistentParameter(name);

                            stack.push(valueString == null ?
                                    defValue : new ValueWrapper(valueString)
                            );
                            break;

                        case "pparamSet":
                            name = stack.pop().toString();
                            value = stack.pop();

                            requestContext.setPersistentParameter(name, value.toString());
                            break;

                        case "pparamDel":
                            name = stack.pop().toString();
                            requestContext.removePersistentParameter(name);
                            break;

                        case "tparamGet":
                            defValue = stack.pop();
                            name = stack.pop().toString();

                            valueString = requestContext.getTemporaryParameter(name);

                            stack.push(valueString == null ?
                                    defValue : new ValueWrapper(valueString)
                            );
                            break;

                        case "tparamSet":
                            name = stack.pop().toString();
                            value = stack.pop();

                            requestContext.setTemporaryParameter(name, value.toString());
                            break;

                        case "tparamDel":
                            name = stack.pop().toString();
                            requestContext.removeTemporaryParameter(name);
                            break;

                        default:
                            throw new IllegalArgumentException();
                    }
                }
            }

            new ArrayList<>(stack).forEach(e -> {
                try {
                    requestContext.write(e.toString());
                } catch (IOException exc) {
                    System.out.println(exc.getMessage());
                }
            });
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            for (int i = 0, end = node.numberOfChildren(); i < end; i++) {
                node.getChild(i).accept(this);
            }
        }
    };

    /**
     * Constructs a {@link SmartScriptEngine} for the given {@link DocumentNode} and
     * {@link RequestContext}.
     *
     * @param documentNode the root node of the smart script document to execute
     * @param requestContext a {@link RequestContext} for creating the document's header
     *                       and output
     */
    public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        this.documentNode = documentNode;
        this.requestContext = requestContext;
    }

    /**
     * Executes the given smart script document.
     */
    public void execute() {
        documentNode.accept(visitor);
    }
}
