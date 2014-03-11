package ax.hx.hx.pathtracer.pathtracer.material;

import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Created by hx on 3/9/14.
 */
public class LightMaterial implements Material
{
    private Color color;

    public LightMaterial(Color color){
        this.color = color;
    }

    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin){
        return null;
    }

    public Influence calculateInfluence(Ray incoming, Ray outgoing, Normal normal, Influence incomingInfluence){
        return new Influence(color.r, color.g, color.b);
    }
}
