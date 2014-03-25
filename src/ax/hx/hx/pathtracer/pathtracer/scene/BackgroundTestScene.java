package ax.hx.hx.pathtracer.pathtracer.scene;

import ax.hx.hx.pathtracer.image.ImageOutput;
import ax.hx.hx.pathtracer.image.PPMOutput;
import ax.hx.hx.pathtracer.image.RGBImage;
import ax.hx.hx.pathtracer.pathtracer.AbstractScene;
import ax.hx.hx.pathtracer.pathtracer.AbstractShape;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.camera.Background;
import ax.hx.hx.pathtracer.pathtracer.camera.Camera;
import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.material.*;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.shape.MeshShape;
import ax.hx.hx.pathtracer.pathtracer.shape.SphereShape;

import java.io.File;

/**
 * Created by hx on 3/25/14.
 */
public class BackgroundTestScene extends AbstractScene {
    public BackgroundTestScene(){

        Background background = new Background(new File("/home/hx/tmp/background.jpg"));
        this.setBackground(background);

        Coordinate3 origin = new Coordinate3(0,0,3.0);
        AbstractShape sphere = new SphereShape(origin, 0.5);
        Color color = new Color(1.0,1.0,1.0);
        Material material = new DiffuseMirrorBlend(color,0.5);
        sphere.setMaterial(material);
        getShapes().add(sphere);

        origin = new Coordinate3(5,-2,2);
        sphere = new SphereShape(origin, 3.3);
        color = new Color(1.0,1.0,1.0);
        material = new LightMaterial(color);
        sphere.setMaterial(material);
        getShapes().add(sphere);

        // Add monkey
        //AbstractShape mesh = new MeshShape(new File("/home/hx/suzanne.raw"));
        //Color color = new Color(0.9,0.9,0.9);
        //Material material = new DiffuseMirrorBlend(color, 0.5);
        //mesh.setMaterial(material);
        //getShapes().add(mesh);
    }

    public static void main(String[] args){
        RGBImage image = new RGBImage(1024,1024);
        ImageOutput output = new PPMOutput(image, new File("/home/hx/tmp/FirstCameraTest.ppm"));
        image.setOutputModule(output);
        Scene scene = new BackgroundTestScene();
        Camera camera = new Camera(scene, 1.0, image, 10);
        while (true){
            camera.doPasses(10);
            camera.render();
            image.output();
            System.out.println("Wrote to file");
        }
    }
}
