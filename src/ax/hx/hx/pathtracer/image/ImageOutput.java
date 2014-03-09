package ax.hx.hx.pathtracer.image;

/**
 * This needs to be implemented in order to be delegated image output.
 * Classes implementing ImageOutput are recommended to be passed the
 * image they belong to before the image tries to output using them.
 */
public interface ImageOutput
{
    public void output();
}
