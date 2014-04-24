package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.material;


import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.Material;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Color;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Coordinate3;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Normal;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.Ray;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.RayMath;

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
	return RayMath.reflectedRay(ray,normal,origin);
	/*
        // This is not really a random ray, this is a mirror...
        //
        Vector3 vec = new Vector3(ray.getVector());
        vec.normalize();

        Vector3 norm = new Vector3(normal);

        norm.scale(2*(norm.dotProduct(vec)));

        norm.subtract(vec);
        norm.negate();
        norm.normalize();
        return new Ray (origin, norm, ray.isInsideSomething());*/
    }

    public void applyToRadiance(Ray incoming,
                                    Ray outgoing,
                                    Normal normal,
                                    Radiance incomingRadiance){
        incomingRadiance.applyColor(color);
    }
}
