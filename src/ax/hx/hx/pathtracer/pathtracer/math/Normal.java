package ax.hx.hx.pathtracer.pathtracer.math;

/**
 * Created by hx on 3/7/14.
 */
public class Normal extends Vector3
{
    public Normal(double x, double y, double z){
        super(x,y,z);
        normalize();
        }

    public Normal(Normal normal){
        super(normal);
    }

    public Normal(Vector3 normal){
        super(normal);
        normalize();
    }
}
