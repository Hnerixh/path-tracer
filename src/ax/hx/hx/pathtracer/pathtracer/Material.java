package ax.hx.hx.pathtracer.pathtracer;

/**
 * Please observe that getRandomOutgoingRay may give null, for example if the material is a lightsource or a material reflecting nothing.
 */
public interface Material
{
    public Ray getRandomRay(Ray incoming, Normal normal);
    public Influence calculateInfluence(Ray incoming, Ray outgoing, Normal normal, Influence incomingInfluence);
}
