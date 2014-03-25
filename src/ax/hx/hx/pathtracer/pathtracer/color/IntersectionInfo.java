package ax.hx.hx.pathtracer.pathtracer.color;

import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Created by hx on 3/25/14.
 */
public class IntersectionInfo {
    public Ray incoming;
    public Normal normal;
    public Coordinate3 hitCoord;
    public IntersectionInfo(Ray incoming, Normal normal, Coordinate3 hitCoord){
        this.incoming = incoming;
        this.normal = normal;
        this.hitCoord = hitCoord;
    }
}
