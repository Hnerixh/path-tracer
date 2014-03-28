package ax.hx.hx.pathtracer.pathtracer.camera;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import ax.hx.hx.pathtracer.image.RGBImage;
import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.color.Influence;


/**
  * Created by hx on 3/7/14.
  */
 public class Camera
 {
     Scene scene;
     int width;
     int heigth;
     int size;
     double focalLength;
     Influence[] influenses;
     RGBImage image;
     int renderDepth;
     Random rnd;
     long totalTraces = 0; // Fun overflow after 1.5 hours if it happens to be an int.
     private boolean hasWorkers = false;

     CameraWorkerInfo killswitch = new CameraWorkerInfo();
     int workers = 2;

     private BlockingQueue<CameraJob> jobQueue;
     private BlockingQueue<TraceResult> resultQueue;

     public Camera(Scene scene, double focalLength, RGBImage image, int renderDepth){
         init(scene, focalLength, image, renderDepth, workers);
     }

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
         this.rnd = new Random();
         this.renderDepth = renderDepth;
         this.workers = workers;

         jobQueue = new ArrayBlockingQueue<CameraJob>(size);
         resultQueue = new ArrayBlockingQueue<TraceResult>(size);

         influenses = new Influence[size];
         for (int i = 0; i < size; i++) {
             influenses[i] = new Influence();
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
         TraceResult res;
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
