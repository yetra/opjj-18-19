package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.awt.*;

/**
 * {@link JNotepadPP} is a simple text file editor that supports working with multiple
 * documents at the same time.
 *
 * It provides the following functionality:
 *  - creating new blank documents
 *  - opening existing documents
 *  - saving and saving-as documents
 *  - cut / copy / paste of document text
 *  - showing statistical info on the documents
 *  - exiting from the application
 *
 * @author Bruna Dujmović
 *
 */
public class JNotepadPP extends JFrame {

    /**
     * The {@link DefaultMultipleDocumentModel} object that contains all the currently
     * open documents.
     */
    private DefaultMultipleDocumentModel mdm;

    /**
     * Constructs an {@link JNotepadPP} frame and initializes its components.
     */
    public JNotepadPP() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(10, 10);
        setSize(500, 500);

        initGUI();
    }

    /*
     * --------------------------------------------------------------------------
     * --------------------------- GUI initialization ---------------------------
     * --------------------------------------------------------------------------
     */

    /**
     * Initializes the components of the {@link JNotepadPP} frame.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        mdm = new DefaultMultipleDocumentModel();
        cp.add(mdm, BorderLayout.CENTER);

        createActions();
        createMenus();
        cp.add(createToolBar(), BorderLayout.PAGE_START);
    }

    /**
     * Creates the actions to be performed when corresponding events occur on
     * subscribed GUI components.
     */
    private void createActions() {

    }

    /**
     * Creates {@link JNotepadPP}'s menu bar and menus.
     */
    private void createMenus() {

    }

    /**
     * Creates and returns {@link JNotepadPP}'s toolbar.
     *
     * @return {@link JNotepadPP}'s toolbar
     */
    private JToolBar createToolBar() {
        return null;
    }

    /*
     * ---------------------------------------------------------------------------
     * ------------------------------ Main program -------------------------------
     * ---------------------------------------------------------------------------
     */

    /**
     * The main method. Creates an instance of {@link JNotepadPP} and displays it.
     *
     * @param args the command-line arguments, not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }
}
