package ax.hx.hx.pathtracer.pathtracer.scene;

import ax.hx.hx.pathtracer.image.ImageOutput;
import ax.hx.hx.pathtracer.image.PPMOutput;
import ax.hx.hx.pathtracer.image.RGBImage;
import ax.hx.hx.pathtracer.pathtracer.AbstractScene;
import ax.hx.hx.pathtracer.pathtracer.AbstractShape;
import ax.hx.hx.pathtracer.pathtracer.camera.Camera;
import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.material.MirrorMaterial;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.material.DiffuseMaterial;
import ax.hx.hx.pathtracer.pathtracer.material.LightMaterial;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.shape.PlaneShape;
import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.shape.SphereShape;
import ax.hx.hx.pathtracer.pathtracer.shape.TriangleShape;

import java.io.File;

/**
 * Created by hx on 3/10/14.
 */
public class CornellBoxTestScene extends AbstractScene
{
        public CornellBoxTestScene(){
	// Create a Cornell box of size 3x3x3

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
	color = new Color(1,1,1);
	material = new DiffuseMaterial(color);
	shape.setMaterial(material);
	getShapes().add(shape);

	// Roof
	origin = new Coordinate3(0,1.5,0);
	normal = new Normal(0,1,0);
	shape = new PlaneShape(normal, origin);
	color = new Color(1,1,1);
	material = new DiffuseMaterial(color);
	shape.setMaterial(material);
	getShapes().add(shape);

	// Left
	origin = new Coordinate3(1.5,0,0);
	normal = new Normal(1,0,0);
	shape = new PlaneShape(normal, origin);
	color = new Color(1.0,0.2,0.2);
	material = new DiffuseMaterial(color);
	shape.setMaterial(material);
	getShapes().add(shape);


	    // Right
	    origin = new Coordinate3(-1.5,0,0);
	    normal = new Normal(1,0,0);
	    shape = new PlaneShape(normal, origin);
	    color = new Color(0.2,1.0,0.2);
	    material = new DiffuseMaterial(color);
	    shape.setMaterial(material);
	    getShapes().add(shape);

	    // Back
	    origin = new Coordinate3(0,0,-6);
	    normal = new Normal(0,0,1);
	    shape = new PlaneShape(normal, origin);
	    color = new Color(1,1,1);
	    material = new DiffuseMaterial(color);
	    shape.setMaterial(material);
	    getShapes().add(shape);


	    // Black lamp behind camera to increase performance
	    origin = new Coordinate3(0,0,0.1);
	    normal = new Normal(0,0,1);
	    shape = new PlaneShape(normal, origin);
	    color = new Color(1,1,1);
	    material = new DiffuseMaterial(color);
	    shape.setMaterial(material);
	    getShapes().add(shape);

	// Add a sphere
	origin = new Coordinate3(-.9, 1, 4.4);
	shape = new SphereShape(origin, 0.5);
	color = new Color(1,1,1);{}
	material = new DiffuseMaterial(color);
	shape.setMaterial(material);
        getShapes().add(shape);

	// Add a sphere
	origin = new Coordinate3(.9, 1, 4.4);
	shape = new SphereShape(origin, 0.5);
	color = new Color(1,1,1);{}
	material = new MirrorMaterial(color);
	shape.setMaterial(material);
        getShapes().add(shape);

//	// Add a triangle
//        Coordinate3 a = new Coordinate3(-.9, -0.5, 4.4);
//        Coordinate3 b = new Coordinate3(0,-.5,3);
//        Coordinate3 c = new Coordinate3(.9,-.5,4.4);
//	shape = new TriangleShape(a,b,c);
//	color = new Color(1,1,1);
//	material = new DiffuseMaterial(color);
//	shape.setMaterial(material);
//        getShapes().add(shape);
	}

    public static void main(String[] args){
            RGBImage image = new RGBImage(1024,1024);
            ImageOutput output = new PPMOutput(image, new File("/home/hx/tmp/FirstCameraTest.ppm"));
            image.setOutputModule(output);
            Scene scene = new CornellBoxTestScene();
            Camera camera = new Camera(scene, 1.0, image, 20);
    	 while (true){
            camera.doPasses(5);
            camera.render();
            image.output();
                System.out.println("Wrote to file");
    	 }
        }
}
