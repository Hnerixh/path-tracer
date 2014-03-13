package ax.hx.hx.pathtracer.pathtracer.math;

/**
 * A ray is a normalized vector with an origin.
 */
public class Ray
{
    private Coordinate3 c;
    private Vector3 v;


    private boolean insideSomething = false;
    public Ray(Coordinate3 c, Vector3 v) {
        this.c = c;
        v.normalize();
        this.v = v;
    }

    public Ray(Coordinate3 c, Vector3 v, boolean insideSomething) {
        this.c = c;
        v.normalize();
        this.v = v;
	this.insideSomething = insideSomething;
    }

    public boolean isInsideSomething() {
	return insideSomething;
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
