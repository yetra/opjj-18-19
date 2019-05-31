package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;

/**
 * A web worker for updating the background color of /index2.html.
 *
 * @author Bruna Dujmović
 *
 */
public class BgColorWorker implements IWebWorker {

    /**
     * A regex to check if a string represents a valid hex-encoded color.
     */
    private static final String HEX_COLOR_REGEX = "^[0-9A-Fa-f]{6}$";

    @Override
    public void processRequest(RequestContext context) throws Exception {

        String color = context.getParameter("bgcolor");

        if (color != null && color.matches(HEX_COLOR_REGEX)) {
            context.setPersistentParameter("bgcolor", color);
            generateHTMLWithMessage("Color updated to #" + color + "!", context);

        } else {
            generateHTMLWithMessage("Color not updated!", context);
        }
    }

    /**
     * Generates a HTML document on the given context with a link to /index2.html and
     * the provided message.
     *
     * @param message the message to diplay on the page
     */
    private void generateHTMLWithMessage(String message, RequestContext context) throws IOException {
        context.write("<html><head><title>BgColor</title></head><body>");

        context.write("<p>");
        context.write(message);
        context.write("</p>");

        context.write("<p>See it on <a href=\"/index2.html\">index2</a></p>");

        context.write("</body></html>");
    }
}
