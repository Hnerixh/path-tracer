package ax.hx.hx.pathtracer.pathtracer;

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
