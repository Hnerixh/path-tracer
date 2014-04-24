package se.liu.ida.tddd78.henhe459.pathtracer.output;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;

/**
 * A tonemapper tone-maps one Radiance to a r,b or g intensity.
 */
public interface Tonemapper {
    public double tonemapR(Radiance radiance);
    public double tonemapG(Radiance radiance);
    public double tonemapB(Radiance radiance);
}
