package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorLabel;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * {@link JVDraw} is an application that enables painting on a canvas
 * and opening / saving / exporting drawings.
 *
 * @author Bruna Dujmović
 *
 */
public class JVDraw extends JFrame {

    /**
     * The current file path.
     */
    private Path currentPath;

    /**
     * The currently used tool.
     */
    private Tool currentTool;

    /**
     * A model of the geometrical objects.
     */
    private DrawingModel model;

    /**
     * A list of geometrical objects on the canvas.
     */
    private JList<GeometricalObject> list;

    /**
     * The canvas to draw on.
     */
    private JDrawingCanvas canvas;

    /**
     * Constructs a {@link JVDraw} frame.
     */
    public JVDraw() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("JVDraw");
        setLocation(10, 10);
        setSize(800, 600);

        initGUI();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
    }

    /**
     * Initializes the GUI.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        model = new DrawingModelImpl();
        list = new JList<>(new DrawingObjectListModel(model));
        list.addMouseListener(listDoubleClick);
        list.addKeyListener(listKeyPress);

        JScrollPane pane = new JScrollPane(list);
        cp.add(pane, BorderLayout.EAST);

        initToolBars();
        initMenu();

        canvas = new JDrawingCanvas(model, () -> currentTool);
        cp.add(canvas, BorderLayout.CENTER);
        canvas.setVisible(true);

        createActions();
    }

    /**
     * Initializes the tool bar and status bar.
     */
    private void initToolBars() {
        // tool bar
        JToolBar toolBar = new JToolBar();

        JColorArea fgColorProvider = new JColorArea(Color.RED);
        JColorArea bgColorProvider = new JColorArea(Color.BLUE);
        toolBar.add(fgColorProvider);
        toolBar.add(bgColorProvider);

        ButtonGroup group = new ButtonGroup();
        JToggleButton lineButton = new JToggleButton("Line");
        JToggleButton circleButton = new JToggleButton("Circle");
        JToggleButton filledCircleButton = new JToggleButton("Filled circle");

        LineTool lineTool = new LineTool(fgColorProvider, model);
        CircleTool circleTool = new CircleTool(fgColorProvider, model);
        FilledCircleTool filledCircleTool = new FilledCircleTool(fgColorProvider, bgColorProvider, model);

        currentTool = lineTool;
        lineButton.addActionListener(e -> {
            currentTool = lineTool;
            canvas.setTool(currentTool);
        });
        circleButton.addActionListener(e -> {
            currentTool = circleTool;
            canvas.setTool(currentTool);
        });
        filledCircleButton.addActionListener(e -> {
            currentTool = filledCircleTool;
            canvas.setTool(currentTool);
        });

        group.add(lineButton);
        toolBar.add(lineButton);
        group.add(circleButton);
        toolBar.add(circleButton);
        group.add(filledCircleButton);
        toolBar.add(filledCircleButton);

        getContentPane().add(toolBar, BorderLayout.NORTH);

        // status bar
        JToolBar statusBar = new JToolBar();

        JColorLabel label = new JColorLabel(fgColorProvider, bgColorProvider);
        statusBar.add(label);

        getContentPane().add(statusBar, BorderLayout.PAGE_END);
    }

    /**
     * Initializes the File menu.
     */
    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        fileMenu.add(new JMenuItem(openAction));
        fileMenu.add(new JMenuItem(saveAction));
        fileMenu.add(new JMenuItem(saveAsAction));
        fileMenu.add(new JMenuItem(exportAction));
        fileMenu.add(new JMenuItem(exitAction));

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Creates the action.
     */
    private void createActions() {
        openAction.putValue(Action.NAME, "Open");
        openAction.putValue(Action.SHORT_DESCRIPTION, "Open file");
        openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

        saveAction.putValue(Action.NAME, "Save");
        saveAction.putValue(Action.SHORT_DESCRIPTION, "Save drawing");
        saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

        saveAsAction.putValue(Action.NAME, "Save as");
        saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Save drawing as");
        saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
        saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);

        exportAction.putValue(Action.NAME, "Export");
        exportAction.putValue(Action.SHORT_DESCRIPTION, "Export drawing as");
        exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
        exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

        exitAction.putValue(Action.NAME, "Exit");
        exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit the app");
        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
    }

    // ------------------------------------------------------------------------------
    // ---------------------------------- Actions -----------------------------------
    // ------------------------------------------------------------------------------

    /**
     * Opens a file and creates a drawing from it.
     */
    private final Action openAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            open();
        }
    };

    /**
     * Saves the current drawing.
     */
    private final Action saveAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            save(false);
        }
    };

    /**
     * Performs save as on the current drawing.
     */
    private final Action saveAsAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            save(true);
        }
    };

    /**
     * Exports the current drawing.
     */
    private final Action exportAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            export();
        }
    };

    /**
     * Exits the application.
     */
    private final Action exitAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            exit();
        }
    };

    // ------------------------------------------------------------------------------
    // ------------------------------ Helper methods --------------------------------
    // ------------------------------------------------------------------------------

    /**
     * A helper method for the open action.
     */
    private void open() {
        if (model.isModified()) {
            int response = JOptionPane.showConfirmDialog(this,
                    "This document has unsaved changes. Do you wish to save them?",
                    "Unsaved changes found", JOptionPane.YES_NO_CANCEL_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                save(false);
            }
        }

        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Open file");
        if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path filePath = jfc.getSelectedFile().toPath();
        if (!Files.isReadable(filePath)) {
            JOptionPane.showMessageDialog(this,
                    "The file " + filePath + " is not readable!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            model.clear();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ", 2);

                switch (parts[0]) {
                    case "LINE":
                        model.add(Line.fromString(parts[1]));
                        break;
                    case "CIRCLE":
                        model.add(Circle.fromString(parts[1]));
                        break;
                    case "FCIRCLE":
                        model.add(FilledCircle.fromString(parts[1]));
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Invalid geometric object name!");
                }
            }

        } catch (IllegalArgumentException | IOException exc) {
            JOptionPane.showMessageDialog(this, exc.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        currentPath = filePath;
    }

    /**
     * A helper method for the save and save as actions.
     *
     * @param isSaveAs {@code true} if save as should be performed
     */
    private void save(boolean isSaveAs) {
        Path savePath = currentPath;

        if (isSaveAs || savePath == null) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save drawing");
            if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(this, "Nothing was saved!",
                        "Save drawing aborted", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            savePath = jfc.getSelectedFile().toPath();
            if (Files.exists(savePath)) {
                int response = JOptionPane.showConfirmDialog(
                        this, "A file already exists on the path " + savePath +
                                ".\nDo you wish to overwrite it?",
                        "Target File Already Exists", JOptionPane.YES_NO_OPTION);

                if (response == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "Nothing was saved!",
                            "Save drawing aborted", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }

        try (BufferedWriter bw = Files.newBufferedWriter(savePath)) {
            GeometricalObjectWriter visitor = new GeometricalObjectWriter(bw);

            for (int i = 0, size = model.getSize(); i < size; i++) {
                GeometricalObject object = model.getObject(i);
                object.accept(visitor);
            }

        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        model.clearModifiedFlag();
    }

    /**
     * A helper method for the export action.
     */
    private void export() {
        GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
        for (int i = 0, size = model.getSize(); i < size; i++) {
            GeometricalObject object = model.getObject(i);
            object.accept(bbcalc);
        }
        Rectangle box = bbcalc.getBoundingBox();

        BufferedImage image = new BufferedImage(
                box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
        );

        Graphics2D g = image.createGraphics();
        g.translate(-box.x, -box.y);

        GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
        for (int i = 0, size = model.getSize(); i < size; i++) {
            GeometricalObject object = model.getObject(i);
            object.accept(painter);
        }
        g.dispose();

        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save drawing");
        if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this, "Nothing was saved!",
                    "Save drawing aborted", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        File file = jfc.getSelectedFile();
        if (Files.exists(file.toPath())) {
            int response = JOptionPane.showConfirmDialog(
                    this, "A file already exists on the path " + file.toPath() +
                            ".\nDo you wish to overwrite it?",
                    "Target File Already Exists", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "Nothing was saved!",
                        "Save drawing aborted", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        try {
            String fileName = file.toPath().getFileName().toString();
            if (!fileName.endsWith(".jpg") && !fileName.equals(".png")
                    && !fileName.equals(".gif")) {
                throw new RuntimeException("Invalid extension!");
            }
            ImageIO.write(image, fileName.substring(fileName.length() - 3), file);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Exported successfully.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * A helper method for the exit action.
     */
    private void exit() {
        if (model.isModified()) {
            int response = JOptionPane.showConfirmDialog(this,
                    "This document has unsaved changes. Do you wish to save them?",
                    "Unsaved changes found", JOptionPane.YES_NO_CANCEL_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                save(false);
            }
        }

        JVDraw.this.dispose();
    }

    // ------------------------------------------------------------------------------
    // --------------------------------- Listeners ----------------------------------
    // ------------------------------------------------------------------------------

    /**
     * A listener for double clicks on the {@link JList} component.
     */
    private final MouseListener listDoubleClick = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() != 2) {
                return;
            }

            GeometricalObject clicked = model.getObject(list.getSelectedIndex());
            GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
            editor.setPreferredSize(new Dimension(300, 300));

            if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit",
                    JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    editor.checkEditing();
                    editor.acceptEditing();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };

    /**
     * A listener for + / - / DEL key presses on the {@link JList} component.
     */
    private final KeyListener listKeyPress = new KeyAdapter() {

        @Override
        public void keyPressed(KeyEvent e) {
            GeometricalObject selected = model.getObject(list.getSelectedIndex());
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_PLUS) {
                model.changeOrder(selected, 1);

            } else if (keyCode == KeyEvent.VK_MINUS) {
                model.changeOrder(selected, -1);

            } else if (keyCode == KeyEvent.VK_DELETE) {
                model.remove(selected);
            }
        }
    };

    // ------------------------------------------------------------------------------
    // ------------------------------- Main program ---------------------------------
    // ------------------------------------------------------------------------------

    /**
     * The main method. Starts the program.
     *
     * @param args the command-line arguments, not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame drawFrame = new JVDraw();
            drawFrame.setVisible(true);
        });
    }
}
