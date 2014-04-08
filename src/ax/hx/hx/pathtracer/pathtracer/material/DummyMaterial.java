package ax.hx.hx.pathtracer.pathtracer.material;

import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Just a dummy material.
 * Behaves like a pink lamp.
 */
public class DummyMaterial implements Material
{
    public Ray getRandomRay(Ray incoming, Normal normal, Coordinate3 origin){
        return null;
    }

    public Radiance calculateInfluence(Ray incoming, Ray outgoing, Normal normal, Radiance incomingRadiance){
        if (incomingRadiance == null){
            return null;
        }
        return new Radiance(1.0,0.0,1.0);
    }
}
