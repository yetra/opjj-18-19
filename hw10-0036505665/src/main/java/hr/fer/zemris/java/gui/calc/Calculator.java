package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

/**
 * This program is a calculator that supports basic binary (sin, cos, tan, ctg, log, ln,
 * exponentiation, etc.) and unary (/, *, -, =) mathematical operations. Stack operations
 * are also provided for storing/removing values from an internal stack.
 *
 * Clicking on the 'Inv' checkbox switches the currently displayed binary operations to
 * their inverse counterparts.
 *
 * @author Bruna Dujmović
 */
public class Calculator extends JFrame {

    /**
     * The model used for this calculator.
     */
    private CalcModel model = new CalcModelImpl();

    /**
     * The stack for supporting the 'push' and 'pop' operations.
     */
    private Stack<Double> valueStack = new Stack<>();

    /**
     * A list of all buttons that represent operations which have an inverse operation.
     */
    private List<InvOperationButton> invOperationButtons = new ArrayList<>(7);

    /**
     * Constructs a {@link Calculator} object and initializes the GUI.
     */
    public Calculator() {
        setLocation(20, 50);
        setSize(300, 200);
        setTitle("Java Calculator v1.0");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initGUI();
    }

    /**
     * Initializes all GUI components of this calculator.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout());

        JLabel display = new JLabel("0");
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setOpaque(true);
        display.setBackground(Color.YELLOW);
        model.addCalcValueListener(e -> display.setText(model.toString()));
        cp.add(display, "1,1");

        cp.add(new OperationButton("0", e -> insertDigit(0)), "5,3");
        cp.add(new OperationButton("1", e -> insertDigit(1)), "4,3");
        cp.add(new OperationButton("2", e -> insertDigit(2)), "4,4");
        cp.add(new OperationButton("3", e -> insertDigit(3)), "4,5");
        cp.add(new OperationButton("4", e -> insertDigit(4)), "3,3");
        cp.add(new OperationButton("5", e -> insertDigit(5)), "3,4");
        cp.add(new OperationButton("6", e -> insertDigit(6)), "3,5");
        cp.add(new OperationButton("7", e -> insertDigit(7)), "2,3");
        cp.add(new OperationButton("8", e -> insertDigit(8)), "2,4");
        cp.add(new OperationButton("9", e -> insertDigit(9)), "2,5");

        cp.add(new OperationButton("clr", e -> model.clear()), "1,7");
        cp.add(new OperationButton("res", e -> model.clearAll()), "2,7");
        cp.add(new OperationButton("push", e -> valueStack.push(model.getValue())), "3,7");
        cp.add(new OperationButton("pop", e -> popStack()), "4,7");

        cp.add(new OperationButton("+/-", e -> swapSign()), "5,4");
        cp.add(new OperationButton(".", e -> insertDecimalPoint()), "5,5");

        cp.add(new OperationButton(
                "1/x", e -> model.setValue(1.0 / model.getValue())), "2,1"
        );

        InvOperationButton log = new InvOperationButton(
                "log", e -> model.setValue(Math.log10(model.getValue())),
                "10^x", e -> model.setValue(Math.pow(10, model.getValue()))
        );
        cp.add(log, "3,1");
        invOperationButtons.add(log);

        InvOperationButton ln = new InvOperationButton(
                "ln", e -> model.setValue(Math.log(model.getValue())),
                "e^x", e -> model.setValue(Math.exp(model.getValue()))
        );
        cp.add(ln, "4,1");
        invOperationButtons.add(ln);

        InvOperationButton exp = new InvOperationButton(
                "x^n", e -> setBinaryOperation(Math::pow),
                "x^(1/n)", e -> setBinaryOperation((first, second) -> Math.pow(first, 1.0 / second))
        );
        cp.add(exp, "5,1");
        invOperationButtons.add(exp);

        InvOperationButton sin = new InvOperationButton(
                "sin", e -> model.setValue(Math.sin(model.getValue())),
                "arcsin", e -> model.setValue(Math.asin(model.getValue()))
        );
        cp.add(sin, "2,2");
        invOperationButtons.add(sin);

        InvOperationButton cos = new InvOperationButton(
                "cos", e -> model.setValue(Math.cos(model.getValue())),
                "arccos", e -> model.setValue(Math.acos(model.getValue()))
        );
        cp.add(cos, "3,2");
        invOperationButtons.add(cos);

        InvOperationButton tan = new InvOperationButton(
                "tan", e -> model.setValue(Math.tan(model.getValue())),
                "arctan", e -> model.setValue(Math.atan(model.getValue()))
        );
        cp.add(tan, "4,2");
        invOperationButtons.add(tan);

        InvOperationButton ctg = new InvOperationButton(
                "ctg", e -> model.setValue(1.0 / Math.tan(model.getValue())),
                "arctg", e -> model.setValue(Math.PI / 2 - Math.atan(model.getValue()))
        );
        cp.add(ctg, "5,2");
        invOperationButtons.add(ctg);

        cp.add(new OperationButton(
                "=", e -> {
                    if (model.isActiveOperandSet() && model.getPendingBinaryOperation() != null) {
                        model.setValue(
                                model.getPendingBinaryOperation().applyAsDouble(
                                        model.getActiveOperand(), model.getValue()
                                )
                        );
                        model.setPendingBinaryOperation(null);
                    }
                }), "1,6"
        );

        cp.add(new OperationButton(
                "/", e -> setBinaryOperation((first, second) -> first / second)), "2,6"
        );

        cp.add(new OperationButton(
                "*", e -> setBinaryOperation((first, second) -> first * second)), "3,6"
        );

        cp.add(new OperationButton(
                "-", e -> setBinaryOperation((first, second) -> first - second)), "4,6"
        );

        cp.add(new OperationButton(
                "+", e -> setBinaryOperation((first, second) -> first + second)), "5,6"
        );

        JCheckBox invCheckBox = new JCheckBox("Inv");
        invCheckBox.addActionListener(
                e -> invOperationButtons.forEach(InvOperationButton::inverse)
        );
        cp.add(invCheckBox, "5,7");
    }

    /**
     * A helper method that executes the given binary operation.
     *
     * @param op the binary operation to execute
     */
    private void setBinaryOperation(DoubleBinaryOperator op) {
        if (model.getPendingBinaryOperation() == null) {
            model.setActiveOperand(model.getValue());

        } else if (model.isActiveOperandSet()) {
            model.setValue(
                    model.getPendingBinaryOperation().applyAsDouble(
                            model.getActiveOperand(), model.getValue()
                    )
            );
            model.setActiveOperand(model.getValue());
        }

        model.setPendingBinaryOperation(op);
        model.clear();
    }

    /**
     * A helper method to be called when inserting a decimal point on button click. If
     * the insertion causes an exception, an appropriate error message dialog will be
     * shown.
     */
    private void insertDecimalPoint() {
        try {
            model.insertDecimalPoint();
        } catch (CalculatorInputException e) {
            JOptionPane.showMessageDialog(
                    this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * A helper method to be called to swap the value's sign on button click. If the
     * swapping causes an exception, an appropriate error message dialog will be shown.
     */
    private void swapSign() {
        try {
            model.swapSign();
        } catch (CalculatorInputException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * A helper method to be called when popping from the stack on button click. The
     * popped value is shown on the display. If the stack is empty, an appropriate
     * error message dialog will be shown.
     */
    private void popStack() {
        try {
            model.setValue(valueStack.pop());
        } catch (EmptyStackException ex) {
            JOptionPane.showMessageDialog(
                    this, "Empty stack!", "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * A helper method to be called when inserting a digit on button click. If the
     * insertion causes an exception, an appropriate error message dialog will be shown.
     *
     * @param digit the digit to insert
     */
    private void insertDigit(int digit) {
        try {
            model.insertDigit(digit);
        } catch (CalculatorInputException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * The main method. Creates and displays the calculator frame.
     *
     * @param args the command-line arguments, not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new Calculator();
            frame.pack();
            frame.setVisible(true);
        });
    }
}
