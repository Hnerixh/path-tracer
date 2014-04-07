package ax.hx.hx.pathtracer.pathtracer.camera;

/**
 * Used for killing off all the CameraWorkers
 */
class CameraWorkerInfo {
    private boolean shouldDie = false;
    public CameraWorkerInfo(){
    }

    public void kill(){
        shouldDie = true;
    }
    public boolean shouldDie(){
        return shouldDie;
    }
}
