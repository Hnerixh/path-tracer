package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.camera.Background;
import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.color.IntersectionInfo;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements logic for looping over the shapes in the scene,
 * as well as doing the actuall path tracing recursively.
 *
 * Could be improved by replacing recursion with a normal loop,
 * but IMHO that would be trading speed for complexity.
 */
public abstract class AbstractScene implements Scene
{
    private List<Shape> shapes = new ArrayList<Shape>();
    private Background background = new Background();

    public Radiance pathtrace(Ray ray, int depth){
	if (depth <= 0) {
	    return new Radiance(); // This had no influence, so just return nothing.
	}
        Shape hit = null;
        double nearestHit = -1;

        IntersectionInfo info = null;

        Coordinate3 rayOrigin = ray.getOrigin();
        for (Shape shape: shapes) {
            IntersectionInfo intersectionInfo = shape.intersection(ray);

            if (intersectionInfo == null){ // if we got null, we did not hit.
                continue;
            }
            Coordinate3 intersection = intersectionInfo.hitCoord;
            // Apparently we hit something...
            if (intersection.distance(rayOrigin) <= nearestHit || nearestHit == -1){
		//System.out.println("passed");
                hit = shape;
                nearestHit = intersection.distance(rayOrigin);
                info = intersectionInfo;
            }
        }
        // We didn't hit anything :-(
        if (hit == null){
            return worldInfluence(ray);
        }
	else { // We hit something, and hit contains the hit closest
               // to the camera.
	    depth--;
	    return hit.traceLastHit(depth, this, info);
	}

    }

    Radiance worldInfluence(Ray ray){
        Normal normal = new Normal(ray.getVector());
        return background.worldInfluence(normal);
    }

    protected List<Shape> getShapes() {
	return shapes;
    }

    public void setShapes(final List<Shape> shapes) {
	this.shapes = shapes;
    }

    public void setBackground(Background background) {
        this.background = background;
    }
}
