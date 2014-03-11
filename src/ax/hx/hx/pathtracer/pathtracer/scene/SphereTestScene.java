package ax.hx.hx.pathtracer.pathtracer.scene;

import ax.hx.hx.pathtracer.image.ImageOutput;
import ax.hx.hx.pathtracer.image.PPMOutput;
import ax.hx.hx.pathtracer.image.RGBImage;
import ax.hx.hx.pathtracer.pathtracer.AbstractScene;
import ax.hx.hx.pathtracer.pathtracer.AbstractShape;
import ax.hx.hx.pathtracer.pathtracer.camera.Camera;
import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.shape.SphereShape;
import ax.hx.hx.pathtracer.pathtracer.material.DiffuseMaterial;
import ax.hx.hx.pathtracer.pathtracer.material.LightMaterial;

import java.io.File;

/**
 * Created by hx on 3/7/14.
 */
public class SphereTestScene extends AbstractScene
{
    public SphereTestScene(){
	Coordinate3 origin = new Coordinate3(0,0,3.0);
	AbstractShape sphere = new SphereShape(origin, 0.5);
	Color color = new Color(1.0,1.0,1.0);
	Material material = new DiffuseMaterial(color);
	sphere.setMaterial(material);
        getShapes().add(sphere);

	origin = new Coordinate3(5,0,2.5);
	sphere = new SphereShape(origin, 3.3);
	color = new Color(1.0,1.0,1.0);
	material = new LightMaterial(color);
	sphere.setMaterial(material);
	getShapes().add(sphere);
    }

    public static void main(String[] args){
            RGBImage image = new RGBImage(1024,1024);
            ImageOutput output = new PPMOutput(image, new File("/home/hx/tmp/FirstCameraTest.ppm"));
            image.setOutputModule(output);
            Scene scene = new SphereTestScene();
            Camera camera = new Camera(scene, 1.0, image);
    	while (true){
            camera.doPasses(100);
            camera.render();
            image.output();
                System.out.println("Wrote to file");
    	}
        }
}
