package ax.hx.hx.pathtracer.pathtracer.camera;

/**
 * Simple return to the main camera from it's camera workers,
 * informing of job well done!
 */
class TraceResult {
    public final int traces;
    public TraceResult(int traces){
        this.traces = traces;
    }
}
