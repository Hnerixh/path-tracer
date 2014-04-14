package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.pathtracer.camera.Background;
import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.color.IntersectionInfo;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Rand;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractScene implements Scene
{
    private List<Shape> shapes = new ArrayList<Shape>();
    private Background background = new Background();

    // public Radiance pathtrace(Ray ray, int depth){
    //     while(depth > 0){
    //         // Iterate through all shapes in the scene
    //         Shape hit = null;
    //         double nearestHit = -1;

    //         IntersectionInfo info = null;

    //         Coordinate3 rayOrigin = ray.getOrigin();

    //         for (Shape shape: shapes) {
    //             IntersectionInfo intersectionInfo = shape.intersection(ray);

    //             if (intersectionInfo == null){ // if we got null, we did not hit.
    //                 continue;
    //             }

    //             Coordinate3 intersection = intersectionInfo.hitCoord;
    //             // Apparently we hit something...
    //             if (intersection.distance(rayOrigin) <= nearestHit || nearestHit == -1){
    //                 hit = shape;
    //                 nearestHit = intersection.distance(rayOrigin);
    //                 info = intersectionInfo;
    //             }
    //         }
    //         // We didn't hit anything :-(
    //         if (hit == null){
    //             return worldColor(ray);
    //         }
    //         else { // We hit something, and hit contains the hit closest
    //             // to the camera.
    //             depth--;
    //             return hit.traceLastHit(depth, this, info);
    //         }
    //     }
    //     // Hit depth limit.
    //     return null;
    // }


    public Radiance pathtrace(Ray ray, int depth,
                              double RRratio,
                              Radiance radiance){

	radiance.reset(1.0, 1.0, 1.0);

        while(true){
	    Shape hit = null;
	    double nearest_hit_distance = -1;
	    IntersectionInfo nearest_hit_info = null;
	    Coordinate3 ray_origin = ray.getOrigin();
	    // IDEA ANTIWARNING
            // Apparently I did not use the null I assigned the variables here.
            // I could skip assigning them null explicidly, but then I loose clarity.
	    IntersectionInfo hit_info = null;
	    Coordinate3 intersection_point = null;

	    for (Shape shape : shapes) {
                hit_info = shape.intersection(ray);

                if (hit_info == null){ // Missed
		    // IDEA ANTIWARNING
		    // "continue with the next shape"
		    // Easy to understand, unlike other ways to do this.
                    continue;          // continue with next object
                }
                // Hit something. Figure out if it is the closest hit
                // yet. (or the first)
                intersection_point = hit_info.hitCoord;
		// IDEA ANTIWARNING yes, it is okay to compare floating point in this case.
                if (intersection_point.distance(ray_origin) <= nearest_hit_distance
                    || nearest_hit_distance == -1){ // If -1, this is the first hit.
                    hit = shape;
                    nearest_hit_distance = intersection_point.distance(ray_origin);
                    nearest_hit_info = hit_info;
                }
            }

            // Missed everything... Depressing
            // Anyway, done here, return.
            if (hit == null){
                radiance.applyColor(worldColor(ray));
                return radiance;
            }

            // Hit something :D
	    Material material = hit.getMaterial();
	    Normal normal = nearest_hit_info.normal;
	    Coordinate3 coord = nearest_hit_info.hitCoord;

	    // next_ray may be null
	    Ray next_ray = material.getRandomRay(ray, normal, coord);

	    // Apply the material to our radiance. The material will
            // handle the null situation.
            material.applyToRadiance(next_ray, ray, normal, radiance);

            // Here the null situation is handled
            // Hit something like a lamp or dummy material. No further
            // rays sent, just return.
            if (next_ray == null){
                return radiance;
            }

            // Terminate path?  Path is terminated by russian roulette
            // once a minimum depth is reached.
            depth--;
            if (depth <= 0){
                // Apply russian roulette at this stage.
                if (Rand.rand() < RRratio){
                    // Terminate it...
                    radiance.discard();
                    return radiance;
                }
            }
            // Do it again!
            ray = next_ray;
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
