package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.image.ImageOutput;
import ax.hx.hx.pathtracer.image.PPMOutput;
import ax.hx.hx.pathtracer.image.RGBImage;

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
        shapes.add(shape);

	// Floor
	origin = new Coordinate3(0,-1.5,0);
	Normal normal = new Normal(0,1,0);
	shape = new PlaneShape(normal, origin);
	color = new Color(1,1,1);
	material = new DiffuseMaterial(color);
	shape.setMaterial(material);
	shapes.add(shape);

	// Roof
	origin = new Coordinate3(0,1.5,0);
	normal = new Normal(0,1,0);
	shape = new PlaneShape(normal, origin);
	color = new Color(1,1,1);
	material = new DiffuseMaterial(color);
	shape.setMaterial(material);
	shapes.add(shape);

	// Left
	origin = new Coordinate3(1.5,0,0);
	normal = new Normal(1,0,0);
	shape = new PlaneShape(normal, origin);
	color = new Color(1.0,0.2,0.2);
	material = new DiffuseMaterial(color);
	shape.setMaterial(material);
	shapes.add(shape);


	    // Right
	    origin = new Coordinate3(-1.5,0,0);
	    normal = new Normal(1,0,0);
	    shape = new PlaneShape(normal, origin);
	    color = new Color(0.2,1.0,0.2);
	    material = new DiffuseMaterial(color);
	    shape.setMaterial(material);
	    shapes.add(shape);

	    // Back
	    origin = new Coordinate3(0,0,-6);
	    normal = new Normal(0,0,1);
	    shape = new PlaneShape(normal, origin);
	    color = new Color(1,1,1);
	    material = new DiffuseMaterial(color);
	    shape.setMaterial(material);
	    shapes.add(shape);


	    // Black lamp behind camera to increase performance
	    origin = new Coordinate3(0,0,0.1);
	    normal = new Normal(0,0,1);
	    shape = new PlaneShape(normal, origin);
	    color = new Color(1,1,1);
	    material = new DiffuseMaterial(color);
	    shape.setMaterial(material);
	    shapes.add(shape);

	// Add a sphere
	origin = new Coordinate3(-.9, 1, 4.4);
	shape = new SphereShape(origin, 0.5);
	color = new Color(1,1,1);{}
	material = new DiffuseMaterial(color);
	shape.setMaterial(material);
        shapes.add(shape);


	}

    public static void main(String[] args){
            RGBImage image = new RGBImage(1024,1024);
            ImageOutput output = new PPMOutput(image, new File("/home/hx/tmp/FirstCameraTest.ppm"));
            image.setOutputModule(output);
            Scene scene = new CornellBoxTestScene();
            Camera camera = new Camera(scene, 1.0, image);
    	 while (true){
            camera.doPasses(10);
            camera.render();
            image.output();
                System.out.println("Wrote to file");
    	 }
        }
}
