package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents a {@link Sphere} graphical object which is defined by its
 * center, radius, and Phong model parameters.
 *
 * @author Bruna Dujmović
 *
 */
public class Sphere extends GraphicalObject {

    /**
     * The center of the sphere.
     */
    private Point3D center;

    /**
     * The radius of the sphere.
     */
    private double radius;

    /**
     * The red diffuse component.
     */
    private double kdr;
    /**
     * The green diffuse component.
     */
    private double kdg;
    /**
     * The blue diffuse component.
     */
    private double kdb;

    /**
     * The red reflective component.
     */
    private double krr;
    /**
     * The green reflective component.
     */
    private double krg;
    /**
     * The blue reflective component.
     */
    private double krb;

    /**
     * The shininess factor.
     */
    private double krn;

    /**
     * Constructs a {@link Sphere} object of a given center and radius, and Phong
     * model parameters.
     *
     * @param center the center of the sphere
     * @param radius the radius of the sphere
     * @param kdr the red diffuse component
     * @param kdg the green diffuse component
     * @param kdb the blue diffuse component
     * @param krr the red reflective component
     * @param krg the green reflective component
     * @param krb the blue reflective component
     * @param krn the shininess factor
     */
    public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb,
                  double krr, double krg, double krb, double krn) {

        this.center = center;
        this.radius = radius;
        this.kdr = kdr;
        this.kdg = kdg;
        this.kdb = kdb;
        this.krr = krr;
        this.krg = krg;
        this.krb = krb;
        this.krn = krn;
    }

    /**
     * Returns a {@link RayIntersection} object representing the closest intersection
     * point of this sphere and a given ray.
     *
     * @param ray the ray to check
     * @return a {@link RayIntersection} object representing the closest intersection
     *         point of this sphere and a given ray
     */
    public RayIntersection findClosestRayIntersection(Ray ray) {
        Point3D P = ray.start;      // origin of the ray
        Point3D U = ray.direction;  // normalized directional vector of the ray

        /*
         * parametric equation of the line on which the ray lies:
         * L(t) = P + tU
         *
         * equation of the sphere:
         * (X - center) * (X - center) = radius^2, where X is a point that lies on the sphere
         *
         * quadratic equation for the intersection(s):
         * U*U * t^2 + 2*U*(P - center) * t + (P - center)*(P - center) - radius^2 = 0
         *   a * t^2 +                b * t +                                    c = 0
         *
         * we are solving it to get the parameter(s) t to determine the intersection(s)
         */
        Point3D Q = P.sub(center);                          // P - center
        double a = U.scalarProduct(U);                      // U*U - should be 1
        double b = Q.scalarProduct(U.scalarMultiply(2));    // 2*U*(P - center)
        double c = Q.scalarProduct(Q) - radius*radius;      // (P - center)*(P - center) - radius^2
        double discriminant = b*b - 4*a*c;

        if (discriminant < 0) { // complex solutions - no intersection
            return null;
        }

        // we only need the closer intersection, so the smaller t parameter
        double t = (-b - Math.sqrt(discriminant)) / (2 * a);
        Point3D intersection = P.add(U.scalarMultiply(t));

        return new SphereRayIntersection(intersection, P.sub(intersection).norm(), true);
    }

    /**
     * A class that represents the intersection of a ray and this sphere.
     */
    private class SphereRayIntersection extends RayIntersection {

        /**
         * Constructs a {@link SphereRayIntersection} object.
         *
         * @param point the point of the intersection
         * @param distance the distance between the start of the ray and the intersection
         * @param outer {@code true} if the intersection is an outer intersection
         */
        SphereRayIntersection(Point3D point, double distance, boolean outer) {
            super(point, distance, outer);
        }

        @Override
        public Point3D getNormal() {
            return getPoint().sub(center).normalize();
        }

        @Override
        public double getKdr() {
            return kdr;
        }

        @Override
        public double getKdg() {
            return kdg;
        }

        @Override
        public double getKdb() {
            return kdb;
        }

        @Override
        public double getKrr() {
            return krr;
        }

        @Override
        public double getKrg() {
            return krg;
        }

        @Override
        public double getKrb() {
            return krb;
        }

        @Override
        public double getKrn() {
            return krn;
        }
    }
}