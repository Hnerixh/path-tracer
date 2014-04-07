package ax.hx.hx.pathtracer.pathtracer.camera;

import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Rand;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;
import ax.hx.hx.pathtracer.pathtracer.math.Vector3;

import java.util.concurrent.BlockingQueue;

/**
 * A CameraWorker does the actual rendering, but gets RenderJobs from Camera.
 * The rendering jobs are recieved from a BlockingQueue.
 * The result of a finished job are sent down another BlockingQueue.
 *
 * This class also writes to the main grid of total radiance
 */
class CameraWorker implements Runnable {
    private final double focalLength;
    private final BlockingQueue<CameraJob> jobQueue;
    private final BlockingQueue<TraceResult> resultQueue;
    private final int depth;
    private final Scene scene;
    private final CameraWorkerInfo killswitch;
    private final int width;
    private final int heigth;
    private final Radiance[] radiances;

    CameraWorker(BlockingQueue<CameraJob> jobQueue,
                 BlockingQueue<TraceResult> resultQueue,
                 int traceDepth,
                 Scene scene,
                 CameraWorkerInfo killswitch,
                 int width,
                 int heigth,
                 double focalLength,
                 Radiance[] radiances){

        this.jobQueue = jobQueue;
        this.resultQueue = resultQueue;
        this.depth = traceDepth;
        this.scene = scene;
        this.killswitch = killswitch;
        this.width = width;
        this.heigth = heigth;
        this.focalLength = focalLength;
        this.radiances = radiances;
    }

    public void run() {
        CameraJob job;
        Radiance radiance;
        while (true) {
            if (killswitch.shouldDie()){
                return;
            }
            try {
                job = jobQueue.take();
                int traces = 0;
                for (int i = job.start; i < job.end; i++) {
                    radiance = scene.pathtrace(rayForPixel(i), depth);
                    radiances[i].addInfluence(radiance);
                    traces++;
                }
                resultQueue.put(new TraceResult(traces));
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
