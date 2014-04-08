package ax.hx.hx.pathtracer.pathtracer.material;

import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

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
