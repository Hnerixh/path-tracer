package ax.hx.hx.pathtracer.pathtracer.camera;

import ax.hx.hx.pathtracer.pathtracer.color.Radiance;
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

    private double dummyR = 1.0;
    private double dummyG = 0.2;
    private double dummyB = 1.0;


    // According to documentation a ColorModel will always give values
    // in [0..255]
    private static final double RANGE = 255.0;
    public Background(File file){
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            image = null;
            System.out.println("Could not load background image. Using dummy.");
        }
        width = image.getWidth();
        heigth = image.getHeight();
    }

    public Background(double r, double g, double b){
        dummyB = b;
        dummyG = g;
        dummyR = r;
    }

    public Background(){}


    public Radiance worldInfluence(Normal vec){
        if (image == null){
            return new Radiance(dummyR, dummyG, dummyB);
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
        return getInfluenceFromImage(xCoordinate, yCoordinate);
    }

    private Radiance getInfluenceFromImage(int x, int y){
        int pixel = image.getRGB(x,y);
        // So this is why I wrote my own image output module...
        // The normal non-stupid way did not want to play ball with me.
        double r = (double) ((pixel & 0x00ff0000) >> 16);
        double g = (double) ((pixel & 0x0000ff00) >> 8);
        double b = (double) (pixel & 0x000000ff);

        r /= RANGE;
        b /= RANGE;
        g /= RANGE;

        return new Radiance(r,g,b);
    }
}
