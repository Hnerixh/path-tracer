package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.IntersectionInfo;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Coordinate3;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Normal;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Ray;

/**
 * Everything that can intersect, be assigned a material, and
 * calculate radiance given intersection info should be considered a
 * Shape.
 */
public interface Shape
{
    IntersectionInfo intersection(Ray ray);
    public Ray newRay(Ray oldRay, Normal normal, Coordinate3 coord);
    public void applyToRadiance(Ray nextRay, Ray oldRay, Normal normal, Radiance radiance);
}
