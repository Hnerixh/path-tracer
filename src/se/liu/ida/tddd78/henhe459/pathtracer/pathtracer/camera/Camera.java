package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.camera;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.Scene;
import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;


/**
  * Created by hx on 3/7/14.
  */
 public class Camera
 {
     private final Scene scene;
     private final int width;
     private final int heigth;
     private final int size;
     private final double focalLength;
     private final Radiance[] radiances;
     private final int renderDepth;
     private long totalTraces = 0; // Fun overflow after 1.5 hours if it happens to be an int.
     private long totalSuccesfulTraces = 0;
     private boolean hasWorkers = false;
     private final double rrRatio;
     private List<CameraObserver> observers = new ArrayList<CameraObserver>();

     private final CameraWorkerInfo killswitch = new CameraWorkerInfo();
     private int workers = 2;

     private final BlockingQueue<CameraJob> jobQueue;
     private final BlockingQueue<TraceResult> resultQueue;

     // ANTIWARNING Russian Roulette Ratio
     public Camera(Scene scene, int width, int heigth, double focalLength, int renderDepth, double rrRatio, int workers){
         this.scene = scene;
         this.width = width;
         this.heigth = heigth;
         this.size = width * heigth;
         this.focalLength = focalLength;
         this.renderDepth = renderDepth;
         this.workers = workers;
         this.rrRatio = rrRatio;

         jobQueue = new ArrayBlockingQueue<CameraJob>(size);
         resultQueue = new ArrayBlockingQueue<TraceResult>(size);

         radiances = new Radiance[size];
         for (int i = 0; i < size; i++) {
             radiances[i] = new Radiance();
         }

         createWorkers();
     }

// --Commented out by Inspection START (4/15/14 1:19 AM):
//     public void killWorkers(){
//         killswitch.kill();
//     }
// --Commented out by Inspection STOP (4/15/14 1:19 AM)

     private void createWorkers(){
	 for (int i = 0; i < workers; i++) {
	     CameraWorker worker =
		     new CameraWorker(jobQueue, resultQueue, renderDepth, rrRatio, scene, killswitch, width, heigth,
				      focalLength, radiances);
	     Thread workerThread = new Thread(worker);
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

     public void addObserver(CameraObserver observer){
	 observers.add(observer);
     }

     public void notifyObservers(){
	 for (CameraObserver observer : observers){
	     observer.passDone(radiances);
	 }
     }


     private void waitForWorkers(int passes){
         try {
             for (int i = 0; i < workers*passes; i++) {
                TraceResult res = resultQueue.take();
                totalSuccesfulTraces += res.succesfulTraces;
                totalTraces += res.traces;
		 // IDEA ANTIWARNING
		 // Someone told me IDEA did not warn for even powers of 10.
		 // They were wrong.
                double hitpercent = (100.0 * totalSuccesfulTraces / totalTraces);
                double spp = (totalTraces / (double) size); // Traced samples per pixel
                double realSpp = spp * hitpercent / 100.0; // Actual emitter hits per pixel
                System.out.println("Traces per pixel: " + spp);
                System.out.println("Samples per pixel: " + realSpp);
                System.out.println("Hit probability: " + hitpercent + "%");
                System.out.println("-------------");
             }
         }
         catch (InterruptedException e) { e.printStackTrace();}
     }

     public void doPasses(int passes){
          if(!hasWorkers){
              System.out.println("Killing workers should only be used for benchmarking purposes. You just did something terribly wrong.");
          }
             queuePasses(passes);
             waitForWorkers(passes);
		notifyObservers();
     }

     public void kill(){
	 killswitch.kill();
     }
 }
