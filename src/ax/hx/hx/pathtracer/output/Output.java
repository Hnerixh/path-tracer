package ax.hx.hx.pathtracer.output;

import ax.hx.hx.pathtracer.pathtracer.camera.CameraObserver;
import ax.hx.hx.pathtracer.pathtracer.color.Radiance;

import java.io.*;

/**
 * Outputs an image in the PPM P3 format to the specified File.
 *
 * The PPM P3 format is trivial to write to. Begin with the magic word
 * "P3", the image width, height and max value, separated by
 * whitespace. Then append r,g, and b data for each pixel, just
 * separated by whitespace.
 *
 * Specification:
 * http://netpbm.sourceforge.net/doc/ppm.html
 *
 * Usage with bit depths other than 8 bit are a bit unspecified. The
 * file will be written just fine, but it's uncertain if image viewing
 * programs will accept the result. For example 16 bit ax.hx.hx.pathtracer.output is okay
 * with feh, but not with gimp. The first Pixel in the Image sets the
 * bit-depth. Usage with images that contains pixels of different
 * types are just stupid, and therefore unspecified.
 */

public class Output implements CameraObserver
{
    private final int xsize;
    private final int ysize;
    private final File file;
    private final Tonemapper tonemapper;

    // 255 = maximum color value in 8-bit picture
    // 65535 = maximum color value in 16-bit picture
    private static final int TWO_HUNDRED_FIFTY_FIVE = 255;
    private static final int TWO_TO_THE_POWER_OF_16_MINUS_ONE = 65535;
    private int maxVal = TWO_HUNDRED_FIFTY_FIVE;


    public Output(File file, int xsize, int ysize, boolean highBitDepth, Tonemapper tonemapper) {
        this.xsize = xsize;
        this.ysize = ysize;
        this.file = file;
        this.tonemapper = tonemapper;

        if (highBitDepth){
            maxVal = TWO_TO_THE_POWER_OF_16_MINUS_ONE;
        }
    }

    public void passDone(Radiance[] radiances){
        if (file.exists()){
	    if (! file.delete()){
		System.out.println("Error deleting file!");
	    }
        }
	Writer writer = null;
        try {
            if(! file.createNewFile()){
		System.out.println("File already exists!");
	    }
	    // IDEA ANTIWARNING Is closed in finally.
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

            writer.write("P3\n");

            // Width and height
            writer.write(xsize + " " + ysize + "\n");
            // Max pixel value
            writer.write(maxVal + "\n");

            for (Radiance radiance : radiances){
                int r = (int) (tonemapper.tonemapR(radiance) * maxVal);
                int g = (int) (tonemapper.tonemapG(radiance) * maxVal);
                int b = (int) (tonemapper.tonemapB(radiance) * maxVal);     // IDEA ANTIWARNING b = blue

                writer.write(r + " " + g + " " + b + "\n");
            }
        }
        catch (IOException e){e.printStackTrace();}
	finally{
	    try{
		if (writer != null){
		    writer.close();
		}
	    }
	    catch (IOException e){e.printStackTrace();}
	}
    }
}
