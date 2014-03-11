package ax.hx.hx.pathtracer.pathtracer.scene;

import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;
import ax.hx.hx.pathtracer.pathtracer.Scene;

/**
 * Created by hx on 3/7/14.
 */
public class RandomTestScene implements Scene
{
    public Influence pathtrace(Ray ray, int depth){
	return new Influence();
    }
}
