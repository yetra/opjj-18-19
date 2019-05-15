package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

import java.util.HashSet;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;

/**
 * This class is an implementation of the {@link CalcModel} interface.
 *
 * @author Bruna Dujmović
 *
 */
public class CalcModelImpl implements CalcModel {

    /**
     * A flag for checking if this model is editable.
     */
    private boolean isEditable = true;
    /**
     * A flag for checking the sign of the current value.
     */
    private boolean isPositive = true;
    /**
     * A flag for checking if the active operand is set.
     */
    private boolean isActiveOperandSet = false;

    /**
     * A string for storing inserted digits.
     */
    private String valueString = "";
    /**
     * The corresponding double value of the stored string.
     */
    private double value = 0.0;

    /**
     * The value of the active operand.
     */
    private double activeOperand;
    /**
     * The operation to be executed.
     */
    private DoubleBinaryOperator pendingOperation;

    /**
     * The collection containing all registered listeners of this model.
     */
    private Set<CalcValueListener> listeners = new HashSet<>();

    @Override
    public void addCalcValueListener(CalcValueListener l) {
        listeners.add(l);
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    @Override
    public String toString() {
        if (valueString.isEmpty() || valueString.equals("-")) {
            return valueString + "0";
        }

        stripLeadingZeroes();
        return valueString;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
        this.valueString = Double.toString(value);
        isEditable = false;

        notifyListeners();
    }

    @Override
    public boolean isEditable() {
        return isEditable;
    }

    @Override
    public void clear() {
        valueString = "";
        value = 0.0;
        isEditable = true;

        notifyListeners();
    }

    @Override
    public void clearAll() {
        valueString = "";
        value = 0.0;
        isActiveOperandSet = false;
        pendingOperation = null;
        isEditable = true;

        notifyListeners();
    }

    @Override
    public void swapSign() throws CalculatorInputException {
        if (!isEditable) {
            throw new CalculatorInputException("This model is not editable!");
        }

        isPositive = !isPositive;
        if (valueString.startsWith("-")) {
            valueString = valueString.substring(1);
        } else {
            valueString = "-".concat(valueString);
        }
        value = -value;

        notifyListeners();
    }

    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if (!isEditable) {
            throw new CalculatorInputException("This model is not editable!");
        }
        if (valueString.isEmpty() || valueString.equals("-") || valueString.contains(".")) {
            throw new CalculatorInputException("Cannot add a decimal point!");
        }

        valueString = valueString.concat(".");
        notifyListeners();
    }

    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (!isEditable) {
            throw new CalculatorInputException("This model is not editable!");
        }
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Digit must be in range [0, 9]!");
        }

        String newValueString = valueString.concat(Integer.toString(digit));
        double newValue = Double.parseDouble(newValueString);

        if (newValue > Double.MAX_VALUE) {
            throw new CalculatorInputException("Number would be too large!");
        }
        valueString = newValueString;
        value = newValue;

        notifyListeners();
    }

    @Override
    public boolean isActiveOperandSet() {
        return isActiveOperandSet;
    }

    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (!isActiveOperandSet) {
            throw new IllegalStateException("Active operand is not set!");
        }

        return activeOperand;
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
        isActiveOperandSet = true;
    }

    @Override
    public void clearActiveOperand() {
        isActiveOperandSet = false;
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;
    }

    /**
     * Notifies all listeners stored in the {@link #listeners} collection.
     */
    private void notifyListeners() {
        listeners.forEach(l -> l.valueChanged(this));
    }

    /**
     * Removes the leading zeroes from {@link #valueString}.
     */
    private void stripLeadingZeroes() {
        int length = valueString.length();

        if (length > 1) {
            int index = 0;
            while (index + 1 < length && valueString.charAt(index) == '0'
                    && valueString.charAt(index + 1) != '.') {
                index++;
            }

            valueString = valueString.substring(index);
        }
    }
}
