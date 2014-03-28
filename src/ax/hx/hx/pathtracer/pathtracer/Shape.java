package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.color.IntersectionInfo;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Created by hx on 3/7/14.
 */
public interface Shape
{
    IntersectionInfo intersection(Ray ray);
    Influence traceLastHit(int depth, Scene scene, IntersectionInfo info);
    public void setMaterial(Material material);
}
