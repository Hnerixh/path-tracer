package ax.hx.hx.pathtracer.pathtracer.math;

/**
 * A ray is a normalized vector with an origin.
 */
public class Ray
{
    private final Coordinate3 coordinate;
    private final Vector3 v;


    private boolean insideSomething = false;
    public Ray(Coordinate3 coordinate, Vector3 v) {
        this.coordinate = coordinate;
        v.normalize();
        this.v = v;
    }

    public Ray(Coordinate3 coordinate, Vector3 v, boolean insideSomething) {
        this.coordinate = coordinate;
        v.normalize();
        this.v = v;
	this.insideSomething = insideSomething;
    }

    public boolean isInsideSomething() {
	return insideSomething;
    }


    @Override public String toString() {
	return "Ray{" +
	       "coordinate=" + coordinate +
	       ", v=" + v +
	       '}';
    }

    public Coordinate3 getOrigin(){
        return coordinate;
    }

    public Vector3 getVector(){
        return v;
    }
}
