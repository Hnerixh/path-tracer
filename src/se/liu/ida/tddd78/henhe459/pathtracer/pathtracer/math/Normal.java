package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math;

/**
 * Subclass of Vector3, where the vector is guaranteed to be
 * normalized
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
