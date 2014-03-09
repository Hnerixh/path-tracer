package ax.hx.hx.pathtracer.image;

/**
 * An RGB image is able to store a list of pixels, where each pixel is
 * guaranteed to provide for a depth of at least 8 bits for each
 * channel. The internal representation is a one-dimensional array,
 * because I think [x + y * this.x] is a lot simpler to read than
 * [x][y]. Strange...
 *
 * Adding the pixels to the image can be accomplished either by
 * setPixel(), or appendPixel(). appendPixel() will loop over the
 * imape again in case of overflow.
 *
 * Images can be written to disk or shown on screen using an interface
 * implementing ImageOutput. The ImageOutput is set using
 * setOutputModule().  Thus Image supports the State pattern. On
 * initialization the output module is set to a DummyOutput.
 */
public class RGBImage implements Image
{
    private final int x;
    private final int y;

    private Pixel[] pixels;

    private int appendCounter = 0;
    private final int size;

    private ImageOutput outputModule;

    public RGBImage(int x, int y) {
        this.x = x;
        this.y = y;
        this.size = x * y;
        pixels = new Pixel[size];

        this.outputModule = new DummyOutput(this);
    }

    public Pixel getPixel(int x, int y) {
        return pixels[x + y * this.x];
    }

    public Pixel getPixel(int i){
        return pixels[i];
    }

    public void appendPixel(Pixel pixel) {
        if (appendCounter == size) {
            appendCounter = 0;
        }
        pixels[appendCounter] = pixel;
        appendCounter++;
    }

    public int getWidth() {
        return x;
    }

    public int getHeight() {
        return y;
    }

    public int getSize() {
        return this.size;
    }

    public void setOutputModule(ImageOutput output){
        outputModule = output;
    }

    public void output(){
        outputModule.output();
    }
}
