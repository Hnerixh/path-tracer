package ax.hx.hx.pathtracer.pathtracer.color;

/**
 * This is just a floating point rgb color class.
 */
public class Color
{
    /**
     * Red
     */
    public final double r;
    /**
     * Green
     */
    public final double g;
    /**
     * Blue
     */
    public final double b;     // IDEA ANTIWARNING b = blue
    public Color(double r, double g, double b){
	this.r = r;
	this.g = g;
	this.b = b;
    }
}
