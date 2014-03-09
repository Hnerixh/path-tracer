package ax.hx.hx.pathtracer.image;

/**
 * Just a dummy output
 */
public class DummyOutput implements ImageOutput
{
    // AntiWarning: Of couse the Image is left unused, this is a dummy class.
    DummyOutput(Image image){
    }
    public void output(){
        System.out.println("Dummy output engaged");
    }
}
