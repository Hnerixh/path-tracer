package ax.hx.hx.pathtracer.pathtracer.color;

/**
 * This is just a floating point rgb color.
 */
public class Color
{
    public final double r;
    public final double g;
    public final double b;     // IDEA ANTIWARNING b = blue
    public Color(double r, double g, double b){
	this.r = r;
	this.g = g;
	this.b = b;
    }
}
