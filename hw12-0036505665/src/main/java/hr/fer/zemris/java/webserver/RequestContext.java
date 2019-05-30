package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * This class is used for storing client request parameters, generating HTTP headers
 * and writing content using a given output stream.
 *
 * @author Bruna Dujmović
 *
 */
public class RequestContext {

    /**
     * An output stream for writing content.
     */
    private OutputStream outputStream;
    /**
     * The charset used for byte-to-char conversion.
     */
    private Charset charset;

    /**
     * The encoding of the content.
     */
    private String encoding = "UTF-8";

    /**
     * The status code of the header.
     */
    private int statusCode = 200;
    /**
     * The status text of the header.
     */
    private String statusText = "OK";

    /**
     * The mime type of the content.
     */
    private String mimeType = "text/html";
    /**
     * The length of the content.
     */
    private Long contentLength = null;

    /**
     * A map of request parameters.
     */
    private Map<String, String> parameters;
    /**
     * A map of temporary request parameters.
     */
    private Map<String, String> temporaryParameters;
    /**
     * A map of persistent request parameters.
     */
    private Map<String, String> persistentParameters;

    /**
     * A list of cookies.
     */
    private List<RCCookie> outputCookies;

    /**
     * {@code true} if a header has already been generated for the current response.
     */
    private boolean headerGenerated = false;

    /**
     * An object for dispatching URL requests.
     */
    private IDispatcher dispatcher;

    /**
     * Constructs a {@link RequestContext} with the given parameters.
     *
     * @param outputStream the output stream for writing content
     * @param parameters the map of parameters
     * @param persistentParameters the map of persistent parameters
     * @param outputCookies the list of cookies
     * @throws NullPointerException if the given output stream is {@code null}
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters,
                          Map<String, String> persistentParameters,
                          List<RCCookie> outputCookies) {

        this.outputStream = Objects.requireNonNull(outputStream);
        this.parameters = Objects.requireNonNullElse(parameters, new HashMap<>());
        this.temporaryParameters = new HashMap<>();
        this.persistentParameters = Objects.requireNonNullElse(persistentParameters, new HashMap<>());
        this.outputCookies = Objects.requireNonNullElse(outputCookies, new ArrayList<>());
    }

    /**
     * Constructs a {@link RequestContext} with the given parameters.
     *
     * @param outputStream the output stream for writing content
     * @param parameters the map of parameters
     * @param persistentParameters the map of persistent parameters
     * @param outputCookies the list of cookies
     * @param temporaryParameters the map of temporary parameters
     * @param dispatcher the object used for dispatching URL requests
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters,
                          Map<String, String> persistentParameters,
                          List<RCCookie> outputCookies, Map<String,String> temporaryParameters,
                          IDispatcher dispatcher) {

        this(outputStream, parameters, persistentParameters, outputCookies);
        this.temporaryParameters = temporaryParameters;
        this.dispatcher = dispatcher;
    }

    /**
     * Sets this context's encoding to a given encoding.
     *
     * @param encoding the new encoding of this context
     * @throws RuntimeException if the header has already been generated
     */
    public void setEncoding(String encoding) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated!");
        }

        this.encoding = encoding;
    }

    /**
     * Sets the status code of the header to a given status code.
     *
     * @param statusCode the new status code of the header
     * @throws RuntimeException if the header has already been generated
     */
    public void setStatusCode(int statusCode) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated!");
        }

        this.statusCode = statusCode;
    }

    /**
     * Sets the status text of the header to a given status text.
     *
     * @param statusText the new status text of the header
     * @throws RuntimeException if the header has already been generated
     */
    public void setStatusText(String statusText) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated!");
        }

        this.statusText = statusText;
    }

    /**
     * Sets the mime type of the content to a given type.
     *
     * @param mimeType the new mime type of the content
     * @throws RuntimeException if the header has already been generated
     */
    public void setMimeType(String mimeType) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated!");
        }

        this.mimeType = mimeType;
    }

    /**
     * Sets the length of the content to a given value.
     *
     * @param contentLength the new content length
     * @throws RuntimeException if the header has already been generated
     */
    public void setContentLength(Long contentLength) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated!");
        }

        this.contentLength = contentLength;
    }

    /**
     * Returns the map of parameters.
     *
     * @return the map of parameters
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Returns the map of temporary parameters.
     *
     * @return the map of temporary parameters
     */
    public Map<String, String> getTemporaryParameters() {
        return temporaryParameters;
    }

    /**
     * Sets the given map as the temporary parameters map of this context.
     *
     * @param temporaryParameters the new temporary parameters map
     */
    public void setTemporaryParameters(Map<String, String> temporaryParameters) {
        this.temporaryParameters = temporaryParameters;
    }

    /**
     * Returns the map of persistent parameters.
     *
     * @return the map of persistent parameters
     */
    public Map<String, String> getPersistentParameters() {
        return persistentParameters;
    }

    /**
     * Sets the given map as the persistent parameters map of this context.
     *
     * @param persistentParameters the new temporary parameters map
     */
    public void setPersistentParameters(Map<String, String> persistentParameters) {
        this.persistentParameters = persistentParameters;
    }

    /**
     * Retrieves the value associated with a given name from the {@link #parameters}
     * map, or {@code null} if no such association exists.
     *
     * @param name a {@link #parameters} map key
     * @return the value associated with the given name or {@code null}
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Retrieves a read-only set of all the names in the {@link #parameters} map.
     *
     * @return a read-only set of all the names in the {@link #parameters} map
     */
    public Set<String> getParameterNames() {
        return Collections.unmodifiableSet(parameters.keySet());
    }

    /**
     * Retrieves the value associated with a given name from the {@link #persistentParameters}
     * map, or {@code null} if no such association exists.
     *
     * @param name a {@link #persistentParameters} map key
     * @return the value associated with the given name or {@code null}
     */
    public String getPersistentParameter(String name) {
        return persistentParameters.get(name);
    }

    /**
     * Retrieves a read-only set of all the names in the {@link #persistentParameters} map.
     *
     * @return a read-only set of all the names in the {@link #persistentParameters} map
     */
    public Set<String> getPersistentParameterNames() {
        return Collections.unmodifiableSet(persistentParameters.keySet());
    }

    /**
     * Adds a given name-value pair to the {@link #persistentParameters} map.
     *
     * @param name the key of the key-value pair
     * @param value the value of the key-value pair
     */
    public void setPersistentParameter(String name, String value) {
        persistentParameters.put(name, value);
    }

    /**
     * Removes a key-value pair specified by the given name from the {@link #persistentParameters}
     * map.
     *
     * @param name the key of the key-value pair to remove
     */
    public void removePersistentParameter(String name) {
        persistentParameters.remove(name);
    }

    /**
     * Retrieves the value associated with a given name from the {@link #temporaryParameters}
     * map, or {@code null} if no such association exists.
     *
     * @param name a {@link #temporaryParameters} map key
     * @return the value associated with the given name or {@code null}
     */
    public String getTemporaryParameter(String name) {
        return temporaryParameters.get(name);
    }

    /**
     * Retrieves a read-only set of all the names in the {@link #temporaryParameters} map.
     *
     * @return a read-only set of all the names in the {@link #temporaryParameters} map
     */
    public Set<String> getTemporaryParameterNames() {
        return Collections.unmodifiableSet(temporaryParameters.keySet());
    }

    /**
     * Retrieves a unique identifier for the current user session.
     *
     * @return a unique identifier for the current user session
     */
    public String getSessionID() {
        return "";
    }

    /**
     * Adds a given name-value pair to the {@link #temporaryParameters} map.
     *
     * @param name the key of the key-value pair
     * @param value the value of the key-value pair
     */
    public void setTemporaryParameter(String name, String value) {
        temporaryParameters.put(name, value);
    }

    /**
     * Removes a key-value pair specified by the given name from the {@link #temporaryParameters}
     * map.
     *
     * @param name the key of the key-value pair to remove
     */
    public void removeTemporaryParameter(String name) {
        temporaryParameters.remove(name);
    }

    /**
     * Adds a given cookie to this context's list of cookies.
     *
     * @param cookie the cookie to add
     */
    public void addRCCookie(RCCookie cookie) {
        outputCookies.add(cookie);
    }

    /**
     * Returns the object used for dispatching URL requests.
     *
     * @return the object used for dispatching URL requests
     */
    public IDispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     * Writes a given byte array using the {@link #outputStream} and returns the
     * context.
     *
     * @param data the byte array to write
     * @return the context after writing
     * @throws IOException if there was an issue with writing the data
     * @throws RuntimeException if the header has already been generated
     */
    public RequestContext write(byte[] data) throws IOException {
        if (!headerGenerated) {
            outputStream.write(generateHeader());
            headerGenerated = true;
        }

        outputStream.write(data);

        return this;
    }

    /**
     * Writes a given number of bytes of the provided data array starting from a
     * specified offset using the {@link #outputStream} and returns the context.
     *
     * @param data the byte array to write
     * @param offset the index of the first byte to be written
     * @param len the number of bytes to write
     * @return the context after writing
     * @throws IOException if there was an issue with writing the data
     * @throws RuntimeException if the header has already been generated
     */
    public RequestContext write(byte[] data, int offset, int len) throws IOException {
        if (!headerGenerated) {
            outputStream.write(generateHeader());
            headerGenerated = true;
        }

        outputStream.write(data, offset, len);

        return this;
    }

    /**
     * Writes a givne string using the {@link #outputStream} and returns the context.
     *
     * @param text the string to write
     * @return the context after writing
     * @throws IOException if there was an issue with writing the string
     * @throws RuntimeException if the header has already been generated
     */
    public RequestContext write(String text) throws IOException {
        if (!headerGenerated) {
            outputStream.write(generateHeader());
            headerGenerated = true;
        }

        byte[] data = text.getBytes(charset);
        outputStream.write(data);

        return this;
    }

    /**
     * Creates a header and returns it in a byte array encoded with {@link #encoding}.
     *
     * @return an encoded byte array of the header
     */
    private byte[] generateHeader() {
        charset = Charset.forName(encoding);

        StringBuilder sb = new StringBuilder();

        sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText);
        sb.append("\r\n");

        sb.append("Content-Type: ").append(mimeType);
        if (mimeType.startsWith("text/")) {
            sb.append("; charset=").append(encoding);
        }
        sb.append("\r\n");

        if (contentLength != null) {
            sb.append("Content-Length:").append(contentLength).append("\r\n");
        }

        if (!outputCookies.isEmpty()) {
            outputCookies.forEach(
                    cookie -> sb.append(cookie.toString()).append("\r\n")
            );
        }

        sb.append("\r\n");

        return sb.toString().getBytes(charset);
    }

    /**
     * This class models a cookie.
     */
    public static class RCCookie {
        /**
         * The name of this cookie.
         */
        private String name;

        /**
         * The value of this cookie.
         */
        private String value;

        /**
         * The time until this cookie is valid.
         */
        private Integer maxAge;

        /**
         * The domain of this cookie.
         */
        private String domain;

        /**
         * The path of this cookie.
         */
        private String path;

        /**
         * Constructs an {@link RCCookie} from the given parameters.
         *
         * @param name the name of the cookie
         * @param value the value of the cookie
         * @param maxAge the maximum age of the cookie
         * @param domain the domain of the cookie
         * @param path the path of the cookie
         */
        public RCCookie(String name, String value, Integer maxAge, String domain,
                        String path) {
            this.name = name;
            this.value = value;
            this.maxAge = maxAge;
            this.domain = domain;
            this.path = path;
        }

        /**
         * Returns the name of this cookie.
         *
         * @return the name of this cookie
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the value of this cookie.
         *
         * @return the value of this cookie
         */
        public String getValue() {
            return value;
        }

        /**
         * Returns the maximum age of this cookie.
         *
         * @return the maximum age of this cookie
         */
        public Integer getMaxAge() {
            return maxAge;
        }

        /**
         * Returns the domain of this cookie.
         *
         * @return the domain of this cookie
         */
        public String getDomain() {
            return domain;
        }

        /**
         * Returns the path of this cookie.
         *
         * @return the path of this cookie
         */
        public String getPath() {
            return path;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("Set-Cookie: ").append(name).append("=\"").append(value).append("\"");

            if (domain != null) {
                sb.append("; Domain=").append(domain);
            }
            if (path != null) {
                sb.append("; Path=").append(path);
            }
            if (maxAge != null) {
                sb.append("; Max-Age=").append(maxAge);
            }

            return sb.toString();
        }
    }
}
