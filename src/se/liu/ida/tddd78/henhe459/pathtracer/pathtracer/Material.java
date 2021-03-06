package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Coordinate3;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Normal;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Ray;

/**
 * Materials give objects in the scene their 'look'.
 * All materials should be able to do two things:
 *
 * 1. Give a new random ray, within a distribution which takes both
 * the cosine term of the rendering equation as well as other
 * characteristics of the material.
 *
 * 2. Given incoming and outgoing rays, a normal and incoming
 * radiance, the material should give it's radiance. This means that
 * texturing isn't supported, but can easily be added.
 *
 *
 * Please observe that getRandomOutgoingRay may give null, for example
 * if the material is a lightsource or a material reflecting nothing.
 */
public interface Material
{
    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin);

    // IDEA ANTIWARNING
    // Current materials may not use every argument,
    // but they are left here for the future,
    // in case someone would like to implement a more complex BRDF.
    public void applyToRadiance(Ray incoming,
                                Ray outgoing,
                                Normal normal,
                                Radiance incomingRadiance);
}
