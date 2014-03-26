package ax.hx.hx.pathtracer.pathtracer.camera;

import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Rand;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;
import ax.hx.hx.pathtracer.pathtracer.math.Vector3;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by hx on 3/25/14.
 */
public class CameraWorker implements Runnable {
    private double focalLength;
    private BlockingQueue<CameraJob> jobQueue;
    private BlockingQueue<TraceResult> resultQueue;
    private int depth;
    private Scene scene;
    private CameraWorkerInfo killswitch;
    private Random rnd;
    private int width;
    private int heigth;
    private int size;
    private boolean wait;

    CameraWorker(BlockingQueue<CameraJob> jobQueue,
                 BlockingQueue<TraceResult> resultQueue,
                 int traceDepth,
                 Scene scene,
                 CameraWorkerInfo killswitch,
                 int width,
                 int heigth,
                 double focalLength,
                 boolean wait){

        this.jobQueue = jobQueue;
        this.resultQueue = resultQueue;
        this.depth = traceDepth;
        this.scene = scene;
        this.killswitch = killswitch;
        this.width = width;
        this.heigth = heigth;
        this.wait = wait;
        this.size = width*heigth;
        this.focalLength = focalLength;

        this.rnd = new Random();
    }

    public void run() {
        CameraJob job;
        Influence influence;
        while (true) {
            if (killswitch.shouldDie()){
                return;
            }
            try {
                if (wait) {jobQueue.take();}
                for (int i = 0; i < size; i++) {
                    influence = scene.pathtrace(rayForPixel(i), depth);
                    resultQueue.put(new TraceResult(i, influence));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Ray rayForPixel(int i){
        // Get x, y from i, z from focalLength. Put x and y so that
        // $x \land y \in [-0.5,0.5]$. Displace x and y within the pixel. Generate vector -> ray.

        // Get x and y
        double x = i % width;
        double y = (i-x)/width;

        // Scale
        double scale = Math.max(width, heigth);
        x /= scale;
        y /= scale;
        x -= (width / scale) / 2;
        y -= (heigth / scale) / 2;

        // Size of image plane is 1.0;
        // This offsets the coordinates randomly within the pixel.
        // -> Don't need AA.
        double pixelSize = 1.0/scale;
        x += pixelSize* Rand.rand();
        y += pixelSize*Rand.rand();

        // Generate ray
        Vector3 vector = new Vector3(x, y, focalLength);
        Coordinate3 coord = new Coordinate3(0,0,0);
        return new Ray(coord, vector);
    }

}
