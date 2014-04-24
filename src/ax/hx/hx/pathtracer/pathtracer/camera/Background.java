package ax.hx.hx.pathtracer.pathtracer.camera;

import ax.hx.hx.pathtracer.pathtracer.color.Color;
import ax.hx.hx.pathtracer.pathtracer.math.Normal;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is used as background when rendering.
 * The background can be either a solid color
 * or a image that Java can read using BufferedImage.
 *
 * This sucks because Java only reads 8-bit images.
 */
public class Background {
    private BufferedImage image = null;
    private int width;
    private int heigth;

    private Color color = new Color(1.0,0.0,1.0); // Dummy color until it might get initialized.


    // According to documentation a ColorModel will always give values
    // in [0..255]
    private static final double RANGE = 255.0;
    public Background(File file){
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            image = null;
            e.printStackTrace();
        }
        width = image.getWidth();
        heigth = image.getHeight();
    }

    // IDEA ANTIWARNING b = blue
    public Background(double r, double g, double b){
        color = new Color(r,g,b);
    }

    public Background(){}


    public Color worldColor(Normal vec){
        if (image == null){
            return color;
        }

        double x,y,z;
        x = vec.getX();
        y = vec.getY();
        z = vec.getZ();

        double yPos = (Math.acos(-y)) / Math.PI; // in [0,1], 0 = image top
        double xPos = (Math.atan(z/x) ) / Math.PI; // 0,1 = edge, 0.5 middle.
        // Note that xPos might be negative at the moment, however,
        // xPos is guaranteed to be within [0+x*1/4, 1+x*1/4] where x
        // is in [0,1]. This was done because we want the edges of the
        // image to be in the negative direction of the Z
        // axis. (Behind the camera)

        int yCoordinate = (int) Math.floor(yPos * heigth);
        int xCoordinate = (int) Math.floor(xPos * width);
        xCoordinate += width;
        xCoordinate %= width; // And now we have the location of the
                              // pixel we want.
        yCoordinate %= heigth;
        return getColorFromImage(xCoordinate, yCoordinate);
    }

    private Color getColorFromImage(int x, int y){
        int pixel = image.getRGB(x,y);
	// IDEA ANTIWARNING
        // So this is why I wrote my own image output module...
        // The normal non-stupid way did not want to play ball with me,
	// so manual mode it is.
	// This is neither nice nor correct, but rather funny.
	// The 8 is not detected as a magic number, strange...
        double r = ((pixel & 0x00ff0000) >> 16);
        double g = ((pixel & 0x0000ff00) >> 8);
        double b = (pixel & 0x000000ff); // IDEA ANITWARNING b = blue

        r /= RANGE;
        b /= RANGE;
        g /= RANGE;

        return new Color(r,g,b);
    }
}
