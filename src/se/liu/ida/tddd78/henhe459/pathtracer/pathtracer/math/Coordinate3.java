 package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math;

  /**
   * Created by hx on 3/7/14.
   */
  public class Coordinate3
  {
      /**
       * x coordinate
       */
      public final double x;
      /**
       * y coordinate
       */
      public final double y;
      /**
       * z coordinate
       */
      public final double z;
      public Coordinate3(double x, double y, double z) {
          this.x = x;
          this.y = y;
          this.z = z;
       }
// --Commented out by Inspection START (4/7/14 12:44 PM):
//       public Coordinate3(Coordinate3 c){
//           this.x = c.x;
//           this.y = c.y;
//           this.z = c.z;
//      }
// --Commented out by Inspection STOP (4/7/14 12:44 PM)
      public double distance(Coordinate3 co){
          double dx = co.x - x;
          double dy = co.y - y;
          double dz = co.z - z;
          return Math.sqrt(dx*dx + dy*dy + dz*dz);
      }

      @Override public String toString() {
	  return "Coordinate3{" +
		 "x=" + x +
		 ", y=" + y +
		 ", z=" + z +
		 '}';
      }
  }
