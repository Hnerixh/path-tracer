package ax.hx.hx.pathtracer.pathtracer;

/**
 * Created by hx on 3/7/14.
 */
public interface Shape
{
    Coordinate3 intersection(Ray ray);
    Influence traceLastHit(int depth, Scene scene);
}
