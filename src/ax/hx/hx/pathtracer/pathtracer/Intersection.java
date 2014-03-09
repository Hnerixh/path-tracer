package ax.hx.hx.pathtracer.pathtracer;

/**
 * Created by hx on 3/7/14.
 */
public class Intersection
{
    public Coordinate3 coordinate;
    public Normal normal;

    Intersection(Coordinate3 coordinate, Normal normal){
        this.coordinate = coordinate;
        this.normal = normal;
    }
}
