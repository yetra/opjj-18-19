package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates the {@link SmartScriptEngine} class by executing the demo scripts and
 * printing the results to the console.
 *
 * @author Bruna Dujmović
 *
 */
public class SmartScriptEngineDemo {

    /**
     * The main method. Executes the given demo scripts and prints the result to the
     * console.
     *
     * @param args the command-line arguments, not used
     */
    public static void main(String[] args) {
        osnovniDemo();
        System.out.println("\n-------------------------------------------------\n");

        zbrajanjeDemo();
        System.out.println("\n-------------------------------------------------\n");

        brojPozivaDemo();
        System.out.println("\n-------------------------------------------------\n");

        fibonacciDemo();
        System.out.println("\n-------------------------------------------------\n");

        fibonaccihDemo();
        System.out.println("\n-------------------------------------------------\n");
    }

    /*
     * ... zaglavlje ...
     *
     * This is sample text.
     *
     *   This is 1-th time this message is generated.
     *
     *   This is 2-th time this message is generated.
     *
     *   This is 3-th time this message is generated.
     *   ...
     *
     *   sin(0^2) = 0.000
     *
     *   sin(2^2) = 0.070
     *   ...
     */
    /**
     * Reads and executes the osnovni.smscr smart script.
     */
    private static void osnovniDemo() {
        String documentBody = readFromDisk("osnovni.smscr");
        Map<String,String> parameters = new HashMap<>();
        Map<String,String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();

        // create engine and execute it
        new SmartScriptEngine(
                new SmartScriptParser(documentBody).getDocumentNode(),
                new RequestContext(System.out, parameters, persistentParameters, cookies)
        ).execute();
    }

    /*
     * ... zaglavlje ...
     *
     * Računam sumu brojeva:
     * a=4, b=2, rezultat=6
     */
    /**
     * Reads and executes the zbrajanje.smscr smart script.
     */
    private static void zbrajanjeDemo() {
        String documentBody = readFromDisk("zbrajanje.smscr");
        Map<String,String> parameters = new HashMap<>();
        Map<String,String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();
        parameters.put("a", "4");
        parameters.put("b", "2");

        // create engine and execute it
        new SmartScriptEngine(
                new SmartScriptParser(documentBody).getDocumentNode(),
                new RequestContext(System.out, parameters, persistentParameters, cookies)
        ).execute();
    }

    /*
     * HTTP/1.1 200 OK
     * Content-Type: text/plain; charset=UTF-8
     *
     * Ovaj dokument pozvan je sljedeći broj puta:
     * 3
     * Vrijednost u mapi: 4
     */
    /**
     * Reads and executes the brojPoziva.smscr smart script.
     */
    private static void brojPozivaDemo() {
        String documentBody = readFromDisk("brojPoziva.smscr");
        Map<String,String> parameters = new HashMap<>();
        Map<String,String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();
        persistentParameters.put("brojPoziva", "3");
        RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);

        // create engine and execute it
        new SmartScriptEngine(
                new SmartScriptParser(documentBody).getDocumentNode(), rc
        ).execute();
        System.out.println("\nVrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));
    }

    /*
     * ... zaglavlje ...
     *
     * Prvih 10 fibonaccijevih brojeva je:
     * 0
     * 1
     * 1
     * 2
     * 3
     * 5
     * 8
     * 13
     * 21
     * 34
     */
    /**
     * Reads and executes the fibonacci.smscr smart script.
     */
    private static void fibonacciDemo() {
        String documentBody = readFromDisk("fibonacci.smscr");
        Map<String,String> parameters = new HashMap<>();
        Map<String,String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();

        // create engine and execute it
        new SmartScriptEngine(
                new SmartScriptParser(documentBody).getDocumentNode(),
                new RequestContext(System.out, parameters, persistentParameters, cookies)
        ).execute();
    }

    /**
     * Reads and executes the fibonaccih.smscr smart script.
     */
    private static void fibonaccihDemo() {
        String documentBody = readFromDisk("fibonaccih.smscr");
        Map<String,String> parameters = new HashMap<>();
        Map<String,String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();

        // create engine and execute it
        new SmartScriptEngine(
                new SmartScriptParser(documentBody).getDocumentNode(),
                new RequestContext(System.out, parameters, persistentParameters, cookies)
        ).execute();
    }

    /**
     * Returns a string representation of the document read from a given path.
     *
     * @param path the path to the document
     * @return a string representation of the document read from the given path
     */
    private static String readFromDisk(String path) {
        String documentBody = null;

        try {
            documentBody = Files.readString(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Can't read from the given path!");
            System.exit(1);
        }

        return documentBody;
    }
}
