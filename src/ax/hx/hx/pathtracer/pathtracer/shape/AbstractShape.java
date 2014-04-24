package ax.hx.hx.pathtracer.pathtracer.shape;

import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.Shape;
import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.material.DummyMaterial;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Abstract class for shapes. Implements setters and getters for materials.
 */
public abstract class AbstractShape implements Shape
{

    private Material material = new DummyMaterial();

    void setMaterial(Material material) {
        this.material = material;
    } // Should only be used on initialization.

    public Material getMaterial(){
        return material;
    }

    public Ray newRay(Ray oldRay, Normal normal, Coordinate3 coord){
	return material.getRandomRay(oldRay, normal, coord);
    }
    public void applyToRadiance(Ray nextRay, Ray oldRay, Normal normal, Radiance radiance){
	material.applyToRadiance(nextRay, oldRay, normal, radiance);
    }
}
