package ax.hx.hx.pathtracer.image;

/**
 * Interface for a class implementing Image. A image should be able to
 * give you it's dimensions, as well as a Pixel for each possible
 * combination of coordinates inside the image. Results for trying to
 * get a pixel outside of the image are undefined.
 */
interface Image
{
    int getWidth();
    int getHeight();
    Pixel getPixel(int x, int y);
    Pixel getPixel(int i);
}
