package ax.hx.hx.pathtracer.pathtracer.scene;

import ax.hx.hx.pathtracer.image.ImageOutput;
import ax.hx.hx.pathtracer.image.PPMOutput;
import ax.hx.hx.pathtracer.image.RGBImage;
import ax.hx.hx.pathtracer.pathtracer.AbstractScene;
import ax.hx.hx.pathtracer.pathtracer.AbstractShape;
import ax.hx.hx.pathtracer.pathtracer.camera.Camera;
import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.material.DiffuseMaterial;
import ax.hx.hx.pathtracer.pathtracer.material.LightMaterial;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.shape.PlaneShape;
import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.shape.SphereShape;

import java.io.File;

/**
 * This file is used for testing planes and spheres.
 */
public class PlaneTestScene extends AbstractScene
{
    public PlaneTestScene(){
	// Add a sphere in the middle of the scene.
	Coordinate3 origin = new Coordinate3(0,0,3.0);
	AbstractShape shape = new SphereShape(origin, 0.5);
	Color color = new Color(0.7,0.0,1.0);
	Material material = new DiffuseMaterial(color);
	shape.setMaterial(material);
        getShapes().add(shape);

	// Add a big light to the right
	origin = new Coordinate3(5,-4,2);
	shape = new SphereShape(origin, 3.3);
	color = new Color(1.0,1.0,1.0);
	material = new LightMaterial(color);
	shape.setMaterial(material);
	getShapes().add(shape);

	// Add a small light beside the sphere.
	origin = new Coordinate3(-5,-4,2);
	shape = new SphereShape(origin, 3.3);
	color = new Color(1.0,1.0,1.0);
	material = new LightMaterial(color);
	shape.setMaterial(material);
	getShapes().add(shape);


	// Add a plane below the sphere
	origin = new Coordinate3(0,-.5,0);
	Normal normal = new Normal(0,.5,0);
	shape = new PlaneShape(normal, origin);
	material = new DiffuseMaterial(color);
	shape.setMaterial(material);
	getShapes().add(shape);


//		// Add a triangle
//        Coordinate3 a = new Coordinate3(1, 1, 4);
//        Coordinate3 b = new Coordinate3(1, 2,8);
//        Coordinate3 c = new Coordinate3(0,0,8);
//	shape = new TriangleShape(a,b,c);
//	color = new Color(1,1,1);{}
//	material = new DiffuseMaterial(color);
//	shape.setMaterial(material);
//        shapes.add(shape);
    }

    public static void main(String[] args){
            RGBImage image = new RGBImage(1024,1024);
            ImageOutput output = new PPMOutput(image, new File("/home/hx/tmp/FirstCameraTest.ppm"));
            image.setOutputModule(output);
            Scene scene = new PlaneTestScene();
            Camera camera = new Camera(scene, 1.0, image);
            // while (true){
            camera.doPasses(10);
            camera.render();
            image.output();
                System.out.println("Wrote to file");
                // }
        }
}
