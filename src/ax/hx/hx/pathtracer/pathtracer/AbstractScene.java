package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx on 3/7/14.
 */
public abstract class AbstractScene implements Scene
{
    private List<Shape> shapes = new ArrayList<Shape>();

    public Influence pathtrace(Ray ray, int depth){
	if (depth <= 0) {
	    return new Influence(0,0,0);
	}
        Shape hit = null;
        double nearestHit = -1;
        // System.out.println("Pathtracing " + ray);
        Coordinate3 hitCoord = null;

        Coordinate3 rayOrigin = ray.getOrigin();
        for (Shape shape: shapes) {
            Coordinate3 intersection = shape.intersection(ray);
            if (intersection == null){ // if we got null, we did not hit.
                continue;
            }
            // Apparently we hit something...
	    //System.out.println(intersection.distance(rayOrigin));
            if (intersection.distance(rayOrigin) <= nearestHit || nearestHit == -1){
		//System.out.println("passed");
                hit = shape;
                nearestHit = intersection.distance(rayOrigin);
                hitCoord = intersection;
            }
        }
        // We didn't hit anything :-(
        if (hit == null){
            return worldInfluence(ray);
        }
	else { // We hit something, and hit contains the hit closest
               // to the camera.
	    depth--;
	    return hit.traceLastHit(depth, this);
	}

    }

    Influence worldInfluence(Ray ray){
        return new Influence(0.0,0.0,0.0);
    }

    public List<Shape> getShapes() {
	return shapes;
    }

    public void setShapes(final List<Shape> shapes) {
	this.shapes = shapes;
    }
}
