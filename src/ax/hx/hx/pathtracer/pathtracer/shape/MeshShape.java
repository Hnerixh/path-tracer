package ax.hx.hx.pathtracer.pathtracer.shape;

import ax.hx.hx.pathtracer.pathtracer.Material;
import ax.hx.hx.pathtracer.pathtracer.color.IntersectionInfo;
import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
import ax.hx.hx.pathtracer.pathtracer.math.Ray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Shape subclass for arbitrary meshes of triangular polygons.
 *
 * Please note that this class does not provide ANY acceleration
 * structure, and thus, intersection takes O(n) time, where n is the
 * number of polygons. Linear time is too slow for this, so users
 * should limit their use of polygons.
 *
 * The class is initialized with a File, pointing to a .raw file.
 * Only triangles are supported, quads in the .raw will result in
 * undefined behavior.
 */
public class MeshShape extends AbstractShape
{
    private final List<TriangleShape> triangles = new ArrayList<TriangleShape>();
    public MeshShape(File file, Material material){
	this.setMaterial(material);
        // Parse the .raw file
        BufferedReader in = null;
        try{
            in = new BufferedReader(new FileReader(file)); // IDEA ANTIWARNING It is closed in a try block, in the finally block.
            String line;
	    // IDEA ANTIWARNING
	    // I find this readable and simple.
            while ((line = in.readLine()) != null){
                // Each line in the file contains 9 floats, which maps
                // to 3 coordinates, which are used to create a
                // triangle.
                double[] doubleValues = new double[9];
		String[] values = line.split(" ");
		for (int i = 0; i < 9; i++) { // Parse to doubles
                    doubleValues[i] = Double.parseDouble(values[i]);
                }
                Coordinate3[] coords = new Coordinate3[3];
                for (int i = 0; i < 3; i++){ // create coordinates
                    coords[i] = new Coordinate3(doubleValues[i*3],
                                                doubleValues[i*3+1],
                                                doubleValues[i*3+2]);
                }
                // Create triangle and add it.
                triangles.add(new TriangleShape(coords[0],
                                                coords[1],
                                                coords[2],
						material));
            }
        }
        catch (FileNotFoundException e){
		e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                assert in != null;
                in.close();
            }
            catch (IOException ex){
		ex.printStackTrace();
            }
        }
        // System.out.println("done");

    }

    public IntersectionInfo intersection(Ray ray){
        IntersectionInfo intersection = null;
        double nearestHit = 0;
	boolean hasHit = false;

        for (TriangleShape triangle : triangles){
            IntersectionInfo hit = triangle.intersection(ray);

            if (hit == null){
		// IDEA ANTIWARNING
		// "continue" with the next triangle.
                continue;
            }

            if (ray.getOrigin().distance(hit.hitCoord) < nearestHit
                || ! hasHit){
		hasHit = true;
                nearestHit = ray.getOrigin().distance(hit.hitCoord);
                intersection = hit;
            }
        }

        if (intersection == null) {
            return null;
        }

        return intersection;
    }
}
