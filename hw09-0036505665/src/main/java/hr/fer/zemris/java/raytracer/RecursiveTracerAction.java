package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import static hr.fer.zemris.java.raytracer.Util.tracer;

/**
 * This class represents the recursive tracing action used for ray-caster parallelization.
 *
 * @author Bruna Dujmović
 *
 */
class RecursiveTracerAction extends RecursiveAction {

    /**
     * The threshold for the recursive decomposition (the value after which the
     * computation will be performed directly).
     */
    private static final int THRESHOLD = 16 * 16 * 16;

    /**
     * The number of pixels per screen row.
     */
    private int width;
    /**
     * The number of pixels per screen column.
     */
    private int height;

    /**
     * The horizontal width of the observed space.
     */
    private double horizontal;
    /**
     * The vertical height of the observed space.
     */
    private double vertical;

    /**
     * The initial y-coordinate for the currently observed section.
     */
    private int yMin;
    /**
     * The final y-coordinate for the currently observed section.
     */
    private int yMax;

    /**
     * The x-axis of the observed space.
     */
    private Point3D xAxis;
    /**
     * The y-axis of the observed space.
     */
    private Point3D yAxis;

    /**
     * The position of the observer (eye position).
     */
    private Point3D eye;
    /**
     * The upper left corner of the screen.
     */
    private Point3D screenCorner;
    /**
     * The scene that contains the objects.
     */
    private Scene scene;

    /**
     * An array storing the red components of the rgb color.
     */
    private short[] red;
    /**
     * An array storing the green components of the rgb color.
     */
    private short[] green;
    /**
     * An array storing the blue components of the rgb color.
     */
    private short[] blue;

    /**
     * The object used for cancelling the rendering of the image if it is set to
     * {@code true}.
     */
    private AtomicBoolean cancel;

    /**
     * Constructs a {@link RecursiveTracerAction} action.
     *
     * @param width the number of pixels per screen row
     * @param height the number of pixels per screen column
     * @param horizontal the horizontal width of the observed space
     * @param vertical the vertical height of the observed space
     * @param yMin the initial y-coordinate for the currently observed section
     * @param yMax the final y-coordinate for the currently observed section
     * @param xAxis the x-axis of the observed space
     * @param yAxis the y-axis of the observed space
     * @param eye the position of the observer (eye position)
     * @param screenCorner the upper left corner of the screen
     * @param scene the scene that contains the objects
     * @param red an array storing the red components of the rgb color
     * @param green an array storing the green components of the rgb color
     * @param blue an array storing the blue components of the rgb color.
     * @param cancel the object used for cancelling the rendering of the image
     */
    public RecursiveTracerAction(int width, int height, double horizontal, double vertical,
                                 int yMin, int yMax, Point3D xAxis, Point3D yAxis,
                                 Point3D eye, Point3D screenCorner, Scene scene,
                                 short[] red, short[] green, short[] blue, AtomicBoolean cancel) {

        this.width = width;
        this.height = height;
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.yMin = yMin;
        this.yMax = yMax;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.eye = eye;
        this.screenCorner = screenCorner;
        this.scene = scene;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.cancel = cancel;
    }

    /**
     * Performs the tracer computation directly.
     */
    private void computeDirect() {
        short[] rgb = new short[3];
        int offset = 0;
        for(int y = yMin; y <= yMax && !cancel.get(); y++) {
            for(int x = 0; x < width; x++) {
                Point3D screenPoint = screenCorner
                        .add(xAxis.scalarMultiply(x * horizontal / (width - 1)))
                        .sub(yAxis.scalarMultiply(y * vertical / (height - 1)));
                Ray ray = Ray.fromPoints(eye, screenPoint);

                tracer(scene, ray, rgb);

                red[offset] = rgb[0] > 255 ? 255 : rgb[0];
                green[offset] = rgb[1] > 255 ? 255 : rgb[1];
                blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

                offset++;
            }
        }
    }

    @Override
    protected void compute() {
        if(yMax - yMin + 1 <= THRESHOLD) {
            computeDirect();
            return;
        }

        invokeAll(
                new RecursiveTracerAction(
                        width, height, horizontal, vertical, yMin, yMin+(yMax-yMin)/2,
                        xAxis, yAxis, eye, screenCorner, scene, red, green, blue, cancel
                ),
                new RecursiveTracerAction(
                        width, height, horizontal, vertical, yMin+(yMax-yMin)/2+1, yMax,
                        xAxis, yAxis, eye, screenCorner, scene, red, green, blue, cancel
                )
        );
    }
}
