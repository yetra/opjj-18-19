package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.atomic.AtomicBoolean;

import static hr.fer.zemris.java.raytracer.Util.tracer;

/**
 * This program is a simplification of a ray-tracer for the rendering of 3D scenes.
 */
public class RayCaster {

    /**
     * The main method. Shows the image using {@link RayTracerViewer}.
     *
     * @param args the command-line arguments, not used
     */
    public static void main(String[] args) {
        RayTracerViewer.show(getIRayTracerProducer(),
                new Point3D(10,0,0),
                new Point3D(0,0,0),
                new Point3D(0,0,10),
                20, 20)
        ;
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
            public void produce(Point3D eye, Point3D view, Point3D viewUp,
                                double horizontal, double vertical, int width,
                                int height, long requestNo, IRayTracerResultObserver observer,
                                AtomicBoolean cancel) {

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

                Scene scene = RayTracerViewer.createPredefinedScene();

                short[] rgb = new short[3];
                int offset = 0;
                for(int y = 0; y < height; y++) {
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

                System.out.println("Izračuni gotovi...");
                observer.acceptResult(red, green, blue, requestNo);
                System.out.println("Dojava gotova...");
            }
        };
    }
}
