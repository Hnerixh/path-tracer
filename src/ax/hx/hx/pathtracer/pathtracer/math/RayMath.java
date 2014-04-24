package ax.hx.hx.pathtracer.pathtracer.math;

/**
 * This is a utility class containing various functions for getting
 * randomized rays.
 */
public final class RayMath
{
    private RayMath() {}

    public static Ray newCosineImportanceWeightedRay(Normal normal, Coordinate3 origin, Ray ray){
        // There is a need for outgoing rays to be distributed
        // randomly by Lambert's cosine Law.
        //
        // http://en.wikipedia.org/wiki/Lambert%27s_cosine_law
        //
        // This is done with the following steps:
        // 1. Randomize a point in a cube.
        // 2. If that point is inside a sphere in the cube, proceed, else, goto 1.
        // 3. Create a normalized Vector vr from these coordinates.
        // 4. Add the normal to vr
        // 5. Normalize vr again
        // 6. Return a ray from vr and origin
        //
        //
        // 1/2. Randomize a point in a cube until it's inside a unit
        // sphere. This could go on for ever, get some
        // coffee. (Average should be like 1.3 times)

        double rx, ry, rz;
           do {
	    // IDEA ANTIWARNING
	    // This is okay, we want a number in [-1.0,1.0].
            rx = RandomGen.rand() * 2.0 - 1.0;
            ry = RandomGen.rand() * 2.0 - 1.0;
            rz = RandomGen.rand() * 2.0 - 1.0;
          } while((! insideUnitSphere(rx, ry, rz)) || isZeroVector(rx, ry, rz));
        // We need to remove the zero vector to avoid division by zero
        // in the normalization step.

        // 3. Create normalized vector.
        Vector3 vr = new Vector3(rx, ry, rz);
        vr.normalize();

        // 4. Add the normal
        vr.add(normal);

        // 5. Normalize again
        vr.normalize();

        // 6. Return the randomized ray
        return new Ray(origin, vr, ray.isInsideSomething());

    }

    public static double reflectance(Normal normal, Ray ray, double indexOfRefraction){
        // How much of the light should be reflected, and how much
        // should be transmitted?

        // Just to be a little cheap, Schlicks approximation is used
        // instead of the real fresnell equations.
        double cosTheta = - normal.dotProduct(ray.getVector());

        double n1 = 1;
        double n2 = indexOfRefraction;
        if (ray.isInsideSomething()){
            n2 = 1; n1 = indexOfRefraction;
            double n = n1/n2; // IDEA ANTIWARNING n = index of refraction

            // If so, use the angle from the normal to the other ray.
            double sin2Theta = n*n*(1 - cosTheta*cosTheta);
            cosTheta = Math.sqrt(1 - sin2Theta);
        }
        double r0 = (n1-n2) / (n1+n2);
	r0 *= r0; // IDEA ANTIWARNING The variable is squared, not assigned to itself.
        double x = 1 - cosTheta;
        return r0 + (1-r0) * x * x * x * x * x;
    }

    public static Ray reflectedRay(Ray ray, Normal normal, Coordinate3 origin){
        // This is not really a random ray, this is a mirror...
        //
        Vector3 vec = new Vector3(ray.getVector());
        vec.normalize();

        Vector3 norm = new Vector3(normal);

        norm.scale(2*(norm.dotProduct(vec)));

        norm.subtract(vec);
        norm.negate();
        norm.normalize();
        return new Ray (origin, norm, ray.isInsideSomething());
    }

    private static boolean insideUnitSphere(double x, double y, double z){
        return (x*x + y*y + z*z <= 1);
    }

    private static boolean isZeroVector(double x, double y, double z){
        return (x == 0.0 && y == 0.0 && z == 0.0);
    }
}
