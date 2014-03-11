package ax.hx.hx.pathtracer.pathtracer.material;

import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Created by hx on 3/8/14.
 */
public class DummyMaterial implements Material
{
    public Ray getRandomRay(Ray incoming, Normal normal, Coordinate3 origin){
        return null;
    }

    public Influence calculateInfluence(Ray incoming, Ray outgoing, Normal normal, Influence incomingInfluence){
        return new Influence(1.0,0.0,1.0);
    }
}
