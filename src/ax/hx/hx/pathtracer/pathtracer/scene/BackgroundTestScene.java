package ax.hx.hx.pathtracer.pathtracer.scene;

import ax.hx.hx.pathtracer.image.ImageOutput;
import ax.hx.hx.pathtracer.image.PPMOutput;
import ax.hx.hx.pathtracer.image.RGBImage;
import ax.hx.hx.pathtracer.pathtracer.*;
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
        Coordinate3 origin;
        Shape shape;
        Color color;
        Material material;


        origin = new Coordinate3(5,-2,2);
        shape = new SphereShape(origin, 3.3);
        color = new Color(1,1,1);
        material = new LightMaterial(color);
        shape.setMaterial(material);
        getShapes().add(shape);

        // Add monkey
        shape = new MeshShape(new File("/home/hx/bunny.raw"));
        color = new Color(0.9,0.9,0.9);
        material = new DiffuseMirrorBlend(color, 0.5);
        shape.setMaterial(material);
        getShapes().add(shape);
    }

    public static void main(String[] args){
        RGBImage image = new RGBImage(512,512);
        ImageOutput output = new PPMOutput(image, new File("/home/hx/tmp/FirstCameraTest.ppm"));
        image.setOutputModule(output);
        Scene scene = new BackgroundTestScene();
        Camera camera = new Camera(scene, 1.0, image, 10, 8);
        while (true){
            camera.doPasses(1);
            camera.render();
            image.output();
            System.out.println("Wrote to file");
        }
    }
}
