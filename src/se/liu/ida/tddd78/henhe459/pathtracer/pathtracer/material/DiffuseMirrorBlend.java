package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.material;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.Material;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Color;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.*;

/**
 * A material which decides between diffuse and perfect specular
 * reflection. This is done using Russian Roulette and the reflectance
 * of the surface according to the Schlick approximation.
 */
public class DiffuseMirrorBlend implements Material {
    private final Color color;
    private final double ratio;
    private final Material diffuse;
    private final Material mirror;

    public DiffuseMirrorBlend(Color color, double ratio){
        this.color = color;
        this.ratio = ratio;

        this.diffuse = new DiffuseMaterial(color);
        this.mirror = new MirrorMaterial(color);
    }
    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin){
        if (RandomGen.rand() > RayMath.reflectance(normal, ray, ratio)){
            return diffuse.getRandomRay(ray, normal, origin);
        }
        else {
            return mirror.getRandomRay(ray, normal, origin);
        }
    }

    public void applyToRadiance(Ray incoming, Ray outgoing, Normal normal, Radiance incomingRadiance){
        incomingRadiance.applyColor(color);
    }
}
