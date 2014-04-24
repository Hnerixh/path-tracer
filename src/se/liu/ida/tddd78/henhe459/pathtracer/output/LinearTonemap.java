package se.liu.ida.tddd78.henhe459.pathtracer.output;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;

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
