package ax.hx.hx.pathtracer.pathtracer;

/**
 * Created by hx on 3/7/14.
 */
public interface Scene
{
    public Influence pathtrace(Ray ray, int depth);
}
