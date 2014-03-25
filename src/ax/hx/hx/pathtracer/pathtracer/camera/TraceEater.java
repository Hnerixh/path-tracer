package ax.hx.hx.pathtracer.pathtracer.camera;

import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.color.Influence;

import java.util.concurrent.BlockingQueue;

/**
 * Created by hx on 3/25/14.
 */
public class TraceEater implements Runnable {
    private BlockingQueue<TraceJob> jobQueue;
    private BlockingQueue<TraceResult> resultQueue;
    private int depth;
    private Scene scene;
    private Killswitch killswitch;

    TraceEater(BlockingQueue<TraceJob> jobQueue,
               BlockingQueue<TraceResult> resultQueue,
               int traceDepth,
               Scene scene,
               Killswitch killswitch){
        this.jobQueue = jobQueue;
        this.resultQueue = resultQueue;
        this.depth = traceDepth;
        this.scene = scene;
        this.killswitch = killswitch;
    }

    public void run() {
        TraceJob job;
        Influence influence;
        while (true) {
            if (killswitch.shouldDie()){
                return;
            }
            try {
                job = jobQueue.take();
                influence = scene.pathtrace(job.ray, depth);
                resultQueue.put(new TraceResult(job, influence));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
