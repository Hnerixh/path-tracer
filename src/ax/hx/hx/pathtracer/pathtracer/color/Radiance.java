package ax.hx.hx.pathtracer.pathtracer.color;

import ax.hx.hx.pathtracer.image.RGBPixel;

/**
 * This holds the color of either a ray or the total radiance for a pixel.
 * Also provides functions for applying colors or other radiance to itself.
 *
 * Radiance can be null_influence, in that case the ray is scheduled
 * to be discarded, OR, when used as the total pixel radiance, black.
 *
 * Can be initialized with a r,g,b triple or  without any arguments. Without
 * arguments the radiance is null.
 */

public class Radiance
{
    private double r;
    private double g;
    private double b;
    private boolean null_influence = false;

    private int influencedBy = 0;

    // Initialize with r g b values
    public Radiance(double r, double g, double b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // Null radiance
    public Radiance(){
	    null_influence = true;
    }

    public void discard(){
        this.null_influence = true;
    }

    // Reset to the equivalent of a new Radiance object, with the supplied colors
    public void reset(double r, double g, double b){
        this.r = r;
        this.g = g;
        this.b = b;
        this. null_influence = false;
        this.influencedBy = 0;
    }

    public RGBPixel getPixel(){
	int ri = (int) (r * 255.0);
	int gi = (int) (g * 255.0);
	int bi = (int) (b * 255.0);
	return new RGBPixel(ri, gi, bi);
    }

    public boolean discarded(){
        return null_influence;
    }

    public void addInfluence(Radiance i){
	// Calculates the avarage of all hits.
    if(i.null_influence){
        return;
    }
    if (this.null_influence){
        r = i.getR();
        g = i.getG();
        b = i.getB();
        influencedBy ++;
        null_influence = false;
        return;
    }

	r = (r*influencedBy + i.getR())/(influencedBy + 1);
	g = (g*influencedBy + i.getG())/(influencedBy + 1);
	b = (b*influencedBy + i.getB())/(influencedBy + 1);
	influencedBy++;
    }

// --Commented out by Inspection START (4/7/14 12:45 PM):
//    public void factor(double factor){
//        r *= factor;
//        g *= factor;
//        b *= factor;
//    }
// --Commented out by Inspection STOP (4/7/14 12:45 PM)

    public void applyColor(Color c){
        r *= c.r;
        g *= c.g;
        b *= c.b;
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
