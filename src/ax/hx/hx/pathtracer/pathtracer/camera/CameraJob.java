package ax.hx.hx.pathtracer.pathtracer.camera;

/**
 * Description of what interval of pixels a CameraWorker should render.
 */
class CameraJob {
    public final int start;
    public final int end;

    public CameraJob(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
