package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.camera;

/**
 * Used for killing off all the CameraWorkers
 */
class CameraWorkerInfo {
    private boolean shouldDie = false;

   public void kill(){
        shouldDie = true;
   }

    public boolean shouldDie(){

	return shouldDie;
    }
}
