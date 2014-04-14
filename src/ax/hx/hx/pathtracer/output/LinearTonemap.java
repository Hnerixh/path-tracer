package ax.hx.hx.pathtracer.output;

import ax.hx.hx.pathtracer.pathtracer.color.Radiance;

/**
 * Linear tonemapper, with black at 0, and pure white at 1.
 * Simplest there is!
 */
public class LinearTonemap implements Tonemapper {
    public double tonemapR(Radiance radiance){
        return radiance.getR();
    }
    public double tonemapG(Radiance radiance){
        return radiance.getG();
    }
    public double tonemapB(Radiance radiance){
        return radiance.getB();
    }
}
