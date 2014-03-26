package ax.hx.hx.pathtracer.pathtracer.material;

import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.math.*;

/**
 * Created by hx on 3/25/14.
 */
public class DiffuseMirrorBlend implements Material {
    private Color color;
    private double ratio;
    private Material diffuse;
    private Material mirror;

    public DiffuseMirrorBlend(Color color, double ratio){
        this.color = color;
        this.ratio = ratio;

        this.diffuse = new DiffuseMaterial(color);
        this.mirror = new MirrorMaterial(color);
    }
    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin){
        if (Rand.rand() > RayFactory.reflectance(normal, ray, ratio)){
            return diffuse.getRandomRay(ray, normal, origin);
        }
        else {
            return mirror.getRandomRay(ray, normal, origin);
        }
    }

    public Influence calculateInfluence(Ray incoming, Ray outgoing, Normal normal, Influence incomingInfluence){
        incomingInfluence.applyColor(color);
        return incomingInfluence;
    }
}
