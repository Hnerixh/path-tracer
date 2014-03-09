package ax.hx.hx.pathtracer.image;

/**
 * Represents a pixel with each channel on a value between MAX_VALUE
 * (255, 8-bit color depth) and 0. Initiation values outside of these
 * limits will be crushed to comply.
 */
public class RGBPixel implements Pixel
{
    private final int r;
    private final int g;
    private final int b;
    private final static int MAX_VALUE = 255;
    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int maxValue() {
        return MAX_VALUE;
    }
    // AntiWarning: no, the variable b does not have type byte,
    // but is a blue channel.
    public RGBPixel(int r, int g, int b){
        // Crushing the values into compliance.
        this.r = Math.max(0, Math.min(r, MAX_VALUE));
        this.g = Math.max(0, Math.min(g, MAX_VALUE));
        this.b = Math.max(0, Math.min(b, MAX_VALUE));
    }
}
