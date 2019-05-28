package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link SmartHttpServer} is a server used for processing HTTP requests. To start
 * it with the default config file, use "./config/server.properties" as the command-
 * line argument.
 *
 * Test URLs for the default host 127.0.0.1 and port 5721:
 * http://127.0.0.1:5721/sample.txt
 * http://127.0.0.1:5721/index.html
 * http://127.0.0.1:5721/fruits.png
 * http://www.localhost.com:5721/sample.txt
 * http://www.localhost.com:5721/index.html
 * http://www.localhost.com:5721/fruits.png
 *
 * @author Bruna Dujmović
 *
 */
public class SmartHttpServer {

    /**
     * The address of the server.
     */
    private String address;

    /**
     * The domain name of the server.
     */
    private String domainName;

    /**
     * The port that the server listens on.
     */
    private int port;

    /**
     * The number of worker threads.
     */
    private int workerThreads;

    /**
     * The value of the session timeout.
     */
    private int sessionTimeout;

    /**
     * Mime types of this server.
     */
    private Map<String,String> mimeTypes = new HashMap<>();

    /**
     * A map of web workers.
     */
    private Map<String,IWebWorker> workersMap = new HashMap<>();

    /**
     * The server thread.
     */
    private ServerThread serverThread;

    /**
     * The thread pool.
     */
    private ExecutorService threadPool;

    /**
     * A path to the document root.
     */
    private Path documentRoot;

    /**
     * Constructs a {@link SmartHttpServer} based on the given config file.
     *
     * @param configFileName the path string of the config file
     */
    public SmartHttpServer(String configFileName) {
        try {
            InputStream config = Files.newInputStream(Paths.get(configFileName));
            Properties properties = new Properties();
            properties.load(config);

            address = properties.getProperty("server.address");
            domainName = properties.getProperty("server.domainName");
            port = Integer.parseInt(properties.getProperty("server.port"));
            workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
            sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
            documentRoot = Paths.get(properties.getProperty("server.documentRoot"));

            Path mimeConfig = Paths.get(properties.getProperty("server.mimeConfig"));
            List<String> lines = Files.readAllLines(mimeConfig);
            for (String line : lines) {
                if (!line.isBlank() && !line.startsWith("#")) {
                    String[] parts = line.split("\\s+=\\s+");
                    mimeTypes.put(parts[0], parts[1]);
                }
            }

            Path workers = Paths.get(properties.getProperty("server.workers"));
            lines = Files.readAllLines(workers);
            for (String line : lines) {
                if (!line.isBlank() && !line.startsWith("#")) {
                    String[] parts = line.split("\\s+=\\s+");
                    String path = parts[0];
                    String fqcn = parts[1];

                    Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
                    Object newObject = referenceToClass.newInstance();
                    IWebWorker iww = (IWebWorker) newObject;
                    workersMap.put(path, iww);
                }
            }
            
        } catch (IOException e) {
            System.out.println("IOException when reading server config file!");
        } catch (IllegalArgumentException e) {
            System.out.println("Config file contains invalid data!");
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the server threat if not already running.
     */
    protected synchronized void start() {
        if (serverThread == null || !serverThread.isAlive()) {
            serverThread = new ServerThread();
            serverThread.start();

            threadPool = Executors.newFixedThreadPool(workerThreads);
        }
    }

    /**
     * Signals the server thread to stop running.
     */
    protected synchronized void stop() {
        try {
            serverThread.join();
        } catch (InterruptedException ignorable) {}

        threadPool.shutdown();
    }

    /**
     * This class models the server thread.
     */
    protected class ServerThread extends Thread {

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket();
                serverSocket.bind(
                        new InetSocketAddress(InetAddress.getByName(address), port)
                );

                while (true) {
                    Socket client = serverSocket.accept();
                    ClientWorker cw = new ClientWorker(client);
                    threadPool.submit(cw);
                }

            } catch (IOException e) {
                System.out.println("IOException when opening the server socket!");
            }
        }
    }

    /**
     * This class models a worker in this server's thread pool.
     */
    private class ClientWorker implements Runnable, IDispatcher {

        /**
         * The socket of the client's request.
         */
        private Socket csocket;

        /**
         * The input stream for reading from the client.
         */
        private PushbackInputStream istream;
        /**
         * The output stream for writing to the client.
         */
        private OutputStream ostream;

        /**
         * The version of the request.
         */
        private String version;
        /**
         * The method of the request.
         */
        private String method;
        /**
         * The host of the request.
         */
        private String host;

        /**
         * A map of request parameters.
         */
        private Map<String,String> params = new HashMap<>();
        /**
         * A map of temporary parameters.
         */
        private Map<String,String> tempParams = new HashMap<>();
        /**
         * A map of permanent parameters.
         */
        private Map<String,String> permPrams = new HashMap<>();

        /**
         * A list of cookies.
         */
        private List<RequestContext.RCCookie> outputCookies = new ArrayList<>();

        /**
         * The context of the request.
         */
        private RequestContext context;

        /**
         * The ID of the session.
         */
        private String SID;

        /**
         * Constructs a {@link ClientWorker} for the given socket.
         *
         * @param csocket the socket of the client's request
         */
        public ClientWorker(Socket csocket) {
            super();
            this.csocket = csocket;
        }

        /**
         * Processes a given url path.
         *
         * @param urlPath the path to process
         * @param directCall {@code true} if it's a direct call
         * @throws Exception if the given url cannot be processed
         */
        public void internalDispatchRequest(String urlPath, boolean directCall)
                throws Exception {

            if (context == null) {
                context = new RequestContext(ostream, params, permPrams,
                        outputCookies, tempParams, this);
            }

            // check if urlPath is mapped to IWebWorker
            if (workersMap.containsKey(urlPath)) {
                workersMap.get(urlPath).processRequest(context);
                ostream.flush();
                csocket.close();
                return;
            }

            // requestedPath = resolve path with respect to documentRoot
            Path requestedFile = documentRoot.resolve(
                    Paths.get(urlPath.startsWith("/") ? urlPath.substring(1) : urlPath)
            );

            // if requestedPath is not below documentRoot, return response status 403 forbidden
            if (!requestedFile.toAbsolutePath().startsWith(documentRoot.toAbsolutePath())) {
                sendError(403, "Forbidden");
                csocket.close();
                return;
            }
            // check if requestedPath exists, is file and is readable; if not, return status 404
            if (!Files.exists(requestedFile) || !Files.isRegularFile(requestedFile)
                    || !Files.isReadable(requestedFile)) {
                sendError(404, "File Not Found");
                csocket.close();
                return;
            }

            // else extract file extension
            String extension = getExtension(requestedFile);

            // if it's a smart script - parse it and create engine
            if (extension.equalsIgnoreCase("smscr")) {
                String documentBody = Files.readString(requestedFile);

                new SmartScriptEngine(
                        new SmartScriptParser(documentBody).getDocumentNode(),
                        context
                ).execute();

                ostream.flush();
                csocket.close();
                return;
            }

            // find in mimeTypes map appropriate mimeType for current file extension
            // (you filled that map during the construction of SmartHttpServer from mime.properties)
            String mimeType = mimeTypes.get(extension);
            // if no mime type found, assume application/octet-stream
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            // set mime-type; set status to 200
            context.setMimeType(mimeType);
            context.setStatusCode(200);
            // If you want, you can modify RequestContext to allow you to add additional headers
            // so that you can add “Content-Length: 12345” if you know that file has 12345 bytes

            // open file, read its content and write it to rc (that will generate header and send
            // file bytes to client)
            sendFileToClient(requestedFile, mimeType);

            csocket.close();
        }

        @Override
        public void dispatchRequest(String urlPath) throws Exception {
            internalDispatchRequest(urlPath, false);
        }

        @Override
        public void run() {
            try {
                // obtain input stream from socket
                istream = new PushbackInputStream(
                        new BufferedInputStream(csocket.getInputStream())
                );
                // obtain output stream from socket
                ostream = new BufferedOutputStream(csocket.getOutputStream());

                // Then read complete request header from your client in separate method...
                List<String> request = readRequest();
                // If header is invalid (less then a line at least) return response status 400
                if (request == null || request.size() < 1) {
                    sendError(400, "Bad request");
                    return;
                }

                String firstLine = request.get(0);
                // Extract (method, requestedPath, version) from firstLine
                String[] extracted = firstLine.split(" ");
                if (extracted.length != 3) {
                    sendError(400, "Bad request");
                    csocket.close();
                    return;
                }

                // if method not GET or version not HTTP/1.0 or HTTP/1.1 return response status 400
                method = extracted[0].toUpperCase();
                if(!method.equals("GET")) {
                    sendError(405, "Method Not Allowed");
                    csocket.close();
                    return;
                }
                version = extracted[2].toUpperCase();
                if(!version.equals("HTTP/1.1")) {
                    sendError(505, "HTTP Version Not Supported");
                    csocket.close();
                    return;
                }

                // Go through headers, and if there is header “Host: xxx”, assign host property
                //   to trimmed value after “Host:”; else, set it to server’s domainName
                //   If xxx is of form some-name:number, just remember “some-name”-part
                for (String header : request) {
                    if (header.startsWith("Host: ")) {
                        host = header.split(":")[1].trim();
                        break;
                    }
                }

                String requestedPath = extracted[1];
                // (path, paramString) = split requestedPath to path and parameterString
                String[] parts = requestedPath.split("\\?");
                String path = parts[0];
                if (parts.length == 2) {
                    String paramString = parts[1];
                    // parseParameters(paramString); ==> your method to fill map parameters
                    parseParameters(paramString);
                }

                internalDispatchRequest(path, true);

            } catch (IOException e) {
                System.out.println("IOException in ClientWorker!");
            } catch (Exception e) {
                System.out.println("Exception in ClientWorker!");
            }
        }

        /**
         * Reads the client's request and returns a list of its headers.
         *
         * @return a list of lines representing the headers of a client's request
         * @throws IOException if there was an issue with reading the request
         */
        private List<String> readRequest() throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int state = 0;

            l:
            while(true) {
                int b = istream.read();
                if (b == -1) return null;
                if (b != 13) {
                    bos.write(b);
                }

                switch(state) {
                    case 0:
                        if (b == 13) { state = 1; } else if (b == 10) state = 4;
                        break;
                    case 1:
                        if (b == 10) { state = 2; } else state = 0;
                        break;
                    case 2:
                        if (b == 13) { state = 3; } else state = 0;
                        break;
                    case 3:
                        if (b == 10) { break l; } else state = 0;
                        break;
                    case 4:
                        if (b == 10) { break l; } else state = 0;
                        break;
                }
            }

            String requestStr = new String(
                    bos.toByteArray(),
                    StandardCharsets.US_ASCII
            );

            return extractHeaders(requestStr);
        }

        /**
         * Extracts the headers from a request and returns them in a list.
         *
         * @param requestStr the request containing the headers
         * @return a list of headers extracted from the given request string
         */
        private List<String> extractHeaders(String requestStr) {
            List<String> headers = new ArrayList<>();
            StringBuilder currentLine = null;

            for (String s : requestStr.split("\n")) {
                if (s.isEmpty()) {
                    break;
                }

                char c = s.charAt(0);

                if (c == 9 || c == 32) {
                    currentLine.append(s);
                } else {
                    if (currentLine != null) {
                        headers.add(currentLine.toString());
                    }
                    currentLine = new StringBuilder(s);
                }
            }

            if (currentLine.length() > 0) {
                headers.add(currentLine.toString());
            }

            return headers;
        }

        /**
         * Sends a specified error to the {@link #ostream}.
         *
         * @param statusCode the code of the error
         * @param statusText the text of the error
         * @throws IOException if there was an issue with writing to the {@link #ostream}
         */
        private void sendError(int statusCode, String statusText) throws IOException {
            ostream.write(
                    ("HTTP/1.1 " + statusCode + " " +statusText + "\r\n" +
                            "Server: SmartHttpServer\r\n" +
                            "Content-Type: text/plain;charset=UTF-8\r\n" +
                            "Content-Length: 0\r\n" +
                            "Connection: close\r\n" +
                            "\r\n").getBytes(StandardCharsets.US_ASCII)
            );
            ostream.flush();
        }

        /**
         * Fills the {@link #params} map with data parsed from a given parameter string.
         *
         * @param parameterString the parameter string to parse
         */
        private void parseParameters(String parameterString) {
            String[] parameterArray = parameterString.split("&");

            for (String parameter : parameterArray) {
                String[] parts = parameter.split("=");

                if (parts.length == 2) {
                    params.put(parts[0], parts[1]);
                }
            }
        }

        /**
         * Returns the extension of a given path.
         *
         * @param path the path whose extension should be returned
         * @return the extension of the given path
         */
        private String getExtension(Path path) {
            String fileName = path.getFileName().toString();
            int lastDot = fileName.lastIndexOf('.');

            return fileName.substring(lastDot + 1);
        }

        private void sendFileToClient(Path file, String mimeType) throws IOException {
            long length = Files.size(file);

            try (InputStream fis = new BufferedInputStream(Files.newInputStream(file))) {
                ostream.write(("HTTP/1.1 200 OK \r\n" +
                        "Server: simple java server\r\n" +
                        "Content-Type: " + mimeType + "\r\n" +
                        "Content-Length: " + length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes(StandardCharsets.US_ASCII)
                );
                ostream.flush();

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) > 0) {
                    ostream.write(buffer, 0, bytesRead);
                }
                ostream.flush();
            }
        }
    }

    /**
     * The main method. Creates and starts the {@link SmartHttpServer}.
     *
     * @param args the command-line arguments, one expected - a path to the server's config file
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Path to config file not provided!");
            System.exit(1);
        }

        new SmartHttpServer(args[0]).start();
    }
}