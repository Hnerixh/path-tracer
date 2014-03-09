package ax.hx.hx.pathtracer.image;

/**
 * A pixel should be able to give out it's color on R,G and B channels. Bit depth is undefined.
 */
public interface Pixel
{
    int getR();
    int getG();
    int getB();
    int maxValue();
}
