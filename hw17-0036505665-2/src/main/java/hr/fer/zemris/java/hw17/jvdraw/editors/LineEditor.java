package hr.fer.zemris.java.hw17.jvdraw.editors;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

import javax.swing.*;
import java.awt.*;

/**
 * A class for supporting the editing of {@link Line} properties.
 *
 * @author Bruna Dujmović
 *
 */
public class LineEditor extends GeometricalObjectEditor {

    /**
     * A {@link JTextField} for inputting the start x-coordinate.
     */
    private JTextField startXField;
    /**
     * A {@link JTextField} for inputting the start y-coordinate.
     */
    private JTextField startYField;

    /**
     * A {@link JTextField} for inputting the end x-coordinate.
     */
    private JTextField endXField;
    /**
     * A {@link JTextField} for inputting the end y-coordinate.
     */
    private JTextField endYField;

    /**
     * The color chosen on the {@link JColorChooser}.
     */
    private Color chosenColor;

    /**
     * The line to edit.
     */
    private Line line;

    /**
     * Constructs a {@link LineEditor} for the given line.
     *
     * @param line the line to edit
     */
    public LineEditor(Line line) {
        this.line = line;

        this.setLayout(new GridLayout(5, 1));

        startXField = new JTextField(line.getStart().x);
        this.add(startXField);

        startYField = new JTextField(line.getStart().y);
        this.add(startYField);

        endXField = new JTextField(line.getEnd().x);
        this.add(endXField);

        endYField = new JTextField(line.getEnd().y);
        this.add(endYField);

        JButton colorChooserButton = new JButton("Choose color");
        colorChooserButton.addActionListener(e ->
                chosenColor = JColorChooser.showDialog(LineEditor.this,
                        "Choose color", line.getLineColor()));
        this.add(colorChooserButton);
    }

    @Override
    public void checkEditing() {
        try {
            Integer.parseInt(startXField.getText());
            Integer.parseInt(startYField.getText());
            Integer.parseInt(endXField.getText());
            Integer.parseInt(endYField.getText());

            // TODO check bounds

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Values cannot be parsed to integers!");
        }
    }

    @Override
    public void acceptEditing() {
        line.setStart(new Point(
                Integer.parseInt(startXField.getText()),
                Integer.parseInt(startYField.getText())
        ));

        line.setEnd(new Point(
                Integer.parseInt(endXField.getText()),
                Integer.parseInt(endYField.getText())
        ));

        line.setLineColor(chosenColor);
    }
}
