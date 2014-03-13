package ax.hx.hx.pathtracer.pathtracer.material;


import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;
import ax.hx.hx.pathtracer.pathtracer.math.Vector3;

/**
 * Created by hx on 3/11/14.
 */
public class MirrorMaterial implements Material
{
    private Color color;
    public MirrorMaterial(Color color){
        this.color = color;
    }

    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin){
        // This is not really a random ray, this is a mirror...
        //
        Vector3 vec = new Vector3(ray.getVector());
        vec.normalize();

        Vector3 norm = new Vector3(normal);

        norm.scale(2*(norm.dotProduct(vec)));

        norm.subtract(vec);
        norm.negate();
        norm.normalize();
        return new Ray (origin, norm, ray.isInsideSomething());
    }

    public Influence calculateInfluence(Ray incoming, Ray outgoing, Normal normal, Influence incomingInfluence){
        incomingInfluence.applyColor(color);
        return incomingInfluence;
    }
}
