package ax.hx.hx.pathtracer;

import java.io.File;

/**
 * This is the simple main file.
 */
public class Main {
    public static void main(String[] args){
        if (args.length < 2){
            System.out.println("Please supply at least two arguments.");
        }
        Renderer renderer = SceneParser.parseScene(new File(args[0]), new File(args[1]));
        renderer.render();
    }
}
