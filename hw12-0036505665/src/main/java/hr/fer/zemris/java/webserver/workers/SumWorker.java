package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A web worker for calculating and displaying the sum of two numbers given through the
 * URL path.
 *
 * @author Bruna Dujmović
 *
 */
public class SumWorker implements IWebWorker {

    /**
     * Relative path string to the first image of this worker.
     */
    private static final String FIRST_IMAGE = "/images/img1.png";
    /**
     * Relative path string to the second image of this worker.
     */
    private static final String SECOND_IMAGE = "/images/img2.png";

    @Override
    public void processRequest(RequestContext context) throws Exception {
        context.setMimeType("text/html");

        int a = 1; int b = 2;
        try {
            String aStr = context.getParameter("a");
            a = (aStr == null) ? 1 : Integer.parseInt(aStr);

            String bStr = context.getParameter("b");
            b = (bStr == null) ? 2 : Integer.parseInt(bStr);

        } catch (NumberFormatException ignorable) {}

        context.setTemporaryParameter("zbroj", Integer.toString(a + b));
        context.setTemporaryParameter("varA", Integer.toString(a));
        context.setTemporaryParameter("varB", Integer.toString(b));

        context.setTemporaryParameter(
                "imgName", (a + b) % 2 == 0 ? FIRST_IMAGE : SECOND_IMAGE
        );

        context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
    }
}
