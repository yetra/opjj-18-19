package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A web worker that displays a page with links to smart scripts and other workers. It
 * provides a form for sending parameters to the {@link SumWorker} and a form for
 * changing the background color of the page using {@link BgColorWorker}.
 *
 * @author Bruna Dujmović
 *
 */
public class Home implements IWebWorker {

    /**
     * The default background color of the page.
     */
    private static final String DEFAULT_COLOR = "7F7F7F";

    @Override
    public void processRequest(RequestContext context) throws Exception {

        String color = context.getPersistentParameter("bgcolor");
        context.setTemporaryParameter(
                "background", (color != null) ? color : DEFAULT_COLOR
        );

        context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
    }
}
