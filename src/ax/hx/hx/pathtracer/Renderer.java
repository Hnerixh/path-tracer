package ax.hx.hx.pathtracer;

import ax.hx.hx.pathtracer.image.RGBImage;
import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.camera.Camera;

/**
 * This is the class that handles the render-write cycle.
 */
public class Renderer {
    private Camera camera;
    private int writeInterval;
    private int targetSamples;
    private RGBImage image;

    public Renderer(Camera camera, RGBImage image, int writeInterval, int targetSamples) {
        this.camera = camera;
        this.writeInterval = writeInterval;
        this.targetSamples = targetSamples;
        this.image = image;
    }

    public void render(){
        long renderStart = System.currentTimeMillis();
        // TODO Fixa det här för target = 0
        while (targetSamples > 0) {
            camera.doPasses(writeInterval);
            camera.render();
            image.output();
            System.out.println("Wrote to file");
            System.out.println(currentRenderTime(renderStart));
            targetSamples -= writeInterval;
        }
        System.out.println("Done!");
    }
    private String currentRenderTime(long renderStart){
        long currentTime = System.currentTimeMillis();
        long runningTime = currentTime - renderStart;
        runningTime /= 1000;
        return "Current time: " + runningTime + "s";
    }
}
