package ax.hx.hx.pathtracer.pathtracer;

/**
 * A ray is a normalized vector with an origin.
 */
public class Ray
{
    private Coordinate3 c;
    private Vector3 v;
    Ray(Coordinate3 c, Vector3 v) {
        this.c = c;
        v.normalize();
        this.v = v;
    }

    @Override public String toString() {
	return "Ray{" +
	       "c=" + c +
	       ", v=" + v +
	       '}';
    }

    public Coordinate3 getOrigin(){
        return c;
    }

    public Vector3 getVector(){
        return v;
    }
}
