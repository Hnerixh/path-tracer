package ax.hx.hx.pathtracer.pathtracer;

/**
 * This is a utility class containing various functions for getting
 * randomized rays.
 */
public class RayFactory
{
    public static Ray newCosineImportanceWeightedRay(Normal normal, Coordinate3 origin){
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
            rx = Math.random() * 2.0 - 1.0;
            ry = Math.random() * 2.0 - 1.0;
            rz = Math.random() * 2.0 - 1.0;
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
        return new Ray(origin, vr);

    }


    private static boolean insideUnitSphere(double x, double y, double z){
        return (x*x + y*y + z*z <= 1);
    }

    private static boolean isZeroVector(double x, double y, double z){
        return (x == 0.0 && y == 0.0 && z == 0.0);
    }
}
