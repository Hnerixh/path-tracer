package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer;

import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.camera.Camera;

/**
 * This is the class that handles the render-write cycle.
 */
class Renderer {
    private Camera camera;
    private final int writeInterval;
    private int targetSamples;

    Renderer(Camera camera, int writeInterval, int targetSamples) {
        this.camera = camera;
        this.writeInterval = writeInterval;
        this.targetSamples = targetSamples;
    }

    public void render(){
        long renderStart = System.currentTimeMillis();
        // TODO Fixa det här för target = 0
	boolean forever = (targetSamples == 0);
        while (targetSamples > 0 || forever) {
            camera.doPasses(writeInterval);
            System.out.println("Wrote to file");
            System.out.println(currentRenderTime(renderStart));
            targetSamples -= writeInterval;
        }
	// Ask the camera to kill threads, then remove reference to it.
	camera.kill();
	camera = null;
        System.out.println("Done!");
    }
    private String currentRenderTime(long renderStart){
        long currentTime = System.currentTimeMillis();
        long runningTime = currentTime - renderStart;
        runningTime /= 1000;
        return "Current time: " + runningTime + "s";
    }
}
