package ax.hx.hx.pathtracer;

import ax.hx.hx.pathtracer.output.LinearTonemap;
import ax.hx.hx.pathtracer.output.Output;
import ax.hx.hx.pathtracer.output.Tonemapper;
import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.Scene;
import ax.hx.hx.pathtracer.pathtracer.Shape;
import ax.hx.hx.pathtracer.pathtracer.camera.Background;
import ax.hx.hx.pathtracer.pathtracer.camera.Camera;
import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.material.*;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;
import ax.hx.hx.pathtracer.pathtracer.shape.MeshShape;
import ax.hx.hx.pathtracer.pathtracer.shape.PlaneShape;
import ax.hx.hx.pathtracer.pathtracer.shape.SphereShape;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a class with one public function, parseScene().
 * This function takes a file, parses it, and returns a Renderer.
 *
 * Please reefer to doc/sc_formatspecification.sc for details.
 */
final class SceneParser {
    // IDEA ANTIWARNING
    // bufferedReader will be initialized before first use.
    private static BufferedReader bufferedReader;

    private SceneParser() {}

    public static Renderer parseScene(File path, final File outputPath) {
        /*
        IDEA ANTIWARNING
        Apparently this is to complex to analyze.
        IDEA ANTIWARNING II
        cyclomatic complexity = 23
        Really, this is a simple function, it is just rather long and tedious.
        IDEA ANTIWARNING III
        This is really overly coupled, true, however, this is used to create the scene for the renderer,
        and therefore uses a lot of different classes.
        IDEA ANTIWARNING IIII
        This is also too long according to IDEA.
        I think it's long, boring both to write and read, but a necessary evil.
        The functionality is still really simple, there are just lots of code.

        I won't split the code here, due to the fact that the actual code is very straightforward,
        and while splitting the code up might make the code inspections happy,
        the code would not get less complex, only longer.
        */

	// ------------ Intermediate parse results with defaults -----------------

	String[] lineParts;
        List<Shape> shapes = new ArrayList<Shape>();
        List<Material> materials = new ArrayList<Material>();
        String line;

        int xSize = 1;
        int ySize = 1;
	/*
	IDEA ANTIWARNING
	long and descriptive.
	In the rest of the code this is refeered to as rrRatio.
	Want to clarify that it is the propability of death.
	*/
	double russianRouletteDeathProbability = 1.0;
        int depth = 3;
        int targetSpp = 1;
        int writeInterval = 1;
        Background background = new Background(0,0,0);
        double focalLength = 1.0;
        Tonemapper tonemapper = new LinearTonemap();
        boolean highBitDepth = false;

	// ------------ Setup -------------------
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("404: File not found.");
	    e.printStackTrace();
            return null;
        }
        // Is this a scene?
        line = readLine();
        if (line == null) {
            System.out.println("Empty file?");
            return null;

        }
        if (!line.equals("scene")) {
            System.out.println("Not a scene description file, magic constant: " + line + " is not 'scene'.");
            return null;
        }



	// ----------------- Parse ----------------------
        /*
	Read scene info
	IDEA ANTIWARNING
	I find this easy to read.
	IDEA ANTIWARNING II
	Continue? Yes. A lot prettier than if-else in my eyes for this scenario.
	*/
	while ((line = readLine()) != null
               && (!line.equals("materials"))) {

            if (line.startsWith("size")) {
                lineParts = line.split(" ");
                xSize = Integer.parseInt(lineParts[1]);
                ySize = Integer.parseInt(lineParts[2]);
                continue;
            }

            if (line.startsWith("depth")) {
                lineParts = line.split(" ");
                depth = Integer.parseInt(lineParts[1]);
                continue;
            }

            if (line.startsWith("target_spp")) {
                lineParts = line.split(" ");
                targetSpp = Integer.parseInt(lineParts[1]);
                continue;
            }

            if (line.startsWith("interval")) {
                lineParts = line.split(" ");
                writeInterval = Integer.parseInt(lineParts[1]);
                continue;
            }

            if (line.startsWith("focal_length")) {
                lineParts = line.split(" ");
                focalLength = Double.parseDouble(lineParts[1]);
                continue;
            }

            if (line.startsWith("bgpic")) {
                lineParts = line.split(" ");
                background = new Background(new File(lineParts[1]));
                continue;
            }

            if (line.startsWith("bgrgb")) {
                lineParts = line.split(" ");
                double r = Double.parseDouble(lineParts[1]);
                double g = Double.parseDouble(lineParts[2]);
                double b = Double.parseDouble(lineParts[3]);     // IDEA ANTIWARNING b = blue
                background = new Background(r, g, b);
                continue;
            }

            if (line.startsWith("rrprob")) {
                lineParts = line.split(" ");
                russianRouletteDeathProbability = Double.parseDouble(lineParts[1]);
                continue;
            }

            if (line.startsWith("16bit")){
                highBitDepth = true;
		continue;
            }

            System.out.println("Unknown option: " + line);
        }

        // Read material info
	// IDEA ANTIWARNING
	// I find this easy to read.
        while ((line = readLine()) != null
               && (!line.equals("shapes"))){
            if (line.startsWith("diff")){
                lineParts = line.split(" ");
                double r = Double.parseDouble(lineParts[1]);
                double g = Double.parseDouble(lineParts[2]);
                double b = Double.parseDouble(lineParts[3]);     // IDEA ANTIWARNING b = blue
                materials.add(new DiffuseMaterial(new Color(r,g,b)));
                continue;
            }

            if (line.startsWith("dmblend")) {
                lineParts = line.split(" ");
                double r = Double.parseDouble(lineParts[1]);
                double g = Double.parseDouble(lineParts[2]);
                double b = Double.parseDouble(lineParts[3]);     // IDEA ANTIWARNING b = blue
                double ior = Double.parseDouble(lineParts[4]);
                materials.add(new DiffuseMirrorBlend(new Color(r,g,b),ior));
                continue;
            }

            if (line.startsWith("mirror")){
                lineParts = line.split(" ");
                double r = Double.parseDouble(lineParts[1]);
                double g = Double.parseDouble(lineParts[2]);
                double b = Double.parseDouble(lineParts[3]);     // IDEA ANTIWARNING b = blue
                materials.add(new MirrorMaterial(new Color(r,g,b)));
                continue;
            }

            if (line.startsWith("light")){
                lineParts = line.split(" ");
                double r = Double.parseDouble(lineParts[1]);
                double g = Double.parseDouble(lineParts[2]);
                double b = Double.parseDouble(lineParts[3]);     // IDEA ANTIWARNING b = blue
                materials.add(new LightMaterial(new Color(r,g,b)));
                continue;
            }

            if (line.startsWith("refr")) {
                lineParts = line.split(" ");
                double r = Double.parseDouble(lineParts[1]);
                double g = Double.parseDouble(lineParts[2]);
                double b = Double.parseDouble(lineParts[3]);     // IDEA ANTIWARNING b = blue
                double ior = Double.parseDouble(lineParts[4]);
                materials.add(new RefractiveMaterial(new Color(r,g,b),ior));
                continue;
            }
            System.out.println("Unknown material: " + line);
        }

        // Read shapes
	// IDEA ANTIWARNING
	// I find this easy to read.
        while ((line = readLine()) != null) {
            if (line.startsWith("sphere")){
                lineParts = line.split(" ");
                double x = Double.parseDouble(lineParts[2]);
                double y = Double.parseDouble(lineParts[3]);
                double z = Double.parseDouble(lineParts[4]);
                double r = Double.parseDouble(lineParts[5]);
		int matIndex = Integer.parseInt(lineParts[1]);
		Material mat = materials.get(matIndex);
                Shape sh = new SphereShape(new Coordinate3(x,y,z), r, mat);
                shapes.add(sh);
                continue;
            }

            if (line.startsWith("plane")){
                lineParts = line.split(" ");
                double x = Double.parseDouble(lineParts[2]);
                double y = Double.parseDouble(lineParts[3]);
                double z = Double.parseDouble(lineParts[4]);

                double xn = Double.parseDouble(lineParts[5]);
                double yn = Double.parseDouble(lineParts[6]);
                double zn = Double.parseDouble(lineParts[7]);
		int matIndex = Integer.parseInt(lineParts[1]);
		Material mat = materials.get(matIndex);
                Shape sh = new PlaneShape(new Normal(xn, yn, zn),
                                          new Coordinate3(x,y,z), mat);
                shapes.add(sh);
                continue;
            }

            if (line.startsWith("mesh")) {
                lineParts = line.split(" ");
		int matIndex = Integer.parseInt(lineParts[1]);
  		Material mat = materials.get(matIndex);
                Shape sh = new MeshShape(new File(lineParts[2]),mat);
                shapes.add(sh);
                continue;
            }
            System.out.println("Unknown shape: " + line);
        }

        // Everything parsed, let's create a Renderer
        Scene scene = new Scene();
        scene.setShapes(shapes);
        scene.setBackground(background);

        Output output = new Output(outputPath, xSize, ySize, highBitDepth, tonemapper);

        int cores = Runtime.getRuntime().availableProcessors();
        Camera camera = new Camera(scene, xSize, ySize, focalLength,
                                depth,
                                russianRouletteDeathProbability,
                                cores);
	camera.addObserver(output);

        return new Renderer(camera, writeInterval, targetSpp);
    }

    private static String readLine() {
	try {
            // Read to the next non-empty, non comment line.
	// IDEA ANTIWARNING
	// I find this easy to read.
	    String line;
	    //IDEA ANTIWARNING II
	    // bufferedReader is initialized before this point.
	    while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                if (line.startsWith("#")) {
                    continue;
                }
                return line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
