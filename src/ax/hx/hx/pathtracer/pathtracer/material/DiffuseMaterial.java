package ax.hx.hx.pathtracer.pathtracer.material;

import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;
import ax.hx.hx.pathtracer.pathtracer.math.RayFactory;

/**
 * This is a diffuse material.
 * The outgoing rays are distributed according to Lambert's cosine law
 * The Influence is calculated using Lambertian reflectance.
 */
public class DiffuseMaterial implements Material
{
    private Color color;
    public DiffuseMaterial(Color color){
	this.color = color;
    }

    public DiffuseMaterial(){
        // Make it plain white.
        this.color = new Color(1,1,1);
    }
    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin){
        return RayFactory.newCosineImportanceWeightedRay(normal, origin);
	// return RayFactory.newRayFromNormal(normal, origin);
    }

    public Influence calculateInfluence(Ray incoming, Ray outgoing, Normal normal, Influence incomingInfluence){
        // We already send out rays distributed by Lambert's cosine
        // law, so just apply our color to incomingInfluence and pass
        // along.

	// The following line is not part of this material, just a test
	// incomingInfluence.factor(normal.dotProduct(outgoing.getVector()));

        incomingInfluence.applyColor(color);
        return incomingInfluence;
    }
}
