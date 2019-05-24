package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

public class RequestContext {

    private OutputStream outputStream;
    private Charset charset;

    public String encoding = "UTF-8";
    public int statusCode = 200;
    public String statusText = "OK";
    public String mimeType = "text/html";
    public Long contentLength = null;

    private Map<String, String> parameters;
    private Map<String, String> temporaryParameters;
    private Map<String, String> persistentParameters;

    private List<RCCookie> outputCookies;

    private boolean headerGenerated = false;

    public RequestContext(OutputStream outputStream, Map<String, String> parameters,
                          Map<String, String> persistentParameters,
                          List<RCCookie> outputCookies) {

        this.outputStream = Objects.requireNonNull(outputStream);
        this.parameters = Objects.requireNonNullElse(parameters, new HashMap<>());
        this.temporaryParameters = new HashMap<>();
        this.persistentParameters = Objects.requireNonNullElse(persistentParameters, new HashMap<>());
        this.outputCookies = Objects.requireNonNullElse(outputCookies, new ArrayList<>());
    }

    public void setEncoding(String encoding) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated!");
        }

        this.encoding = encoding;
    }

    public void setStatusCode(int statusCode) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated!");
        }

        this.statusCode = statusCode;
    }

    public void setStatusText(String statusText) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated!");
        }

        this.statusText = statusText;
    }

    public void setMimeType(String mimeType) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated!");
        }

        this.mimeType = mimeType;
    }

    public void setContentLength(Long contentLength) {
        if (headerGenerated) {
            throw new RuntimeException("Header already generated!");
        }

        this.contentLength = contentLength;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Map<String, String> getTemporaryParameters() {
        return temporaryParameters;
    }

    public void setTemporaryParameters(Map<String, String> temporaryParameters) {
        this.temporaryParameters = temporaryParameters;
    }

    public Map<String, String> getPersistentParameters() {
        return persistentParameters;
    }

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
     * Retrieves a read-only set of all the names in the {@link #persistentParameters}
     * map.
     *
     * @return a read-only set of all the names in the {@link #persistentParameters}
     *         map
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
     * Retrieves a read-only set of all the names in the {@link #temporaryParameters}
     * map.
     *
     * @return a read-only set of all the names in the {@link #temporaryParameters}
     *         map
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

    // TODO ?
    public void addRCCookie(RCCookie cookie) {
        outputCookies.add(cookie);
    }

    public RequestContext write(byte[] data) throws IOException {
        if (!headerGenerated) {
            outputStream.write(generateHeader());
            headerGenerated = true;
        }

        outputStream.write(data);

        return this;
    }

    public RequestContext write(byte[] data, int offset, int len) throws IOException {
        if (!headerGenerated) {
            outputStream.write(generateHeader());
            headerGenerated = true;
        }

        outputStream.write(data, offset, len);

        return this;
    }

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

    public static class RCCookie {
        private String name;
        private String value;
        private Integer maxAge;
        private String domain;
        private String path;

        public RCCookie(String name, String value, Integer maxAge, String domain,
                        String path) {
            this.name = name;
            this.value = value;
            this.maxAge = maxAge;
            this.domain = domain;
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public Integer getMaxAge() {
            return maxAge;
        }

        public String getDomain() {
            return domain;
        }

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
