package ax.hx.hx.pathtracer.pathtracer.shape;

import ax.hx.hx.pathtracer.pathtracer.AbstractShape;
import ax.hx.hx.pathtracer.pathtracer.color.IntersectionInfo;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;
import ax.hx.hx.pathtracer.pathtracer.math.Vector3;

/**
 * Created by hx on 3/10/14.
 */
public class PlaneShape extends AbstractShape
{
    private Normal planeNormal;
    private Coordinate3 coord;

    public IntersectionInfo intersection(Ray ray){
        // Calculate the intersection between a ray and a plane.
        // http://www.cs.princeton.edu/courses/archive/fall00/cs426/lectures/raycast/sld017.htm

        double d = planeNormal.dotProduct(new Vector3(coord));

        Vector3 v = new Vector3(ray.getVector());
        Vector3 p0 = new Vector3(ray.getOrigin());

        double a = - (p0.dotProduct(planeNormal)) - d;
        double b = v.dotProduct(planeNormal);

        // We are facing straight towards the edge of the plane, and
        // will not intersect.
        if (b == 0.0){
            return null;
        }

        // t is the distance from the vectors origin to the
        // intersection point on the plane in the direction of the
        // rays vector v.
        double t = a / b;
        if (t <= 0) {
            return null;
        }
	t -= 0.000001; // Avoid rounding errors.
        v.scale(t);
        p0.add(v);

        // This is the intersection point
        Coordinate3 intersect = new Coordinate3(p0.getX(), p0.getY(), p0.getZ());

        // How is the normal? Do we need to negate the normal before we save it?
        Normal normal = new Normal(planeNormal);
        if (b > 0){
            normal.negate();
        }

        IntersectionInfo info = new IntersectionInfo(ray, normal, intersect);
        return info;
    }

    public PlaneShape(Normal normal, Coordinate3 coord){
        this.planeNormal = normal;
        this.coord = coord;
    }
}
