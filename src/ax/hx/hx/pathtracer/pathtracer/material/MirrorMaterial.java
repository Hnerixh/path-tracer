package ax.hx.hx.pathtracer.pathtracer.material;


import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;
import ax.hx.hx.pathtracer.pathtracer.math.Vector3;

/**
 * A perfectly specular material, also known as a mirror.
 */
public class MirrorMaterial implements Material
{
    private final Color color;
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

    public void applyToRadiance(Ray incoming,
                                    Ray outgoing,
                                    Normal normal,
                                    Radiance incomingRadiance){
        incomingRadiance.applyColor(color);
    }
}
