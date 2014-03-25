package ax.hx.hx.pathtracer.pathtracer.camera;

import ax.hx.hx.pathtracer.pathtracer.math.Ray;

/**
 * Created by hx on 3/25/14.
 */
public class TraceJob {
    public int pixel;
    public Ray ray;

    public TraceJob(int pixel, Ray ray) {
        this.pixel = pixel;
        this.ray = ray;
    }
}
