package ax.hx.hx.pathtracer.pathtracer.camera;

import ax.hx.hx.pathtracer.pathtracer.color.Influence;

import java.util.concurrent.BlockingQueue;

/**
 * Created by hx on 3/25/14.
 */
public class InfluenceHandler implements Runnable {
    private Influence[] influences;
    private BlockingQueue<TraceResult>  queue;
    private int traceCounter = 0;
    private int incoming;

    public InfluenceHandler(Influence[] influences, BlockingQueue<Influence> queue, int incoming) {
        this.influences = influences;
        //this.queue = queue;
        this.incoming = incoming;
    }

    public void run(){
        Influence incoming;
        int pixel;
        TraceResult job;
        while(true){
            try {
                job = queue.take();
                traceCounter++;
                influences[job.pixel].addInfluence(job.influence);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
