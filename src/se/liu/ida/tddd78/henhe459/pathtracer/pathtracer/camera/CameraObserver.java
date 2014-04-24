package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.camera;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;

/**
 * Created by hx on 4/24/14.
 */
public interface CameraObserver
{
    public void passDone(Radiance[] radiances);
}
