package ax.hx.hx.pathtracer.pathtracer.camera;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import ax.hx.hx.pathtracer.image.RGBImage;
import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.color.Influence;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;
import ax.hx.hx.pathtracer.pathtracer.math.Vector3;


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
     int renderDepth = 3;
     Random rnd;
     int totalTraces = 0;
     private static final boolean RUSSIAN = true;

     Killswitch killswitch = new Killswitch();
     int workers = 7;

     private BlockingQueue<TraceJob> jobQueue = new LinkedBlockingQueue<TraceJob>();
     private BlockingQueue<TraceResult> resultQueue = new LinkedBlockingQueue<TraceResult>();

     public Camera(Scene scene, double focalLength, RGBImage image, int renderDepth){
         this.scene = scene;
         this.width = image.getWidth();
         this.heigth = image.getHeight();
         this.size = width * heigth;
         this.focalLength = focalLength;
         this.image = image;
         this.rnd = new Random();

         createWorkers();

         influenses = new Influence[size];
         for (int i = 0; i < size; i++) {
             influenses[i] = new Influence();
         }
     }

     private void createWorkers(){
         TraceEater worker;
         Thread workerThread;
         for (int i = 0; i < workers; i++) {
             worker = new TraceEater(jobQueue, resultQueue, renderDepth, scene, killswitch);
             workerThread = new Thread(worker);
             workerThread.start();
         }
     }

     private void queuePass(){
         TraceJob job;
         for (int i = 0; i < size; i++) {
             job = new TraceJob(i, rayForPixel(i));
             try{
             jobQueue.put(job);
         } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }

     private void queuePasses(int passes){
         for (int i = 0; i < passes; i++) {
             queuePass();
             System.out.println("Queueing a pass");
         }
     }

     public void render(){
         for (int i = 0; i < size; i++) {
             image.appendPixel(influenses[i].getPixel());
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
         x += pixelSize*rnd.nextDouble();
         y += pixelSize*rnd.nextDouble();

        // Generate ray
         Vector3 vector = new Vector3(x, y, focalLength);
         Coordinate3 coord = new Coordinate3(0,0,0);
         return new Ray(coord, vector);
     }

     private void takeResults(int passes){
         int numberOfResults = passes*size;
         TraceResult res;
         try {
             for (int i = 0; i < numberOfResults; i++) {
                 res = resultQueue.take();
                 influenses[res.pixel].addInfluence(res.influence);
                 totalTraces++;
                 if (totalTraces % size == 0) {
                     System.out.println(totalTraces / size);
                 }
             }
         }
         catch (InterruptedException e) { System.out.println("Sorry, the camera got interrupted");}
     }

     public void doPasses(int x){
         queuePasses(x);
         takeResults(x);
     }
 }
