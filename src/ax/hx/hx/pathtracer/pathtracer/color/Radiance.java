package ax.hx.hx.pathtracer.pathtracer.color;


/**
 * This holds the color of either a ray or the total radiance for a pixel.
 * Also provides functions for applying colors or other radiance to itself.
 *
 * Radiance can be nullInfluence, in that case the ray is scheduled
 * to be discarded, OR, when used as the total pixel radiance, black.
 *
 * Can be initialized with a r,g,b triple or  without any arguments. Without
 * arguments the radiance is null.
 */

public class Radiance
{
    private double r;
    private double g;
    private double b;     // IDEA ANTIWARNING b = blue
    private boolean nullInfluence = false;

    private int influencedBy = 0;

    // Initialize with r g b values
    // IDEA ANTIWARNING
    // User should choose the reset value, even when there is only one single user.
    public Radiance(double r, double g, double b){     // IDEA ANTIWARNING b = blue
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // Null radiance
    public Radiance(){
	    nullInfluence = true;
    }

    public void discard(){
        this.nullInfluence = true;
    }

    // Reset to the equivalent of a new Radiance object, with the supplied colors
    public void reset(double r, double g, double b){     // IDEA ANTIWARNING b = blue
        this.r = r;
        this.g = g;
        this.b = b;
        this.nullInfluence = false;
        this.influencedBy = 0;
    }

    public boolean discarded(){
        return nullInfluence;
    }

    public void addRadiance(Radiance radiance){
	// Calculates the avarage of all hits.
    if(radiance.nullInfluence){
        return;
    }
    if (this.nullInfluence){
        r = radiance.r;
        g = radiance.g;
        b = radiance.b;
        influencedBy ++;
        nullInfluence = false;
        return;
    }

	r = (r*influencedBy + radiance.r)/(influencedBy + 1);
	g = (g*influencedBy + radiance.g)/(influencedBy + 1);
	b = (b*influencedBy + radiance.b)/(influencedBy + 1);
	influencedBy++;
    }

// --Commented out by Inspection START (4/7/14 12:45 PM):
//    public void factor(double factor){
//        r *= factor;
//        g *= factor;
//        b *= factor;
//    }
// --Commented out by Inspection STOP (4/7/14 12:45 PM)

    public void applyColor(Color color){
        r *= color.r;
        g *= color.g;
        b *= color.b;
    }

    public double getR() {
	return r;
    }

    public double getG() {
	return g;
    }

    public double getB() {
	return b;
    }
}
