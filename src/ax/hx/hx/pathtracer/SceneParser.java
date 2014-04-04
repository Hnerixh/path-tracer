package ax.hx.hx.pathtracer;

import ax.hx.hx.pathtracer.image.ImageOutput;
import ax.hx.hx.pathtracer.image.PPMOutput;
import ax.hx.hx.pathtracer.image.RGBImage;
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

/**
 * This is a utility class with one public function, parseScene().
 * This function takes a file, parses it, and returns a Renderer.
 *
 * Please reefer to doc/sc_formatspecification.sc for details.
 */
public class SceneParser {
    private static BufferedReader br;

    public static Renderer parseScene(File path, final File outputPath) {
        int writeInterval;
        int targetSamples;
        String[] line_parts;
        ArrayList<Shape> shapes = new ArrayList<Shape>();
        ArrayList<Material> materials = new ArrayList<Material>();
        String line;


        // Intermediate parse results with defaults
        int xSize = 1024;
        int ySize = 1024;

        int depth = 3;
        int target_spp = 1;
        int write_interval = 1;
        Background background = new Background(0,0,0);
        double focal_length = 1.0;

        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.println("404: File not found.");
            return null;
        } catch (IOException e) {
            System.out.println("Shit, IOException");
            return null;
        } finally {
        }

        // Is this a scene?
        line = readLine();
        if (line == null) {
            return null;
        }
        if (!line.equals("scene")) {
            return null;
        }


        // Read scene info
        while ((line = readLine()) != null
               && (!line.equals("materials"))) {
            if (line.startsWith("size")) {
                line_parts = line.split(" ");
                xSize = Integer.parseInt(line_parts[1]);
                ySize = Integer.parseInt(line_parts[2]);
                continue;
            }

            if (line.startsWith("depth")) {
                line_parts = line.split(" ");
                depth = Integer.parseInt(line_parts[1]);
                continue;
            }

            if (line.startsWith("target_spp")) {
                line_parts = line.split(" ");
                target_spp = Integer.parseInt(line_parts[1]);
                continue;
            }

            if (line.startsWith("interval")) {
                line_parts = line.split(" ");
                write_interval = Integer.parseInt(line_parts[1]);
                continue;
            }

            if (line.startsWith("focal_length")) {
                line_parts = line.split(" ");
                focal_length = Double.parseDouble(line_parts[1]);
                continue;
            }

            if (line.startsWith("bgpic")) {
                line_parts = line.split(" ");
                background = new Background(new File(line_parts[1]));
                continue;
            }

            if (line.startsWith("bgrgb")) {
                line_parts = line.split(" ");
                double r = Double.parseDouble(line_parts[1]);
                double g = Double.parseDouble(line_parts[2]);
                double b = Double.parseDouble(line_parts[3]);
                background = new Background(r, g, b);
                continue;
            }
            System.out.println("Unknown option: " + line);
        }

        // Read material info
        while ((line = readLine()) != null
               && (!line.equals("shapes"))){
            if (line.startsWith("diff")){
                line_parts = line.split(" ");
                double r = Double.parseDouble(line_parts[1]);
                double g = Double.parseDouble(line_parts[2]);
                double b = Double.parseDouble(line_parts[3]);
                materials.add(new DiffuseMaterial(new Color(r,g,b)));
                continue;
            }

            if (line.startsWith("dmblend")) {
                line_parts = line.split(" ");
                double r = Double.parseDouble(line_parts[1]);
                double g = Double.parseDouble(line_parts[2]);
                double b = Double.parseDouble(line_parts[3]);
                double ior = Double.parseDouble(line_parts[4]);
                materials.add(new DiffuseMirrorBlend(new Color(r,g,b),ior));
            }

            if (line.startsWith("mirror")){
                line_parts = line.split(" ");
                double r = Double.parseDouble(line_parts[1]);
                double g = Double.parseDouble(line_parts[2]);
                double b = Double.parseDouble(line_parts[3]);
                materials.add(new MirrorMaterial(new Color(r,g,b)));
                continue;
            }

            if (line.startsWith("light")){
                line_parts = line.split(" ");
                double r = Double.parseDouble(line_parts[1]);
                double g = Double.parseDouble(line_parts[2]);
                double b = Double.parseDouble(line_parts[3]);
                materials.add(new LightMaterial(new Color(r,g,b)));
                continue;
            }

            if (line.startsWith("refr")) {
                line_parts = line.split(" ");
                double r = Double.parseDouble(line_parts[1]);
                double g = Double.parseDouble(line_parts[2]);
                double b = Double.parseDouble(line_parts[3]);
                double ior = Double.parseDouble(line_parts[4]);
                materials.add(new RefractiveMaterial(new Color(r,g,b),ior));
                continue;
            }
            System.out.println("Unknown material: " + line);
        }

        // Read shapes
        while ((line = readLine()) != null) {
            if (line.startsWith("sphere")){
                line_parts = line.split(" ");
                double x = Double.parseDouble(line_parts[2]);
                double y = Double.parseDouble(line_parts[3]);
                double z = Double.parseDouble(line_parts[4]);
                double r = Double.parseDouble(line_parts[5]);
                Shape sh = new SphereShape(new Coordinate3(x,y,z), r);
                int matIndex = Integer.parseInt(line_parts[1]);
                Material mat = materials.get(matIndex);
                sh.setMaterial(mat);
                shapes.add(sh);
                continue;
            }

            if (line.startsWith("sphere")){
                line_parts = line.split(" ");
                double x = Double.parseDouble(line_parts[2]);
                double y = Double.parseDouble(line_parts[3]);
                double z = Double.parseDouble(line_parts[4]);

                double xn = Double.parseDouble(line_parts[5]);
                double yn = Double.parseDouble(line_parts[6]);
                double zn = Double.parseDouble(line_parts[7]);

                Shape sh = new PlaneShape(new Normal(xn, yn, zn),
                                          new Coordinate3(x,y,z));
                int matIndex = Integer.parseInt(line_parts[1]);
                Material mat = materials.get(matIndex);
                sh.setMaterial(mat);
                shapes.add(sh);
                continue;
            }

            if (line.startsWith("mesh")) {
                line_parts = line.split(" ");
                Shape sh = new MeshShape(new File(line_parts[2]));
                int matIndex = Integer.parseInt(line_parts[1]);
                Material mat = materials.get(matIndex);
                sh.setMaterial(mat);
                shapes.add(sh);
                continue;
            }
            System.out.println("Unknown shape: " + line);
        }

        // Everything parsed, let's create a Renderer
        SimpleScene scene = new SimpleScene();
        scene.setShapes(shapes);
        scene.setBackground(background);

        RGBImage image = new RGBImage(xSize, ySize);
        ImageOutput imageOutput = new PPMOutput(image, outputPath);
        image.setOutputModule(imageOutput);

        int cores = Runtime.getRuntime().availableProcessors();
        Camera camera = new Camera(scene, focal_length, image, depth, cores);

        Renderer renderer = new Renderer(camera,
                                         image,
                                         write_interval,
                                         target_spp);

        return renderer;
    }

    private static String readLine() {
        String line;
        try {
            // Read to the next non-empty, non comment line.
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                if (line.startsWith("#")) {
                    continue;
                }
                return line;
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            return null;
        }
    }
}
