package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer;

import java.io.File;

/**
 * This is the simple main file.
 */

// IDEA ANTIWARNING
// The Main class has a main method. I don't see the problem.
public final class Main {
    private Main() {}

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
