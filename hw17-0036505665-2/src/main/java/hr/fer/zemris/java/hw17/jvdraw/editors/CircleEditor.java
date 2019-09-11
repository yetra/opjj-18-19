package hr.fer.zemris.java.hw17.jvdraw.editors;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;

import javax.swing.*;
import java.awt.*;

/**
 * A class for supporting the editing of {@link Circle} properties.
 *
 * @author Bruna Dujmović
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

    /**
     * A {@link JTextField} for inputting the center x-coordinate.
     */
    private JTextField centerXField;
    /**
     * A {@link JTextField} for inputting the center y-coordinate.
     */
    private JTextField centerYField;

    /**
     * A {@link JTextField} for inputting the radius.
     */
    private JTextField radiusField;

    /**
     * The color chosen on the {@link JColorChooser}.
     */
    private Color chosenColor;

    /**
     * The circle to edit.
     */
    private Circle circle;

    /**
     * Constructs a {@link CircleEditor} for the given circle.
     *
     * @param circle the circle to edit
     */
    public CircleEditor(Circle circle) {
        this.circle = circle;
        this.chosenColor = circle.getLineColor();

        this.setLayout(new GridLayout(5, 1));

        centerXField = new JTextField(Integer.toString(circle.getCenter().x));
        this.add(centerXField);

        centerYField = new JTextField(Integer.toString(circle.getCenter().y));
        this.add(centerYField);

        radiusField = new JTextField(Integer.toString(circle.getRadius()));
        this.add(radiusField);

        JButton colorChooserButton = new JButton("Choose color");
        colorChooserButton.addActionListener(e ->
                chosenColor = JColorChooser.showDialog(CircleEditor.this,
                "Choose color", circle.getLineColor()));
        this.add(colorChooserButton);
    }

    @Override
    public void checkEditing() {
        try {
            Integer.parseInt(centerXField.getText());
            Integer.parseInt(centerYField.getText());
            Integer.parseInt(radiusField.getText());

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Values cannot be parsed to integers!");
        }
    }

    @Override
    public void acceptEditing() {
        circle.setCenter(new Point(
                Integer.parseInt(centerXField.getText()),
                Integer.parseInt(centerYField.getText())
        ));

        circle.setRadius(Integer.parseInt(radiusField.getText()));

        circle.setLineColor(chosenColor);
    }
}
