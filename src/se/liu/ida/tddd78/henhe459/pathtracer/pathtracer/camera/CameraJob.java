package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.camera;

/**
 * Description of what interval of pixels a CameraWorker should render.
 */
class CameraJob {
    public final int start;
    public final int end;

    CameraJob(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
