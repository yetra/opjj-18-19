package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * This class represents a constraint in the {@link CalcLayout} layout manager.
 *
 * @author Bruna Dujmović
 *
 */
public class RCPosition {

    /**
     * The row of this position.
     */
    private int row;
    /**
     * The column of this position.
     */
    private int column;

    /**
     * Constructs an {@link RCPosition} of the given row and column.
     *
     * @param row the row of this position
     * @param column the column of this position
     */
    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row of this position.
     *
     * @return the row of this position
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of this position.
     *
     * @return the column of this position
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns an {@link RCPosition} object parsed from a given string.
     *
     * @param positionString the position string to parse
     * @return an {@link RCPosition} object parsed from the given string
     * @throws CalcLayoutException if the given string can't be parsed
     */
    public static RCPosition fromString(String positionString) {
        String[] parts = positionString.split(",");

        try {
            int row = Integer.parseInt(parts[0]);
            int column = Integer.parseInt(parts[1]);
            return new RCPosition(row, column);

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new CalcLayoutException("Invalid position string!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RCPosition that = (RCPosition) o;
        return row == that.row &&
                column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
