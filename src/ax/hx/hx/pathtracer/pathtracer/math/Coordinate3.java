 package ax.hx.hx.pathtracer.pathtracer.math;

  /**
   * Created by hx on 3/7/14.
   */
  public class Coordinate3
  {
      public double x,y,z;
      public Coordinate3(double x, double y, double z) {
          this.x = x;
          this.y = y;
          this.z = z;
       }
       public Coordinate3(Coordinate3 c){
           this.x = c.x;
           this.y = c.y;
           this.z = c.z;
      }
      public double distance(Coordinate3 co){
          double dx = co.x - x;
          double dy = co.y - y;
          double dz = co.z - z;
          return Math.sqrt(dx*dx + dy*dy + dz*dz);
      }
      public void add(Coordinate3 co){
          this.x += co.x;
          this.y += co.y;
          this.z += co.z;
      }

      @Override public String toString() {
	  return "Coordinate3{" +
		 "x=" + x +
		 ", y=" + y +
		 ", z=" + z +
		 '}';
      }
  }
