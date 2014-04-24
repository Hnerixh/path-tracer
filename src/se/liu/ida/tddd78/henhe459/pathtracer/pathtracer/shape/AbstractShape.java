package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.shape;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.Material;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.Shape;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.material.DummyMaterial;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Coordinate3;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Normal;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Ray;

/**
 * Abstract class for shapes. Implements setters and getters for materials.
 */
public abstract class AbstractShape implements Shape
{

    private Material material = new DummyMaterial();

    void setMaterial(Material material) {
        this.material = material;
    } // Should only be used on initialization.

    public Ray newRay(Ray oldRay, Normal normal, Coordinate3 coord){
	return material.getRandomRay(oldRay, normal, coord);
    }
    public void applyToRadiance(Ray nextRay, Ray oldRay, Normal normal, Radiance radiance){
	material.applyToRadiance(nextRay, oldRay, normal, radiance);
    }
}
