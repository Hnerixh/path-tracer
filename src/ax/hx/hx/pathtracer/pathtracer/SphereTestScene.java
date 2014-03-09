package ax.hx.hx.pathtracer.pathtracer;

/**
 * Created by hx on 3/7/14.
 */
public class SphereTestScene extends AbstractScene
{
    public SphereTestScene(){
	Coordinate3 origin = new Coordinate3(0,0,3.0);
	SphereShape sphere = new SphereShape(origin, 0.5);
        shapes.add(sphere);
    }
}
