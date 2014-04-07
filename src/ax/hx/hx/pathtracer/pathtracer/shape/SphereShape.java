 package ax.hx.hx.pathtracer.pathtracer.shape;

 import ax.hx.hx.pathtracer.pathtracer.AbstractShape;
 import ax.hx.hx.pathtracer.pathtracer.color.IntersectionInfo;
 import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
 import ax.hx.hx.pathtracer.pathtracer.math.Normal;
 import ax.hx.hx.pathtracer.pathtracer.math.Ray;
 import ax.hx.hx.pathtracer.pathtracer.math.Vector3;

 /**
   * Created by hx on 3/7/14.
   */
  public class SphereShape extends AbstractShape
  {
      private final Coordinate3 origin;
      private final double radius;
      public SphereShape(Coordinate3 origin, double radius){
          this.origin = origin;
          this.radius = radius;
      }

      public IntersectionInfo intersection(Ray ray){

          // Following
          // http://www.cs.princeton.edu/courses/archive/fall00/cs426/lectures/raycast/sld013.htm
          Vector3 vorigin = new Vector3(ray.getOrigin());
          Vector3 vdirection = new Vector3(ray.getVector());

          Vector3 sorigin = new Vector3(origin);

          Vector3 relPos = new Vector3(sorigin);
	relPos.subtract(vorigin);
          // tca is the length to the point where the ray would hit a
          // plane formed by the center point in the sphere and the
          // vector from that point to the rays origin as a normal.
          double tca = relPos.dotProduct(vdirection);

          // Sphere behind obeserver
          if (tca < 0){
              return null;
          }

          // d is the distance from the point on the plane
          // (tca*vdirection) would hit, to the center of the sphere.
          // We keep d squared.
          double dsquared = relPos.dotProduct(relPos) - tca * tca;

          // Not a hit.
          if (dsquared > radius * radius){
              return null;
          }

          // Distance from the previously mentioned point (which we
          // now know is in the middle of one or two hits) to the hits
          // on the surface.
          double thc = Math.sqrt(radius*radius - dsquared);

          double t;
          // Edge hit
          if (thc == 0){
              t = thc;
          }
          // Figure out the closest hit
          else {
	      double ta = tca-thc;
	      double tb = tca+thc;

	      // Make ta smaller than tb
	      if (ta > tb){
		  t = tb;
		  tb = ta;
		  ta = t;
	      }

	      // Sphere behind observer, return null
	      if (tb < 0.00001) {
		 return null;
	      }

	      // Entire sphere in front of observer
	      if (ta > 0.00001){
		 t = ta;
		  t -= 0.000001; // Avoid rounding errors
	      }
	      // Observer in sphere;
	      else {
		  t = tb;
		  t += 0.000001; // Avoid rounding errors
	      }
          }

          // We have the distance, now work out the intersection point.
          vdirection.scale(t);
          vorigin.add(vdirection);

          Coordinate3 intersect = new Coordinate3(vorigin.getX(), vorigin.getY(), vorigin.getZ());
	    Vector3 normV = new Vector3(vorigin);
          // Figure out the normal
          normV.subtract(sorigin);
          Normal normal = new Normal(normV);

//	  if (normal.dotProduct(vorigin) < 0){
//	      normal.negate();
//	  }


          // Save hits
          return new IntersectionInfo(ray, normal, intersect);
      }
}
