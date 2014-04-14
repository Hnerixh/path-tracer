package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Everything that can trace a ray to a depth is a scene. Simple as that.
 */
public interface Scene
{
    // ANTIWARNING RR = Russian Roulette
    public Radiance pathtrace(Ray ray, int depth, double RRratio, Radiance radiance);
}
