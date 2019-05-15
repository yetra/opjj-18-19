package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class is a custom layout manager that arranges components in a fixed 5x7 grid.
 * All rows are of uniform height and all columns are of uniform width. Spacing between
 * components can be set through the constructor and is equal for both rows and columns.
 *
 * Components are uniformly distributed in the grid. The component on position (1,1)
 * spans 5 columns. Each component's constraint is specified using an {@link RCPosition}
 * object.
 *
 * @author Bruna Dujmović
 *
 */
public class CalcLayout implements LayoutManager2 {

    /**
     * An {@link RCPosition} object representing the first position of the layout.
     * It always occupies positions (1,1) to (1,5) inclusive.
     */
    private static final RCPosition FIRST_POSITION = new RCPosition(1, 1);

    /**
     * The number of rows of this layout.
     */
    private static final int ROWS = 5;
    /**
     * The numbe of columns of this layout.
     */
    private static final int COLUMNS = 7;

    /**
     * A map for storing all layout components and their positions.
     */
    private Map<RCPosition, Component> components = new HashMap<>();

    /**
     * The spacing in pixels between each component (for both columns and rows).
     */
    private int spacing;

    /**
     * Constructs a {@link CalcLayout}. Component spacing is set to 0 by default.
     */
    public CalcLayout() {
    }

    /**
     * Constructs a {@link CalcLayout} of the given spacing.
     *
     * @param spacing the spacing in pixels between each component
     */
    public CalcLayout(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        RCPosition position;

        if (constraints instanceof RCPosition) {
            position = (RCPosition) constraints;
        } else if (constraints instanceof String) {
            position = RCPosition.fromString((String) constraints);
        } else {
            throw new CalcLayoutException("Invalid constraint type!");
        }

        validate(position);

        if (components.containsKey(position)) {
            throw new CalcLayoutException("A component already exists on the given position.");
        }
        components.put(position, comp);
    }

    /**
     * Validates the given {@link RCPosition} object.
     *
     * @param position the {@link RCPosition} object to validate
     * @throws CalcLayoutException if the given position is invalid
     */
    private void validate(RCPosition position) {
        int row = position.getRow();
        int column = position.getColumn();

        if (row < 1 || row > 5 || column < 1 || column > 7) {
            throw new CalcLayoutException("Invalid position!");
        }

        if (row == 1 && (column >= 2 && column <= 5)) {
            throw new CalcLayoutException("Invalid position!");
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return getLayoutSize(target, Component::getMaximumSize);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        components.values().removeIf(value -> value.equals(comp));
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return getLayoutSize(parent, Component::getPreferredSize);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return getLayoutSize(parent, Component::getMinimumSize);
    }

    @Override
    public void layoutContainer(Container parent) {
        if (components.size() == 0) {
            return;
        }

        Insets insets = parent.getInsets();

        int totalSpacingWidth = (COLUMNS - 1) * spacing;
        int totalSpacingHeight = (ROWS - 1) * spacing;

        int widthNoInsets = parent.getWidth() - (insets.left + insets.right);
        int heightNoInsets = parent.getHeight() - (insets.top + insets.bottom);

        int componentWidth = (widthNoInsets - totalSpacingWidth) / COLUMNS;
        int componentHeight = (heightNoInsets - totalSpacingHeight) / ROWS;

        int extraWidth = (widthNoInsets - (componentWidth * COLUMNS + totalSpacingWidth)) / 2;
        int extraHeight = (heightNoInsets - (componentHeight * ROWS + totalSpacingHeight)) / 2;

        for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
            RCPosition position = entry.getKey();
            Component component = entry.getValue();

            if (position.equals(FIRST_POSITION)) {
                component.setBounds(
                        insets.left + extraWidth,
                        insets.top + extraHeight,
                        componentWidth * 5 + spacing * 4,
                        componentHeight
                );
                continue;
            }

            component.setBounds(
                    insets.left + extraWidth + (componentWidth + spacing) * (position.getColumn() - 1),
                    insets.top + extraHeight + (componentHeight + spacing) * (position.getRow() - 1),
                    componentWidth,
                    componentHeight
            );
        }
    }

    /**
     * Returns the minimum/maximum/preferred {@link Dimension} of the given parent
     * container based on the added components and constraints of this layout.
     *
     * @param parent the parent container of this layout
     * @param sizeGetter the {@link Function} that specifies which dimension should
     *                   be returned
     * @return the minimum/maximum/preferred {@link Dimension} of the given container
     */
    private Dimension getLayoutSize(Container parent, Function<Component, Dimension> sizeGetter) {
        Insets insets = parent.getInsets();
        int width = 0;
        int height = 0;

        for (Map.Entry<RCPosition, Component> entry : components.entrySet()) {
            Component component = entry.getValue();
            Dimension d = sizeGetter.apply(component);
            if (d == null) {
                continue;
            }

            if (entry.getKey().equals(FIRST_POSITION)) {
                d.width = (d.width - 4 * spacing) / 5;
            }

            if (width < d.width) {
                width = d.width;
            }
            if (height < d.height) {
                height = d.height;
            }
        }

        return new Dimension(
                insets.left + insets.right + COLUMNS * width + (COLUMNS-1) * spacing,
                insets.top + insets.bottom + ROWS * height + (ROWS-1) * spacing
        );
    }
}
