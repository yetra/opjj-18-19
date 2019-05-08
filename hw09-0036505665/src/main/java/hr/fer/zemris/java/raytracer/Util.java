package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;

import java.util.List;

/**
 * An utility class of static methods for the various ray-caster implementations.
 *
 * @author Bruna Dujmović
 *
 */
public class Util {

    /**
     * Tolerance to take into account when comparing distances of type double.
     */
    private static final double TOLERANCE = 0.0001;

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

            if (current != null && (closest == null ||
                    current.getDistance() + TOLERANCE < closest.getDistance())) {
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
