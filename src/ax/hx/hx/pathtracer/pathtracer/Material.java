package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Please observe that getRandomOutgoingRay may give null, for example if the material is a lightsource or a material reflecting nothing.
 */
public interface Material
{
    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin);

    // Current materials may not use every argument,
    // but they are left here for the future,
    // in case someone would like to implement a more complex BRDF.
    public Radiance calculateInfluence(Ray incoming, Ray outgoing, Normal normal, Radiance incomingRadiance);
}
