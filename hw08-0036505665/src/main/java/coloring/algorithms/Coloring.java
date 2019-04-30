package coloring.algorithms;

import marcupic.opjj.statespace.coloring.Picture;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class implements the functionality for filling a {@link Picture} element with
 * the desired color.
 *
 * @author Bruna Dujmović
 *
 */
public class Coloring {

    /**
     * The pixel that was clicked on.
     */
    private Pixel reference;

    /**
     * The picture to color.
     */
    private Picture picture;

    /**
     * The desired color of the clicked picture element.
     */
    private int fillColor;

    /**
     * The color of the {@link #reference} pixel.
     */
    private int refColor;

    /**
     * A {@link Consumer} that sets a given pixel's color to {@link #fillColor}.
     */
    public final Consumer<Pixel> process =
            (pixel) -> picture.setPixelColor(pixel.x, pixel.y, fillColor);

    /**
     * A {@link Function} that returns a list of neighboring pixels of a given pixel.
     */
    public final Function<Pixel, List<Pixel>> succ = (pixel) -> {
        List<Pixel> neighbors = new LinkedList<>();

        if (pixel.x - 1 >= 0) {
            neighbors.add(new Pixel(pixel.x - 1, pixel.y));
        }
        if (pixel.x + 1 < picture.getWidth()) {
            neighbors.add(new Pixel(pixel.x + 1, pixel.y));
        }
        if (pixel.y - 1 >= 0) {
            neighbors.add(new Pixel(pixel.x, pixel.y - 1));
        }
        if (pixel.y + 1 < picture.getHeight()) {
            neighbors.add(new Pixel(pixel.x, pixel.y + 1));
        }

        return neighbors;
    };

    /**
     * A {@link Predicate} that returns {@code true} if the given pixel's color is
     * equal to {@link #refColor}.
     */
    public final Predicate<Pixel> acceptable =
            (pixel) -> picture.getPixelColor(pixel.x, pixel.y) == refColor;

    /**
     * A {@link Supplier} that returns the {@link #reference} pixel.
     */
    public final Supplier<Pixel> s0 = () -> reference;

    /**
     * Constructs a {@link Coloring} object given a reference pixel, picture, and fill
     * color.
     *
     * @param reference the pixel that was clicked on
     * @param picture the picture to color
     * @param fillColor the desired color of the clicked picture element
     */
    public Coloring(Pixel reference, Picture picture, int fillColor) {
        this.reference = Objects.requireNonNull(reference);
        this.picture = Objects.requireNonNull(picture);
        this.fillColor = fillColor;
        this.refColor = picture.getPixelColor(reference.x, reference.y);
    }
}
