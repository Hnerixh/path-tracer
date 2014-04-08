package ax.hx.hx.pathtracer.pathtracer.camera;

import ax.hx.hx.pathtracer.image.ImageOutput;
import ax.hx.hx.pathtracer.image.PPMOutput;
import ax.hx.hx.pathtracer.image.RGBImage;
import ax.hx.hx.pathtracer.pathtracer.AbstractScene;
import ax.hx.hx.pathtracer.pathtracer.AbstractShape;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.material.*;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.shape.PlaneShape;
import ax.hx.hx.pathtracer.pathtracer.shape.SphereShape;

import java.io.File;

/**
 * Simple bench marking utility for the multithreading.
 * No arguments, just modify the code and recompile.
 */
class ThreadingBenchmarker extends AbstractScene {
    private ThreadingBenchmarker(){
        // Create a Cornell box of size 3x3x3

        Color white = new Color(.9,.9,.9);
        Color leftColor = new Color(.8,.75,.29);
        Color rightColor = new Color(0.53,0.26,0.7);
        Color checker1 = new Color(.9,.9,.9);
        Color checker2 = new Color(.7,.7,.7);
        double checkerSize = 0.25;

        // Add a light center
        Coordinate3 origin = new Coordinate3(0,-1.5,4.5);
        AbstractShape shape = new SphereShape(origin, 0.5);
        Color color = new Color(4,4,4);
        Material material = new LightMaterial(color);
        shape.setMaterial(material);
        getShapes().add(shape);

        // Floor
        origin = new Coordinate3(0,-1.5,0);
        Normal normal = new Normal(0,1,0);
        shape = new PlaneShape(normal, origin);
        material = new CheckeredDiffuseMaterial(checker1, checker2, checkerSize);
        shape.setMaterial(material);
        getShapes().add(shape);

        // Roof
        origin = new Coordinate3(0,1.5,0);
        normal = new Normal(0,1,0);
        shape = new PlaneShape(normal, origin);
        material = new DiffuseMaterial(white);
        shape.setMaterial(material);
        getShapes().add(shape);

        // Left
        origin = new Coordinate3(1.5,0,0);
        normal = new Normal(1,0,0);
        shape = new PlaneShape(normal, origin);
        material = new DiffuseMaterial(leftColor);
        shape.setMaterial(material);
        getShapes().add(shape);


        // Right
        origin = new Coordinate3(-1.5,0,0);
        normal = new Normal(1,0,0);
        shape = new PlaneShape(normal, origin);
        material = new DiffuseMaterial(rightColor);
        shape.setMaterial(material);
        getShapes().add(shape);

        // Back
        origin = new Coordinate3(0,0,-6);
        normal = new Normal(0,0,1);
        color = new Color(.2,.2,.8);
        shape = new PlaneShape(normal, origin);
        material = new CheckeredDiffuseMaterial(checker1, checker2, checkerSize);
        shape.setMaterial(material);
        getShapes().add(shape);


        // Black lamp behind camera to increase performance
        origin = new Coordinate3(0,0,0.1);
        normal = new Normal(0,0,1);
        shape = new PlaneShape(normal, origin);
        material = new DiffuseMaterial(white);
        shape.setMaterial(material);
        getShapes().add(shape);

        // Add a sphere
        origin = new Coordinate3(-.5, 1, 4.0);
        shape = new SphereShape(origin, 0.5);
        material = new RefractiveMaterial(white,1.3);
        shape.setMaterial(material);
        getShapes().add(shape);

        // Add a sphere
        origin = new Coordinate3(.4, 1, 5.4);
        shape = new SphereShape(origin, 0.5);
        color = new Color(.7,.7,1.0);
        material = new MirrorMaterial(new Color(0.41, 0.73, 0.51));
        shape.setMaterial(material);
        getShapes().add(shape);
    }

    public static void main(String[] args) {
        RGBImage image = new RGBImage(1024, 1024);
        ImageOutput output = new PPMOutput(image,
                                 new File("/home/hx/tmp/FirstCameraTest.ppm"));
        image.setOutputModule(output);
        Scene scene = new ThreadingBenchmarker();

        // Will run the test scene once for every i in the list, with
        // i as the number of workers.
        int targets[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8};

        for (int i : targets) {
            Camera camera = new Camera(scene, 1.0, image, 16, 1.0, i);
            long startTime = System.currentTimeMillis();
            camera.doPasses(5);
            camera.render();
            image.output();
            long endTime = System.currentTimeMillis();
            double time = (double) (endTime - startTime);
            double renderTime = time / (1000);
            camera.killWorkers();
            System.out.println("Rendering with " + i
                               + " worker(s) took " + renderTime + " seconds.");
        }
    }
}
