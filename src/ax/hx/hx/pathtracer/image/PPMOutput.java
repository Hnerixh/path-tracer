package ax.hx.hx.pathtracer.image;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

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
public class PPMOutput implements ImageOutput
{
    private RGBImage image;
    private File file;

    public PPMOutput(RGBImage image, File file){
        this.image = image;
        this.file = file;
    }

    public void output(){
        int size = image.getSize();
        if (file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

            writer.write("P3\n");

            // Width and heigth
            writer.write(image.getWidth() + " " + image.getHeight() + "\n");

            // Maximum value, assuming all pixels are of the same type.
            writer.write(image.getPixel(0,0).maxValue() + "\n");

            for (int i = 0; i < size; i++) {
                writer.write(stringifyPixel(image.getPixel(i)));
            }

            writer.close();
        }
            // Some error handling
            // The file will be found, it was just created.
            catch (FileNotFoundException e){}
            catch (IOException e){System.out.println("Could not write file...");}
    }

    private String stringifyPixel(Pixel pixel){
        return (pixel.getR() + " " + pixel.getG() + " " + pixel.getB() + "\n");
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param set the file to write to
     */
    public void setFile(File file) {
        this.file = file;
    }
}
