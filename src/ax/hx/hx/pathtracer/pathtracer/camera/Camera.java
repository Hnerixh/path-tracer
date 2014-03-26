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
     int renderDepth = 3;
     Random rnd;
     int totalTraces = 0;
     private static final boolean RUSSIAN = true;

     CameraWorkerInfo killswitch = new CameraWorkerInfo();
     int workers = 7;

     private BlockingQueue<CameraJob> jobQueue;
     private BlockingQueue<TraceResult> resultQueue;

     public Camera(Scene scene, double focalLength, RGBImage image, int renderDepth){
         this.scene = scene;
         this.width = image.getWidth();
         this.heigth = image.getHeight();
         this.size = width * heigth;
         this.focalLength = focalLength;
         this.image = image;
         this.rnd = new Random();
         this.renderDepth = renderDepth;

         jobQueue = new ArrayBlockingQueue<CameraJob>(size);
         resultQueue = new ArrayBlockingQueue<TraceResult>(size);

         createWorkers();

         influenses = new Influence[size];
         for (int i = 0; i < size; i++) {
             influenses[i] = new Influence();
         }
     }

     private void createWorkers(){
         CameraWorker worker;
         Thread workerThread;
         for (int i = 0; i < workers; i++) {
             worker = new CameraWorker(jobQueue, resultQueue, renderDepth, scene, killswitch, width, heigth, focalLength, true);
             workerThread = new Thread(worker);
             workerThread.start();
         }
     }

     private void queuePasses(int passes){
         for (int i = 0; i < passes; i++) {
             try {
                 jobQueue.put(new CameraJob());
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }

     public void render(){
         for (int i = 0; i < size; i++) {
             image.appendPixel(influenses[i].getPixel());
         }
     }


     private void takeResults(int passes){
         TraceResult res;
         try {
             for (int i = 0; i < size*passes; i++) {
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
