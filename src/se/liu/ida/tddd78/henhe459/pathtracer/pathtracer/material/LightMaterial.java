package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.material;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Color;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Coordinate3;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.Material;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Normal;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Ray;

/**
 * The basic light material used in all emitting materials.
 * Emitting materials just have one color. They also won't spawn new rays.
 */
public class LightMaterial implements Material
{
    private final Color color;

    public LightMaterial(Color color){
        this.color = color;
    }

    public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin){
        return null;
    }

    public void applyToRadiance(Ray incoming,
                                    Ray outgoing,
                                    Normal normal,
                                    Radiance incomingRadiance){
        incomingRadiance.applyColor(color);
    }
}
