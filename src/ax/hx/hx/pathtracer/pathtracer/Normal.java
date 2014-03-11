package ax.hx.hx.pathtracer.pathtracer;

/**
 * Created by hx on 3/7/14.
 */
public class Normal extends Vector3
{
    Normal(double x, double y, double z){
        super(x,y,z);
        normalize();
        }

    Normal(Normal normal){
        super(normal);
    }

    Normal(Vector3 normal){
        super(normal);
        normalize();
    }
}
