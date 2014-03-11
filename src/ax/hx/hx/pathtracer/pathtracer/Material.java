package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Please observe that getRandomOutgoingRay may give null, for example if the material is a lightsource or a material reflecting nothing.
 */
public interface Material
{
    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin);
    public Influence calculateInfluence(Ray incoming, Ray outgoing, Normal normal, Influence incomingInfluence);
}
