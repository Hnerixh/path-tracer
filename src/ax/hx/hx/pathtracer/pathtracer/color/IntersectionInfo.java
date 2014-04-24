package ax.hx.hx.pathtracer.pathtracer.color;

import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * This is a class containing all necessary information about a
 * successful ray-shape intersection for use in following radiance
 * calculations.  Stores the intersected ray, the (macro)surface
 * normal of the shape at the intersection point, as well as the
 * coordinates of the intersection.
 */
public class IntersectionInfo {
    // IDEA ANTIWARNING
    // May be used by future materials.
    // IDEA ANTIWARNING II
    // Simple data holder, no functionality.
    // Using public fields for simplicity.
    
    /**
     * The incoming ray
     */
    public final Ray incoming;
    /**
     * The normal in the place of the hit.
     */
    public final Normal normal;
    /**
     * Coordinates of the hit.
     */
    public final Coordinate3 hitCoord;
    public IntersectionInfo(Ray incoming, Normal normal, Coordinate3 hitCoord){
        this.incoming = incoming;
        this.normal = normal;
        this.hitCoord = hitCoord;
    }
}
