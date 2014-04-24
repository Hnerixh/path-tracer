package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.material;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Color;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Coordinate3;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.Material;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Normal;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Ray;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.RayMath;

/**
 * This is a diffuse material.
 * The outgoing rays are distributed according to Lambert's cosine law
 * The Radiance is calculated using Lambertian reflectance.
 */
public class DiffuseMaterial implements Material
{
    private final Color color;
    public DiffuseMaterial(Color color){
	this.color = color;
    }

    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin){
        return RayMath.newCosineImportanceWeightedRay(normal, origin, ray);
	// return RayMath.newRayFromNormal(normal, origin);
    }

    public void applyToRadiance(Ray incoming, Ray outgoing, Normal normal, Radiance incomingRadiance){
        /*
        We already send out rays distributed by Lambert's cosine
        law, so just apply our color to incomingRadiance and pass
        along.
        */

	incomingRadiance.applyColor(color);
    }
}
