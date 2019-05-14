package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import java.awt.*;

/**
 * This program displays a frame containing two lists and the button "Next". The lists
 * display prime numbers that are incrementally generated when "Next" is clicked.
 *
 * @author Bruna Dujmović
 *
 */
public class PrimDemo extends JFrame {

    /**
     * Creates a new {@link PrimDemo} frame.
     */
    public PrimDemo() {
        setLocation(20, 50);
        setSize(300, 200);
        setTitle("PrimDemo");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initGUI();
    }

    /**
     * Initlializes the frame.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        PrimListModel model = new PrimListModel();

        JList<Integer> list1 = new JList<>(model);
        JList<Integer> list2 = new JList<>(model);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> model.next());

        JPanel centerPanel = new JPanel(new GridLayout(1, 0));
        centerPanel.add(new JScrollPane(list1));
        centerPanel.add(new JScrollPane(list2));

        cp.add(centerPanel, BorderLayout.CENTER);
        cp.add(nextButton, BorderLayout.PAGE_END);
    }

    /**
     * The main method. Creates and displays a {@link PrimDemo} frame.
     *
     * @param args the command-line arguments, not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame demoFrame = new PrimDemo();
            demoFrame.setVisible(true);
        });
    }
}
