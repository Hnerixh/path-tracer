package ax.hx.hx.pathtracer.pathtracer.camera;

import ax.hx.hx.pathtracer.pathtracer.color.Radiance;

/**
 * Created by hx on 4/24/14.
 */
public interface CameraObserver
{
    public void passDone(Radiance[] radiance);
}
