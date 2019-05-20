package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;

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
        setTitle("JNotepad++");
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
        cp.add(createStatusBar(), BorderLayout.PAGE_END);

        // check modified files on exit
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitNotepad();
            }
        });

        // update window title on tab change
        mdm.addMultipleDocumentListener(new MultipleDocumentListener() {

            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                String title = "JNotepad++";

                if (currentModel != null) {
                    Path currentDocumentPath = currentModel.getFilePath();
                    title = (currentDocumentPath == null ?
                            "(unnamed)" : currentDocumentPath.toString() + "")
                            + " - " + title;
                }

                setTitle(title);
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {}

            @Override
            public void documentRemoved(SingleDocumentModel model) {}
        });

        // disable Save/Save As/Close document actions when no tab present
        mdm.addChangeListener(e -> {
            boolean hasTabs = mdm.getNumberOfDocuments() != 0;

            saveDocument.setEnabled(hasTabs);
            saveAsDocument.setEnabled(hasTabs);
            closeDocument.setEnabled(hasTabs);
        });
    }

    /**
     * Creates the actions to be performed when corresponding events occur on
     * subscribed GUI components.
     */
    private void createActions() {
        newDocument.putValue(Action.NAME, "New");
        newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
        newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
        newDocument.putValue(Action.SHORT_DESCRIPTION, "Create new document");

        openDocument.putValue(Action.NAME, "Open");
        openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        openDocument.putValue(Action.SHORT_DESCRIPTION, "Open document from disc");

        saveDocument.putValue(Action.NAME, "Save");
        saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save document to disc");
        saveDocument.setEnabled(false);

        saveAsDocument.putValue(Action.NAME, "Save As");
        saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
        saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveAsDocument.putValue(Action.SHORT_DESCRIPTION, "Save document to disc");
        saveAsDocument.setEnabled(false);

        closeDocument.putValue(Action.NAME, "Close");
        closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
        closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        closeDocument.putValue(Action.SHORT_DESCRIPTION, "Close current document without saving");
        closeDocument.setEnabled(false);

        exitNotepad.putValue(Action.NAME, "Exit");
        exitNotepad.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
        exitNotepad.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        exitNotepad.putValue(Action.SHORT_DESCRIPTION, "Exit from JNotepad++");

        cutAction.putValue(Action.NAME, "Cut");

        copyAction.putValue(Action.NAME, "Copy");

        pasteAction.putValue(Action.NAME, "Paste");

        showStatistics.putValue(Action.NAME, "Statistics");
    }

    /**
     * Creates {@link JNotepadPP}'s menu bar and menus.
     */
    private void createMenus() {
        JMenuBar mb = new JMenuBar();

        JMenu file = new JMenu("File");
        mb.add(file);
        file.add(new JMenuItem(newDocument));
        file.add(new JMenuItem(openDocument));
        file.add(new JMenuItem(closeDocument));
        file.addSeparator();
        file.add(new JMenuItem(saveDocument));
        file.add(new JMenuItem(saveAsDocument));
        file.addSeparator();
        file.add(new JMenuItem(exitNotepad));

        JMenu edit = new JMenu("Edit");
        mb.add(edit);
        edit.add(new JMenuItem(cutAction));
        edit.add(new JMenuItem(copyAction));
        edit.add(new JMenuItem(pasteAction));

        setJMenuBar(mb);
    }

    /**
     * Creates and returns {@link JNotepadPP}'s toolbar.
     *
     * @return {@link JNotepadPP}'s toolbar
     */
    private JToolBar createToolBar() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(true);

        tb.add(new JButton(cutAction));
        tb.add(new JButton(copyAction));
        tb.add(new JButton(pasteAction));
        tb.add(new JButton(showStatistics));

        return tb;
    }

    /**
     * Creates and returns {@link JNotepadPP}'s status bar.
     *
     * @return {@link JNotepadPP}'s status bar
     */
    private JPanel createStatusBar() {
        JPanel sb = new JPanel(new GridLayout(0, 3));
        sb.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        sb.setPreferredSize(new Dimension(getWidth(), 24));

        sb.add(new JLabel("Status bar"));

        return sb;
    }

    /*
     * ---------------------------------------------------------------------------
     * --------------------------------- Actions ---------------------------------
     * ---------------------------------------------------------------------------
     */

    /**
     * Creates a new blank document.
     */
    private final Action newDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            mdm.createNewDocument();
        }
    };

    /**
     * Opens an existing document.
     */
    private final Action openDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            openDocument();
        }
    };

    /**
     * Saves the current document.
     */
    private final Action saveDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveDocument(false);
        }
    };

    /**
     * Performs save-as on the current document.
     */
    private final Action saveAsDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveDocument(true);
        }
    };

    /**
     * Closes the current document.
     */
    private final Action closeDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            mdm.closeDocument(mdm.getCurrentDocument());
        }
    };

    /**
     * Cuts the selected text and places its contents into the system clipboard.
     */
    private final Action cutAction = new DefaultEditorKit.CutAction();

    /**
     * Copies the contents of the selected text to the system clipboard.
     */
    private final Action copyAction = new DefaultEditorKit.CopyAction();

    /**
     * Pastes the contents of the system clipboard onto the selection or before the
     * caret, if nothing is selected.
     */
    private final Action pasteAction = new DefaultEditorKit.PasteAction();

    /**
     * Displays statistical info on the current documents.
     */
    private final Action showStatistics = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showStatistics();
        }
    };

    /**
     * Exits the program after checking if modified files wish to be saved.
     */
    private final Action exitNotepad = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            exitNotepad();
        }
    };

    /*
     * ---------------------------------------------------------------------------
     * ----------------------------- Action methods ------------------------------
     * ---------------------------------------------------------------------------
     */

    /**
     * A helper method for performing the {@link #openDocument} action.
     */
    private void openDocument() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Open file");
        if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path filePath = jfc.getSelectedFile().toPath();
        if (!Files.isReadable(filePath)) {
            JOptionPane.showMessageDialog(
                    this,
                    "The file " + filePath + " is not readable!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            mdm.loadDocument(filePath);

        } catch (IllegalArgumentException exc) {
            JOptionPane.showMessageDialog(
                    this,
                    exc.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * A helper method for performing the {@link #saveDocument} and {@link #saveAsDocument}
     * actions.
     *
     * @param isSaveAs {@code true} if save-as is to be performed
     */
    private void saveDocument(boolean isSaveAs) {
        Path savePath = mdm.getCurrentDocument().getFilePath();

        if (isSaveAs || savePath == null) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save document");
            if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nothing was saved!",
                        "Save Document Aborted",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            savePath = jfc.getSelectedFile().toPath();
            if (Files.exists(savePath)) {
                int response = JOptionPane.showConfirmDialog(
                        this,
                        "A file already exists on the path " + savePath +
                                ".\nDo you wish to overwrite it?",
                        "Target File Already Exists",
                        JOptionPane.YES_NO_OPTION
                );

                if (response == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Nothing was saved!",
                            "Save Document Aborted",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
            }
        }

        try {
            mdm.saveDocument(mdm.getCurrentDocument(), savePath);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * A helper method for performing the {@link #showStatistics} action.
     */
    private void showStatistics() {
        if (mdm.getNumberOfDocuments() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "No open documents!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String documentText = mdm.getCurrentDocument().getTextComponent().getText();

        int numberOfChars = documentText.length();
        int numberOfNonBlankChars = documentText.replaceAll("\\s+", "").length();
        long numberOfLines = documentText.lines().count();

        JOptionPane.showMessageDialog(
                this,
                "Your document has " + numberOfChars + " characters, "
                        + numberOfNonBlankChars + " non-blank characters, and "
                        + numberOfLines + " lines.",
                "Statistics",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * A helper method for the {@link #exitNotepad} action.
     */
    private void exitNotepad() {
        for (SingleDocumentModel document : mdm) {
            if (document.isModified()) {
                int response = JOptionPane.showConfirmDialog(
                        this,
                        "This document has unsaved changes. Do you wish to save them?",
                        "Unsaved changes found",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );

                if (response == JOptionPane.CANCEL_OPTION) {
                    break;
                }

                if (response == JOptionPane.YES_OPTION) {
                    saveDocument(false);
                }
            }
        }

        dispose();
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
