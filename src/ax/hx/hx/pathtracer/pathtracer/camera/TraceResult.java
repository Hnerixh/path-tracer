package ax.hx.hx.pathtracer.pathtracer.camera;

import ax.hx.hx.pathtracer.pathtracer.color.Influence;

/**
 * Created by hx on 3/25/14.
 */
public class TraceResult {
    public int pixel;
    public Influence influence;
    public TraceResult(int pixel, Influence influence){
        this.pixel = pixel;
        this.influence = influence;
    }
}
