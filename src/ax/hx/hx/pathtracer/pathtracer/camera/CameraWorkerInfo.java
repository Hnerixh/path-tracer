package ax.hx.hx.pathtracer.pathtracer.camera;

/**
 * Used for killing off all the CameraWorkers
 */
class CameraWorkerInfo {
    private boolean shouldDie = false;

    // --Commented out by Inspection START (4/15/14 1:19 AM):
//    public void kill(){
//        shouldDie = true;
//    }
// --Commented out by Inspection STOP (4/15/14 1:19 AM)
    public boolean shouldDie(){
        return shouldDie;
    }
}
