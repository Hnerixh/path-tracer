package ax.hx.hx.pathtracer.pathtracer.camera;

/**
 * Simple return to the main camera from it's camera workers,
 * informing of job well done along with some info.
 * Also, the variable succesfulTraces contains the number of successful traces, aka traces that hit an emitter.
 */
class TraceResult {
    public final int traces;
    public final int succesfulTraces;
    TraceResult(int traces, int succesfulTraces){
        this.traces = traces;
        this.succesfulTraces = succesfulTraces;
    }
}
