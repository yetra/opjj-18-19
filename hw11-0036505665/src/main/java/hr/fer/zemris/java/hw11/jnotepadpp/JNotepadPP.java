package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.function.UnaryOperator;

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
     * A {@link FormLocalizationProvider} for the localization of this {@link JFrame}.
     */
    private FormLocalizationProvider flp = new FormLocalizationProvider(
            LocalizationProvider.getInstance(), this
    );

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
            public void currentDocumentChanged(SingleDocumentModel previousModel,
                                               SingleDocumentModel currentModel) {
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
        newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
        newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
        newDocument.putValue(Action.SHORT_DESCRIPTION, "Create new document");

        openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        openDocument.putValue(Action.SHORT_DESCRIPTION, "Open document from disc");

        saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save document to disc");
        saveDocument.setEnabled(false);

        saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
        saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveAsDocument.putValue(Action.SHORT_DESCRIPTION, "Save document to disc");
        saveAsDocument.setEnabled(false);

        closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
        closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        closeDocument.putValue(Action.SHORT_DESCRIPTION, "Close current document without saving");
        closeDocument.setEnabled(false);

        exitNotepad.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
        exitNotepad.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        exitNotepad.putValue(Action.SHORT_DESCRIPTION, "Exit from JNotepad++");

        switchToCroatian.putValue(Action.NAME, "hr");
        switchToCroatian.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
        switchToCroatian.putValue(Action.SHORT_DESCRIPTION, "Hrvatski");

        switchToEnglish.putValue(Action.NAME, "en");
        switchToEnglish.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        switchToEnglish.putValue(Action.SHORT_DESCRIPTION, "English");

        switchToGerman.putValue(Action.NAME, "de");
        switchToGerman.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        switchToGerman.putValue(Action.SHORT_DESCRIPTION, "Deutsch");

        toUpperCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
        toUpperCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
        toUpperCase.putValue(Action.SHORT_DESCRIPTION, "Converts selection to upper case");

        toLowerCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
        toLowerCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
        toLowerCase.putValue(Action.SHORT_DESCRIPTION, "Converts selection to lower case");

        invertCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
        invertCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
        invertCase.putValue(Action.SHORT_DESCRIPTION, "Inverts case of selection");
    }

    /**
     * Creates {@link JNotepadPP}'s menu bar and menus.
     */
    private void createMenus() {
        JMenuBar mb = new JMenuBar();

        JMenu file = new JMenu(fileMenu);
        mb.add(file);
        file.add(new JMenuItem(newDocument));
        file.add(new JMenuItem(openDocument));
        file.add(new JMenuItem(closeDocument));
        file.addSeparator();
        file.add(new JMenuItem(saveDocument));
        file.add(new JMenuItem(saveAsDocument));
        file.addSeparator();
        file.add(new JMenuItem(exitNotepad));

        JMenu edit = new JMenu(editMenu);
        mb.add(edit);
        edit.add(new JMenuItem(cutAction));
        edit.add(new JMenuItem(copyAction));
        edit.add(new JMenuItem(pasteAction));

        JMenu tools = new JMenu(toolsMenu);
        mb.add(tools);
        JMenu changeCase = new JMenu(changeCaseSubmenu);
        tools.add(changeCase);
        changeCase.add(new JMenuItem(toUpperCase));
        changeCase.add(new JMenuItem(toLowerCase));
        changeCase.add(new JMenuItem(invertCase));
        JMenu sort = new JMenu(sortSubmenu);
        tools.add(sort);
        sort.add(new JMenuItem(ascendingSort));
        sort.add(new JMenuItem(descendingSort));
        tools.add(new JMenuItem(uniqueAction));

        JMenu languages = new JMenu(languagesMenu);
        mb.add(languages);
        languages.add(new JMenuItem(switchToCroatian));
        languages.add(new JMenuItem(switchToEnglish));
        languages.add(new JMenuItem(switchToGerman));

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
        CompoundBorder cellBorder = BorderFactory.createCompoundBorder(
                new MatteBorder(0, 1, 0, 0, Color.GRAY), new EmptyBorder(0, 4, 0, 4)
        );

        JPanel sb = new JPanel(new GridLayout(0, 3));
        sb.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        sb.setPreferredSize(new Dimension(getWidth(), 24));

        JLabel lengthLabel = new JLabel("length: 0");
        lengthLabel.setBorder(cellBorder);
        sb.add(lengthLabel);
        JLabel caretInfoLabel = new JLabel("Ln: 0  Col: 0  Sel: 0");
        caretInfoLabel.setBorder(cellBorder);
        sb.add(caretInfoLabel);

        // update length & caret info labels on tab switch
        mdm.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel,
                                               SingleDocumentModel currentModel) {
                // TODO remove listener ?
                showTextContentInfo(lengthLabel, caretInfoLabel);
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {}

            @Override
            public void documentRemoved(SingleDocumentModel model) {}
        });

        JLabel clockLabel = new JLabel("");
        clockLabel.setHorizontalAlignment(JLabel.RIGHT);
        clockLabel.setBorder(cellBorder);
        sb.add(clockLabel);
        showClock(clockLabel);

        return sb;
    }

    /*
     * ---------------------------------------------------------------------------
     * --------------------------------- Actions ---------------------------------
     * ---------------------------------------------------------------------------
     */

    /**
     * Expands the file {@link JMenu}.
     */
    private final Action fileMenu = new LocalizableAction("file", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {}
    };

    /**
     * Expands the edit {@link JMenu}.
     */
    private final Action editMenu = new LocalizableAction("edit", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {}
    };

    /**
     * Expands the languages {@link JMenu}.
     */
    private final Action languagesMenu = new LocalizableAction("languages", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {}
    };

    /**
     * Expands the tools {@link JMenu}.
     */
    private final Action toolsMenu = new LocalizableAction("tools", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {}
    };

    /**
     * Expands the change case {@link JMenu}.
     */
    private final Action changeCaseSubmenu = new LocalizableAction("changecase", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {}
    };

    /**
     * Expands the sort {@link JMenu}.
     */
    private final Action sortSubmenu = new LocalizableAction("sort", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {}
    };

    /**
     * Creates a new blank document.
     */
    private final Action newDocument = new LocalizableAction("new", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            mdm.createNewDocument();
        }
    };

    /**
     * Opens an existing document.
     */
    private final Action openDocument = new LocalizableAction("open", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            openDocument();
        }
    };

    /**
     * Saves the current document.
     */
    private final Action saveDocument = new LocalizableAction("save", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveDocument(false);
        }
    };

    /**
     * Performs save-as on the current document.
     */
    private final Action saveAsDocument = new LocalizableAction("saveas", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveDocument(true);
        }
    };

    /**
     * Closes the current document.
     */
    private final Action closeDocument = new LocalizableAction("close", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            mdm.closeDocument(mdm.getCurrentDocument());
        }
    };

    /**
     * Cuts the selected text and places its contents into the system clipboard.
     */
    private final Action cutAction = new LocalizableAction("cut", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            new DefaultEditorKit.CutAction().actionPerformed(e);
        }
    };

    /**
     * Copies the contents of the selected text to the system clipboard.
     */
    private final Action copyAction = new LocalizableAction("copy", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            new DefaultEditorKit.CopyAction().actionPerformed(e);
        }
    };

    /**
     * Pastes the contents of the system clipboard onto the selection or before the
     * caret, if nothing is selected.
     */
    private final Action pasteAction = new LocalizableAction("paste", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            new DefaultEditorKit.PasteAction().actionPerformed(e);
        }
    };

    /**
     * Displays statistical info on the current documents.
     */
    private final Action showStatistics = new LocalizableAction("statistics", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            showStatistics();
        }
    };

    /**
     * Exits the program after checking if modified files wish to be saved.
     */
    private final Action exitNotepad = new LocalizableAction("exit", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            exitNotepad();
        }
    };

    /**
     * Switches the language to English.
     */
    private final Action switchToEnglish = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("en");
        }
    };

    /**
     * Switches the language to Croatian.
     */
    private final Action switchToCroatian = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("hr");
        }
    };

    /**
     * Switches the language to German.
     */
    private final Action switchToGerman = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("de");
        }
    };

    /**
     * Converts the selected text to upper case.
     */
    private final Action toUpperCase = new LocalizableAction("upper", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeCase(String::toUpperCase);
        }
    };

    /**
     * Converts the selected text to lower case.
     */
    private final Action toLowerCase = new LocalizableAction("lower", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeCase(String::toLowerCase);
        }
    };

    /**
     * Inverts the case of the selected text.
     */
    private final Action invertCase = new LocalizableAction("invert", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeCase(text -> invertCase(text));
        }
    };

    /**
     * Sorts the selected lines in ascending order.
     */
    private final Action ascendingSort = new LocalizableAction("ascending", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeSelectedLines(String::compareTo);
        }
    };

    /**
     * Sorts the selected lines in descending order.
     */
    private final Action descendingSort = new LocalizableAction("descending", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            changeSelectedLines(Comparator.reverseOrder());
        }
    };

    /**
     * Removes duplicates of the selected line.
     */
    private final Action uniqueAction = new LocalizableAction("unique", flp) {
        @Override
        public void actionPerformed(ActionEvent e) {
            uniqueLines();
        }
    };

    /*
     * ---------------------------------------------------------------------------
     * ----------------------------- Helper methods ------------------------------
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

    /**
     * Displays and periodically updates a clock of the format yyyy/MM/dd HH:mm:ss on
     * the given label.
     *
     * @param label the label on which to display the clock
     */
    private void showClock(JLabel label) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        Thread clock = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignorable) {}

                SwingUtilities.invokeLater(() ->
                        label.setText(formatter.format(LocalDateTime.now()))
                );
            }
        });
        clock.setDaemon(true);
        clock.start();
    }

    /**
     * Displays text content info for the currently open document on the specified
     * labels.
     *
     * @param lengthLabel the label on which to display the length of the text
     * @param caretInfoLabel the label on which to display caret info (line, column,
     *                       selection length)
     */
    private void showTextContentInfo(JLabel lengthLabel, JLabel caretInfoLabel) {
        JTextComponent component = mdm.getCurrentDocument().getTextComponent();
        Document doc = component.getDocument();
        Element root = doc.getDefaultRootElement();

        component.addCaretListener(e -> {
            lengthLabel.setText("length: " + component.getText().length());

            int pos = component.getCaretPosition();
            int ln = root.getElementIndex(pos) + 1;
            int col = pos - root.getElement(ln - 1).getStartOffset() + 1;
            int sel = component.getSelectionEnd() - component.getSelectionStart();
            caretInfoLabel.setText("Ln: " + ln + "  Col: " + col + "  Sel: " + sel);
        });
    }

    /**
     * Changes the case of the current document text based on the given {@link
     * UnaryOperator}.
     *
     * @param convert the {@link UnaryOperator} that specifies how to change the case
     */
    private void changeCase(UnaryOperator<String> convert) {
        JTextComponent component = mdm.getCurrentDocument().getTextComponent();
        Document doc = component.getDocument();

        int start = component.getSelectionStart();
        int end = component.getSelectionEnd();
        if (end - start < 1) {
            return;
        }

        try {
            String text = doc.getText(start, end - start);

            doc.remove(start, end - start);
            doc.insertString(start, convert.apply(text), null);

            component.setSelectionStart(start);
            component.setSelectionEnd(end);
        } catch (BadLocationException ignorable) {}
    }

    /**
     * Inverts the case of the given text and returns it.
     *
     * @param text the text to be inverted
     * @return the given text in inverted case
     */
    private String invertCase(String text) {
        char[] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                chars[i] = Character.toLowerCase(chars[i]);
            } else if (Character.isLowerCase(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
            }
        }

        return new String(chars);
    }

    /**
     * Sorts the selected lines.
     *
     * @param comparator the comparator used for sorting
     */
    private void changeSelectedLines(Comparator<? super String> comparator) {
        JTextComponent component = mdm.getCurrentDocument().getTextComponent();
        Document doc = component.getDocument();

        int start = component.getSelectionStart();
        int end = component.getSelectionEnd();
        if (end - start < 1) {
            return;
        }

        Element root = doc.getDefaultRootElement();
        int startLine = root.getElementIndex(start);
        int endLine = root.getElementIndex(end);

        try {
            List<String> lines = new ArrayList<>();
            for (int line = startLine; line <= endLine; line++) {
                Element lineElement = root.getElement(line);
                int lineStart = lineElement.getStartOffset();
                int lineEnd = lineElement.getEndOffset();

                String text = doc.getText(lineStart, lineEnd - lineStart);
                lines.add(text);
            }
            lines.sort(comparator);

            start = root.getElement(startLine).getStartOffset();
            end = root.getElement(endLine).getEndOffset();
            doc.remove(start, end - start);
            doc.insertString(start, String.join("", lines), null);
        } catch (BadLocationException ignorable) {}
    }

    /**
     * Removes duplicate lines from the selection.
     */
    private void uniqueLines() {
        JTextComponent component = mdm.getCurrentDocument().getTextComponent();
        Document doc = component.getDocument();

        int start = component.getSelectionStart();
        int end = component.getSelectionEnd();
        if (end - start < 1) {
            return;
        }

        Element root = doc.getDefaultRootElement();
        int startLine = root.getElementIndex(start);
        int endLine = root.getElementIndex(end);

        try {
            Set<String> lines = new LinkedHashSet<>();
            for (int line = startLine; line <= endLine; line++) {
                Element lineElement = root.getElement(line);
                int lineStart = lineElement.getStartOffset();
                int lineEnd = lineElement.getEndOffset();

                String text = doc.getText(lineStart, lineEnd - lineStart);
                lines.add(text);
            }

            start = root.getElement(startLine).getStartOffset();
            end = root.getElement(endLine).getEndOffset();
            doc.remove(start, end - start);
            doc.insertString(start, String.join("", lines), null);
        } catch (BadLocationException ignorable) {}
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
