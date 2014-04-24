package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.color.IntersectionInfo;
import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Everything that can intersect, be assigned a material, and
 * calculate radiance given intersection info should be considered a
 * Shape.
 */
public interface Shape
{
    IntersectionInfo intersection(Ray ray);
    // public void setMaterial(Material material);
    // public Material getMaterial();
    public Ray newRay(Ray oldRay, Normal normal, Coordinate3 coord);
    public void applyToRadiance(Ray nextRay, Ray oldRay, Normal normal, Radiance radiance);
}
