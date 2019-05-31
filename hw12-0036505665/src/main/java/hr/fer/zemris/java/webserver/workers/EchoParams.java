package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.Map;

/**
 * A web worker for displaying the parameters in a given request context.
 *
 * @author Bruna Dujmović
 *
 */
public class EchoParams implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) {
        context.setMimeType("text/html");

        Map<String, String> parameters = context.getParameters();

        try {
            context.write("<html><body><h1>Parameters</h1><table>");
            context.write("<thead><tr><th>Key</th><th>Value</th></tr></thead>");
            context.write("<tbody>");

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                context.write(
                        "<tr><td>" + entry.getKey() + "</td>" +
                        "<td>" + entry.getValue() + "</td></tr>"
                );
            }

            context.write("</tbody></table></body></html>");

        } catch (IOException ex) {
            // Log exception to servers log...
            ex.printStackTrace();
        }
    }
}
