package hr.fer.zemris.java.webserver;

/**
 * An interface used for dispatching URL requests.
 *
 * @author Bruna Dujmović
 * 
 */
public interface IDispatcher {

    /**
     * Dispatches a request for the given url path.
     *
     * @param urlPath the url path of the request
     * @throws Exception if request cannot be dispatched
     */
    void dispatchRequest(String urlPath) throws Exception;
}