package ax.hx.hx.pathtracer.pathtracer.camera;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ax.hx.hx.pathtracer.image.RGBImage;
import ax.hx.hx.pathtracer.output.Output;
import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.color.Radiance;


/**
  * Created by hx on 3/7/14.
  */
 public class Camera
 {
     private Scene scene;
     private int width;
     private int heigth;
     private int size;
     private double focalLength;
     private Radiance[] radiances;
     private int renderDepth;
     private long totalTraces = 0; // Fun overflow after 1.5 hours if it happens to be an int.
     private long totalSuccesfulTraces = 0;
     private boolean hasWorkers = false;
     private double RRratio;
     private Output output;

     private final CameraWorkerInfo killswitch = new CameraWorkerInfo();
     private int workers = 2;

     private BlockingQueue<CameraJob> jobQueue;
     private BlockingQueue<TraceResult> resultQueue;

     public Camera(Scene scene, double focalLength, Output output, int renderDepth, double RRratio, int workers){
         this.scene = scene;
         this.width = output.getXsize();
         this.heigth = output.getYsize();
         this.size = width * heigth;
         this.focalLength = focalLength;
         this.output = output;
         this.renderDepth = renderDepth;
         this.workers = workers;
         this.RRratio = RRratio;

         jobQueue = new ArrayBlockingQueue<CameraJob>(size);
         resultQueue = new ArrayBlockingQueue<TraceResult>(size);

         radiances = new Radiance[size];
         for (int i = 0; i < size; i++) {
             radiances[i] = new Radiance();
         }

         createWorkers();
     }

     public void killWorkers(){
         killswitch.kill();
     }

     private void createWorkers(){
         CameraWorker worker;
         Thread workerThread;
         for (int i = 0; i < workers; i++) {
             worker = new CameraWorker(jobQueue, resultQueue, renderDepth, RRratio, scene, killswitch, width, heigth, focalLength, radiances);
             workerThread = new Thread(worker);
             workerThread.start();
             hasWorkers = true;
         }
     }

     private void queuePasses(int passes){
         for (int i = 0; i < passes; i++) {
             int start = 0;
             int step = size/workers;
             int end = step;
             for (int j = 0; j < workers; j++) {
                 try {
                     jobQueue.put(new CameraJob(start, end));
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 if (j == workers - 1){
                        start = end;
                        end = size;
                    }
                    else {
                        start = end;
                        end += step;
                    }
             }
         }
     }

     public void render(){
          output.output(radiances);
     }


     private void waitForWorkers(int passes){
         try {
             for (int i = 0; i < workers*passes; i++) {
                TraceResult res = resultQueue.take();
                totalSuccesfulTraces += res.succesfulTraces;
                totalTraces += res.traces;
                double hitratio = (100.0 * (double) totalSuccesfulTraces / (double) totalTraces);
                double spp = ((double) totalTraces / (double) size); // Traced samples per pixel
                double real_spp = spp * hitratio / 100.0; // Actual emitter hits per pixel
                System.out.println("Traces per pixel: " + spp);
                System.out.println("Samples per pixel: " + real_spp);
                System.out.println("Hit probability: " + hitratio + "%");
                System.out.println("-------------");
             }
         }
         catch (InterruptedException e) { System.out.println("Sorry, the camera got interrupted");}
     }

     public void doPasses(int passes){
          if(!hasWorkers){
              System.out.println("Killing workers should only be used for benchmarking purposes. You just did something terribly wrong.");
          }
             queuePasses(passes);
             waitForWorkers(passes);
     }
 }
