package ax.hx.hx.pathtracer.pathtracer.math;

/**
 * A Vector3 is three values, which form an "arrow" from origo to the
 * values in 3D space.
 */
public class Vector3
{
    private double x, y, z;

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vec){
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public Vector3(Coordinate3 coord){
        this.x = coord.x;
        this.y = coord.y;
        this.z = coord.z;
    }
    public void normalize(){
        double length = Math.sqrt(x*x + y*y + z*z);
        x /= length;
        y /= length;
        z /= length;
    }


    public void add(Vector3 vec){
        x += vec.x;
        y += vec.y;
        z += vec.z;
    }

    public void subtract(Vector3 vec){
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
    }

    public double dotProduct(Vector3 vec){
        double tx = x;
        double ty = y;
        double tz = z;

        double vx = vec.x;
        double vy = vec.y;
        double vz = vec.z;

        return (tx*vx + ty*vy + tz*vz);
    }

    public void scale(double scale){
        x *= scale;
        y *= scale;
        z *= scale;
    }

    public Vector3 crossProduct(Vector3 vec){
        double ux = this.x;
        double uy = this.y;
        double uz = this.z;

        double vx = vec.x;
        double vy = vec.y;
        double vz = vec.z;

        double x = uy*vz - uz*vy;
        double y = uz*vx - ux*vz;
        double z = ux*vy - uy*vx;

        return new Vector3(x, y, z);
    }

    public void negate(){
        x = -x;
        y = -y;
        z = -z;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }


    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @return the z
     */
    public double getZ() {
        return z;
     }

     @Override public String toString() {
         return "Vector3{" +
             "x=" + x +
             ", y=" + y +
             ", z=" + z +
             '}';
     }

     @Override
     public boolean equals(final Object o) {
         if (this == o) return true;
         if (!(o instanceof Vector3)) return false;

         final Vector3 vector3 = (Vector3) o;

         return Double.compare(vector3.x, x) == 0
                && Double.compare(vector3.y, y) == 0
                && Double.compare(vector3.z, z) == 0;

     }

     @Override
     public int hashCode() {
         int result;
         long temp;
         temp = Double.doubleToLongBits(x);
         result = (int) (temp ^ (temp >>> 32));
         temp = Double.doubleToLongBits(y);
         result = 31 * result + (int) (temp ^ (temp >>> 32));
         temp = Double.doubleToLongBits(z);
         result = 31 * result + (int) (temp ^ (temp >>> 32));
         return result;
     }
 }
