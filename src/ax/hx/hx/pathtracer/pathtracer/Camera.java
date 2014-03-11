package ax.hx.hx.pathtracer.pathtracer;

import java.util.Random;

import ax.hx.hx.pathtracer.image.RGBImage;


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
     int MAX_DEPTH = 3;
     Random rnd;
     int samplesPerPixel = 0;

     Camera(Scene scene, double focalLength, RGBImage image){
         this.scene = scene;
         this.width = image.getWidth();
         this.heigth = image.getHeight();
         this.size = width * heigth;
         this.focalLength = focalLength;
         this.image = image;
         this.rnd = new Random();

         influenses = new Influence[size];
         for (int i = 0; i < size; i++) {
             influenses[i] = new Influence(0,0,0);
         }
     }

     public void doPass(){
         for (int i = 0; i < size; i++) {
             influenses[i].addInfluence(scene.pathtrace(rayForPixel(i), MAX_DEPTH));
         }
	 samplesPerPixel++;
	 System.out.println(samplesPerPixel);
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
         x += pixelSize*rnd.nextDouble()*2;
         y += pixelSize*rnd.nextDouble()*2;

         // Generate ray
         Vector3 vector = new Vector3(x, y, focalLength);
         Coordinate3 coord = new Coordinate3(0,0,0);
         return new Ray(coord, vector);

     }
     public void doPasses(int x){
         for (int i = 0; i < x; i++) {
             doPass();
         }
     }
 }
