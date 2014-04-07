package ax.hx.hx.pathtracer.pathtracer.camera;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ax.hx.hx.pathtracer.image.RGBImage;
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
     private Radiance[] influenses;
     private RGBImage image;
     private int renderDepth;
     private long totalTraces = 0; // Fun overflow after 1.5 hours if it happens to be an int.
     private boolean hasWorkers = false;

     private final CameraWorkerInfo killswitch = new CameraWorkerInfo();
     private int workers = 2;

     private BlockingQueue<CameraJob> jobQueue;
     private BlockingQueue<TraceResult> resultQueue;

 //    public Camera(Scene scene, double focalLength, RGBImage image, int renderDepth){
 //        init(scene, focalLength, image, renderDepth, workers);
 //    }

     public Camera(Scene scene, double focalLength, RGBImage image, int renderDepth, int workers){
         init(scene, focalLength, image, renderDepth, workers);
     }

     private void init(Scene scene, double focalLength, RGBImage image, int renderDepth, int workers){
         this.scene = scene;
         this.width = image.getWidth();
         this.heigth = image.getHeight();
         this.size = width * heigth;
         this.focalLength = focalLength;
         this.image = image;
         this.renderDepth = renderDepth;
         this.workers = workers;

         jobQueue = new ArrayBlockingQueue<CameraJob>(size);
         resultQueue = new ArrayBlockingQueue<TraceResult>(size);

         influenses = new Radiance[size];
         for (int i = 0; i < size; i++) {
             influenses[i] = new Radiance();
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
             worker = new CameraWorker(jobQueue, resultQueue, renderDepth, scene, killswitch, width, heigth, focalLength, influenses);
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
         for (int i = 0; i < size; i++) {
             image.appendPixel(influenses[i].getPixel());
         }
     }


     private void waitForWorkers(int passes){
         try {
             for (int i = 0; i < workers*passes; i++) {
                totalTraces += resultQueue.take().traces;
                System.out.println(((double) totalTraces / (double) size) + " samples per pixel");
             }
         }
         catch (InterruptedException e) { System.out.println("Sorry, the camera got interrupted");}
     }

     public void doPasses(int passes){
          if(!hasWorkers){
              System.out.println("Killing of workers should only be used for benchmarking purposes. You just did something terribly wrong.");
          }
             queuePasses(passes);
             waitForWorkers(passes);
     }
 }
