package hr.fer.zemris.java.hw17.jvdraw.editors;

import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;

import javax.swing.*;
import java.awt.*;

/**
 * A class for supporting the editing of {@link FilledCircle} object properties.
 *
 * @author Bruna Dujmović
 *
 */
public class FilledCircleEditor extends CircleEditor {

    /**
     * The color chosen on the {@link JColorChooser}.
     */
    private Color chosenFillColor;

    /**
     * The filled circle to edit.
     */
    private FilledCircle filledCircle;

    /**
     * Constructs a {@link FilledCircleEditor} for the given filled circle.
     *
     * @param filledCircle the filled circle to edit
     */
    public FilledCircleEditor(FilledCircle filledCircle) {
        super(filledCircle);
        this.filledCircle = filledCircle;

        JButton fillColorChooserButton = new JButton("Choose fill color");
        fillColorChooserButton.addActionListener(e ->
                chosenFillColor = JColorChooser.showDialog(
                        FilledCircleEditor.this, "Choose fill color",
                        filledCircle.getLineColor()));
        this.add(fillColorChooserButton);
    }

    @Override
    public void acceptEditing() {
        super.acceptEditing();

        filledCircle.setFillColor(chosenFillColor);
    }
}
