package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.camera.Background;
import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.color.IntersectionInfo;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.RandomGen;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

import java.util.ArrayList;
import java.util.List;


public class Scene
{
    private List<Shape> shapes = new ArrayList<Shape>();
    private Background background = new Background();

    public Radiance pathtrace(Ray ray, int depth,
                              double rrRatio,
                              Radiance radiance){

	radiance.reset(1.0, 1.0, 1.0);

        while(true){
	    Shape hit = null;
	    double nearestHitDistance = 0;
	    boolean hasHit = false;
	    IntersectionInfo nearestHitInfo = null;
	    Coordinate3 rayOrigin = ray.getOrigin();
	    // IDEA ANTIWARNING
            // I could skip assigning them null explicidly,
            // but since I actually know they will be null in some (most) cases I want to clarify that.
	    IntersectionInfo hitInfo = null;
	    Coordinate3 intersectionPoint = null;

	    for (Shape shape : shapes) {
                hitInfo = shape.intersection(ray);

                if (hitInfo == null){ // Missed
		    // IDEA ANTIWARNING
		    // "continue with the next shape"
		    // I think this is one of the prettier ways to do this.
                    continue;          // continue with next object
                }
                // Hit something. Figure out if it is the closest hit
                // yet. (or the first)
                intersectionPoint = hitInfo.hitCoord;
                if (intersectionPoint.distance(rayOrigin) <= nearestHitDistance
                    || ! hasHit){
		    hasHit = true;
                    hit = shape;
                    nearestHitDistance = intersectionPoint.distance(rayOrigin);
                    nearestHitInfo = hitInfo;
                }
            }

            // Missed everything... Depressing
            // Anyway, done here, return.
            if (hit == null){
                radiance.applyColor(worldColor(ray));
                return radiance;
            }

            // Hit something :D
	    Normal normal = nearestHitInfo.normal;
	    Coordinate3 coord = nearestHitInfo.hitCoord;

	    // nextRay may be null
	    Ray nextRay = hit.newRay(ray, normal, coord);

	    // Apply the material to our radiance. The material will
            // handle the null situation.
            hit.applyToRadiance(nextRay, ray, normal, radiance);

            // Here the null situation is handled
            // Hit something like a lamp or dummy material. No further
            // rays sent, just return.
            if (nextRay == null){
                return radiance;
            }

            // Terminate path?  Path is terminated by russian roulette
            // once a minimum depth is reached.
            depth--;
            if (depth <= 0){
                // Apply russian roulette at this stage.
                if (RandomGen.rand() < rrRatio){
                    // Terminate it...
                    radiance.discard();
                    return radiance;
                }
            }
            // Do it again!
            ray = nextRay;
        }
    }

    Color worldColor(Ray ray){
        Normal normal = new Normal(ray.getVector());
        return background.worldColor(normal);
    }

// --Commented out by Inspection START (4/15/14 1:19 AM):
//    protected List<Shape> getShapes() {
//	return shapes;
//    }
// --Commented out by Inspection STOP (4/15/14 1:19 AM)

    public void setShapes(final List<Shape> shapes) {
	this.shapes = shapes;
    }

    public void setBackground(Background background) {
        this.background = background;
    }
}
