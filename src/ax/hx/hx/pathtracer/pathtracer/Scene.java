package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Created by hx on 3/7/14.
 */
public interface Scene
{
    public Influence pathtrace(Ray ray, int depth);
}
