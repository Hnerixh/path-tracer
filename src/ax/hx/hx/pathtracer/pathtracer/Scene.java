package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Everything that can trace a ray to a depth is a scene. Simple as that.
 */
public interface Scene
{
    public Radiance pathtrace(Ray ray, int depth);
}
