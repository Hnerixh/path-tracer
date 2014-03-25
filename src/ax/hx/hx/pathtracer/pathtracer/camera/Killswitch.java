package ax.hx.hx.pathtracer.pathtracer.camera;

/**
 * Created by hx on 3/25/14.
 */
public class Killswitch {
    private boolean shouldDie = false;
    public Killswitch(){
    }

    public void kill(){
        shouldDie = true;
    }
    public boolean shouldDie(){
        return shouldDie;
    }
}
