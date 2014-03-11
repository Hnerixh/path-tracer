package ax.hx.hx.pathtracer.pathtracer;

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
