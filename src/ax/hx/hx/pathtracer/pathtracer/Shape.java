package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.color.IntersectionInfo;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Everything that can intersect, be assigned a material, and
 * calculate radiance given intersection info should be considered a
 * Shape.
 */
public interface Shape
{
    IntersectionInfo intersection(Ray ray);
    Radiance traceLastHit(int depth, Scene scene, IntersectionInfo info);
    public void setMaterial(Material material);
}
