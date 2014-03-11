package ax.hx.hx.pathtracer.pathtracer;

/**
 * Created by hx on 3/11/14.
 */
public class TriangleShape extends AbstractShape
{
    private Normal normal;

    private Vector3 c1;
    private Vector3 c2;
    private Vector3 c3;
    private final double EPSILON = 0.000001;

    public TriangleShape(Coordinate3 c1, Coordinate3 c2, Coordinate3 c3){
        this.c1 = new Vector3(c1);
        this.c2 = new Vector3(c2);
        this.c3 = new Vector3(c3);

        // I have no idea why, but it works...
        this.c1 = negateXY(this.c1);
        this.c2 = negateXY(this.c2);
        this.c3 = negateXY(this.c3);

        Vector3 temp1 = new Vector3(c2);
        Vector3 temp2 = new Vector3(c3);

        temp1.subtract(this.c1);
        temp2.subtract(this.c1);

        this.normal = new Normal(temp1.crossProduct(temp2));
    }

    private Vector3 negateXY(Vector3 v){
	double x,y,z;
	x = v.getX();
	y = v.getY();
	z = v.getZ();

        x = -x;
        y = -y;

        return new Vector3(x,y,z);
    }

    public Coordinate3 intersection(Ray ray){
        // Following:
        // http://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm
        //
        // Please observe, I have no idea how this works, but I did
        // indeed write the code.

        // Triangle vertices
        Vector3 v1 = new Vector3(c1);
        Vector3 v2 = new Vector3(c2);
        Vector3 v3 = new Vector3(c3);

        // Vectors for the incoming ray
        Vector3 dr = new Vector3(ray.getVector());
        Vector3 or = new Vector3(ray.getOrigin().x, ray.getOrigin().y, ray.getOrigin().z);

        // doubles we use during calculation
        double det;
        double inv_det;
        double u;
        double v;
	double t;

        // Find two edge vectors
        Vector3 edge1 = new Vector3(v2);
        edge1.subtract(v1);
        Vector3 edge2 = new Vector3(v3);
        edge2.subtract(v1);

        // Determinant
        Vector3 p = dr.crossProduct(edge2);
        det = edge1.dotProduct(p);

        // Ray parallel to the triangles plane.
        if (det > -EPSILON && det < EPSILON){
            return null;
        }

        inv_det = 1/det;

        Vector3 tv = new Vector3(or);
        tv.subtract(v1);

        u = tv.dotProduct(p) * inv_det;

        if (u < 0.0 || u > 1.0){
            return null;
        }

        Vector3 q = tv.crossProduct(edge1);

        v = dr.dotProduct(q) * inv_det;

        if (v < 0 || u + v > 1){
            return null;
        }

	t = edge2.dotProduct(q) * inv_det;

        if (t <= EPSILON){
            return null;
        }

        // We now have a distance, and know it to be within the triangle
        t -= EPSILON;
        dr.scale(t);
        or.add(dr);

        // This is the intersection point
        Coordinate3 intersection = new Coordinate3(or.getX(), or.getY(), or.getZ());

        // How is the normal? Do we need to negate the normal before we save it?
        Normal normal = new Normal(this.normal);
        if (dr.dotProduct(normal) > 0){
            normal.negate();
        }

        // Set variables for later.
        setNormal(normal);
        setIncoming(ray);
        setHitCoord(intersection);
        return intersection;
    }
}
