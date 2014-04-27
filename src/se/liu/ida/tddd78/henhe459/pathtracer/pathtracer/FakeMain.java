package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer;

import java.io.File;

/**
 * "fake" main file, used for quick and dirty testing. Just ignore it!
 */
final class FakeMain {
    private FakeMain() {}

    public static void main(String[] args){
        System.out.println("Creating renderer");
	// IDEA antiwarning
	// Feel free to change the filepaths.
        Renderer renderer = SceneParser.parseScene(new File("/home/hx/code/PathTracer/PathTracer/scenes/cornell_box.sc"),
						   new File("/home/hx/code/PathTracer/PathTracer/renders/test.ppm"));


        if (renderer == null) {
            System.out.println("Could not parse scene");
            return;
        }
        System.out.println("Starting rendering");
        renderer.render();
    }
}
