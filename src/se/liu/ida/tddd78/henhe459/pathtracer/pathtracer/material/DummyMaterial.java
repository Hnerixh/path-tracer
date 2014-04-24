package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.material;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Color;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Coordinate3;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.Material;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Normal;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Ray;

/**
 * Just a dummy material.
 * Behaves like a pink lamp.
 */
public class DummyMaterial implements Material
{
    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin){
        return null;
    }

    public void applyToRadiance(Ray incoming, Ray outgoing, Normal normal, Radiance incomingRadiance){
        incomingRadiance.applyColor(new Color(1.0, 0.0, 1.0));
    }
}
