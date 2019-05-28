package hr.fer.zemris.java.webserver;

/**
 * An interface toward any object that can process the current request.
 *
 * @author Bruna Dujmović
 * 
 */
public interface IWebWorker {

    /**
     * Creates content for the client based on the given context.
     *
     * @param context the request context
     * @throws Exception if the request cannot be processed
     */
    public void processRequest(RequestContext context) throws Exception;
}