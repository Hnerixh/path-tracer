package ax.hx.hx.pathtracer.pathtracer;

import ax.hx.hx.pathtracer.image.ImageOutput;
import ax.hx.hx.pathtracer.image.PPMOutput;
import ax.hx.hx.pathtracer.image.RGBImage;
import ax.hx.hx.pathtracer.pathtracer.*;
import java.io.File;

public class FirstCameraTest {

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
