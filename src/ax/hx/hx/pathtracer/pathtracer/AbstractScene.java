package ax.hx.hx.pathtracer.pathtracer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx on 3/7/14.
 */
public abstract class AbstractScene implements Scene
{
    List<Shape> shapes = new ArrayList<Shape>();

    public Influence pathtrace(Ray ray, int depth){
        Shape hit = null;
        double nearestHit = 0.0;
        // System.out.println("Pathtracing " + ray);
        Coordinate3 hitCoord = null;

        Coordinate3 rayOrigin = ray.getOrigin();
        for (Shape shape: shapes) {
            Coordinate3 intersection = shape.intersection(ray);
            if (intersection == null){ // if we got null, we did not hit.
                continue;
            }
            // Apparently we hit something...
            if (intersection.distance(rayOrigin) > nearestHit){
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
        return new Influence(0,0,0);
    }
}
