package ax.hx.hx.pathtracer;

import java.io.File;

/**
 * This is the simple main file.
 */
class Main {
    public static void main(String[] args){
        if (args.length < 2){
            System.out.println("Please supply at least two arguments.");
            return;
        }

        Renderer renderer = SceneParser.parseScene(new File(args[0]), new File(args[1]));

        if (renderer == null) {
            System.out.println("Could not parse scene");
            return;
        }

        renderer.render();
    }
}
