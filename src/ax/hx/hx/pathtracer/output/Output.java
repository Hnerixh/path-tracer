package ax.hx.hx.pathtracer.output;

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
 * programs will accept the result. For example 16 bit output is okay
 * with feh, but not with gimp. The first Pixel in the Image sets the
 * bit-depth. Usage with images that contains pixels of different
 * types are just stupid, and therefore unspecified.
 */

public class Output {
    private int xsize;
    private int ysize;
    private File file;
    private Tonemapper tonemapper;
    private int maxVal = 255;

    public int getXsize() {
        return xsize;
    }

    public int getYsize() {
        return ysize;
    }

    public Output(File file, int xsize, int ysize, boolean highBitDepth, Tonemapper tonemapper) {
        this.xsize = xsize;
        this.ysize = ysize;
        this.file = file;
        this.tonemapper = tonemapper;

        if (highBitDepth){
            maxVal = 65535;
        }
    }

    // ANTIWARNING
    // Ask the Output (noun) to output (verb)
    public void output(Radiance[] radiances){
        if (file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

            writer.write("P3\n");

            // Width and height
            writer.write(xsize + " " + ysize + "\n");
            // Max pixel value
            writer.write(maxVal + "\n");

            for (Radiance radiance : radiances){
                int r = (int) (tonemapper.tonemapR(radiance) * maxVal);
                int g = (int) (tonemapper.tonemapG(radiance) * maxVal);
                int b = (int) (tonemapper.tonemapB(radiance) * maxVal);

                writer.write(r + " " + g + " " + b + "\n");
            }
        }
        catch (IOException e){e.printStackTrace();}
    }
}
