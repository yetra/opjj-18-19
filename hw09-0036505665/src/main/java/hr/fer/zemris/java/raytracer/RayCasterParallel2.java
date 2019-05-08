package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This program is a simplification of a ray-tracer for the rendering of 3D scenes.
 * The implementation here parallelizes the calculation using the Fork-Join framework
 * and RecursiveAction.
 *
 * An animation is shown of the user rotating around the scene.
 */
public class RayCasterParallel2 {

    /**
     * The main method. Shows the image using {@link RayTracerViewer}.
     *
     * @param args the command-line arguments, not used
     */
    public static void main(String[] args) {
        RayTracerViewer.show(
                getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30
        );
    }

    /**
     * Returns an {@link IRayTracerAnimator} object used for providing temporal
     * information to the GUI showing the scene.
     *
     * @return an {@link IRayTracerAnimator} object
     */
    private static IRayTracerAnimator getIRayTracerAnimator() {

        return new IRayTracerAnimator() {
            long time;

            @Override
            public void update(long deltaTime) {
                time += deltaTime;
            }

            @Override
            public Point3D getViewUp() { // fixed in time
                return new Point3D(0,0,10);
            }

            @Override
            public Point3D getView() { // fixed in time
                return new Point3D(-2,0,-0.5);
            }

            @Override
            public long getTargetTimeFrameDuration() {
                return 150; // redraw scene each 150 milliseconds
            }

            @Override
            public Point3D getEye() { // changes in time
                double t = (double)time / 10000 * 2 * Math.PI;
                double t2 = (double)time / 5000 * 2 * Math.PI;
                double x = 50*Math.cos(t);
                double y = 50*Math.sin(t);
                double z = 30*Math.sin(t2);
                return new Point3D(x,y,z);
            }
        };
    }

    /**
     * Returns an {@link IRayTracerProducer} object that is capable of creating scene
     * snapshots using the ray-tracing technique.
     *
     * @return an {@link IRayTracerProducer} object
     */
    private static IRayTracerProducer getIRayTracerProducer() {
        return new IRayTracerProducer() {

            @Override
            public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal,
                                double vertical, int width, int height, long requestNo,
                                IRayTracerResultObserver observer, AtomicBoolean cancel) {

                System.out.println("Započinjem izračune...");
                short[] red = new short[width*height];
                short[] green = new short[width*height];
                short[] blue = new short[width*height];

                Point3D OG = view.sub(eye).normalize();
                Point3D VUV = viewUp.normalize();

                Point3D zAxis = OG;
                Point3D yAxis = (VUV.sub(OG.scalarMultiply(OG.scalarProduct(VUV)))).normalize();
                Point3D xAxis = OG.vectorProduct(yAxis).normalize();

                Point3D screenCorner = view
                        .sub(xAxis.scalarMultiply(horizontal / 2))
                        .add(yAxis.scalarMultiply(vertical / 2));

                Scene scene = RayTracerViewer.createPredefinedScene2();

                ForkJoinPool pool = new ForkJoinPool();
                pool.invoke(new RecursiveTracerAction(
                        width, height, horizontal, vertical, 0, height - 1, xAxis,
                        yAxis, eye, screenCorner, scene, red, green, blue, cancel
                ));
                pool.shutdown();

                System.out.println("Izračuni gotovi...");
                observer.acceptResult(red, green, blue, requestNo);
                System.out.println("Dojava gotova...");
            }
        };
    }
}
