package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This program is a simplification of a ray-tracer for the rendering of 3D scenes.
 *
 * @author Bruna Dujmović
 *
 */
public class RayCaster {

    /**
     * Tolerance to take into account when comparing distances of type double.
     */
    private static final double TOLERANCE = 0.0001;

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

    /**
     * Determines if the given ray intersects an object of the specified scene. If yes,
     * the pixel will be colored as determined by the {@link #determineColorFor} method.
     * Otherwise, it will by colored with rgb(0, 0, 0).
     *
     * @param scene the scene that contains the objects
     * @param ray the ray that intersects the objects
     * @param rgb the array that stores the rgb color values
     */
    protected static void tracer(Scene scene, Ray ray, short[] rgb) {
        rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;

        RayIntersection closest = findClosestIntersection(scene, ray);

        if (closest == null) {
            return;
        }

        determineColorFor(closest, scene, ray, rgb);
    }

    /**
     * Finds the closes intersection of a given ray and the objects of a specified
     * scene.
     *
     * @param scene the scene that contains the objects
     * @param ray the ray that intersects the objects
     * @return a {@link RayIntersection} object representing the closest intersection
     */
    private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
        List<GraphicalObject> objects = scene.getObjects();
        RayIntersection closest = null;

        for (GraphicalObject object : objects) {
            RayIntersection current = object.findClosestRayIntersection(ray);

            if (current != null && (closest == null || current.getDistance() < closest.getDistance())) {
                closest = current;
            }
        }

        return closest;
    }

    /**
     * Determines the color of the pixel using the Phong model.
     *
     * @param closest the closest intersection determined in {@link #findClosestIntersection}
     * @param scene the scene that contains the objects
     * @param origin the ray that intersects the objects
     * @param rgb the array that stores the rgb color values
     */
    private static void determineColorFor(RayIntersection closest, Scene scene,
                                          Ray origin, short[] rgb) {
        // ambient component
        rgb[0] = 15;
        rgb[1] = 15;
        rgb[2] = 15;

        List<LightSource> lights = scene.getLights();
        for (LightSource ls : lights) {
            Ray ray = Ray.fromPoints(ls.getPoint(), closest.getPoint());

            RayIntersection other = findClosestIntersection(scene, ray);
            if (other == null) {
                continue;
            }

            double lsOtherDistance = ls.getPoint().sub(other.getPoint()).norm();
            double lsClosestDistance = ls.getPoint().sub(closest.getPoint()).norm();
            if (lsOtherDistance + TOLERANCE < lsClosestDistance) {
                continue; // skip this light source (it is obscured by that object!)
            }

            // unit vector from the intersection in the direction of the source
            Point3D l = ls.getPoint().sub(closest.getPoint()).normalize();
            // unit normal vector of the intersection
            Point3D n = closest.getNormal();
            double lDotN = Math.max(l.scalarProduct(n), 0);

            // diffuse component
            rgb[0] += ls.getR() * closest.getKdr() * lDotN;
            rgb[1] += ls.getG() * closest.getKdg() * lDotN;
            rgb[2] += ls.getB() * closest.getKdb() * lDotN;

            // projection of the l vector onto the n vector
            Point3D lProjection = n.scalarMultiply(l.scalarProduct(n));
            // unit vector of the reflected ray
            Point3D r = lProjection.add(l.sub(lProjection).negate()).normalize();
            // unit vector from the intersection in the direction of the eye
            Point3D v = origin.start.sub(closest.getPoint()).normalize();
            double rDotVPowN = Math.pow(Math.max(r.scalarProduct(v), 0), closest.getKrn());

            // reflective component
            rgb[0] += ls.getR() * closest.getKrr() * rDotVPowN;
            rgb[1] += ls.getG() * closest.getKrg() * rDotVPowN;
            rgb[2] += ls.getB() * closest.getKrb() * rDotVPowN;
        }
    }
}
