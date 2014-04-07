package ax.hx.hx.pathtracer.pathtracer.material;

import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;
import ax.hx.hx.pathtracer.pathtracer.math.RayFactory;

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

    DiffuseMaterial(){
        // Make it plain white.
        this.color = new Color(1,1,1);
    }
    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin){
        return RayFactory.newCosineImportanceWeightedRay(normal, origin, ray);
	// return RayFactory.newRayFromNormal(normal, origin);
    }

    public Radiance calculateInfluence(Ray incoming, Ray outgoing, Normal normal, Radiance incomingRadiance){
        // We already send out rays distributed by Lambert's cosine
        // law, so just apply our color to incomingRadiance and pass
        // along.

	// The following line is not part of this material, just a test
	// incomingRadiance.factor(normal.dotProduct(outgoing.getVector()));

        incomingRadiance.applyColor(color);
        return incomingRadiance;
    }
}
