 package ax.hx.hx.pathtracer.pathtracer.shape;

 import ax.hx.hx.pathtracer.pathtracer.AbstractShape;
 import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
 import ax.hx.hx.pathtracer.pathtracer.math.Normal;
 import ax.hx.hx.pathtracer.pathtracer.math.Ray;
 import ax.hx.hx.pathtracer.pathtracer.math.Vector3;

 /**
   * Created by hx on 3/7/14.
   */
  public class SphereShape extends AbstractShape
  {
      private Coordinate3 origin;
      private double radius;
      public SphereShape(Coordinate3 origin, double radius){
          this.origin = origin;
          this.radius = radius;
      }

      public Coordinate3 intersection(Ray ray){

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
          setNormal(normal);
          setIncoming(ray);
          setHitCoord(intersect);
          return intersect;
      }

      public Coordinate3 oldintersection(Ray ray){
          // if (true)
          //     return new Coordinate3(1.0,1.0,1.0);
          //
          // Here comes some code for finding the intersection. Please
          // rewrite to actually use the features implemented in
          // Vector3 and relatives.
          //
          // The following web-pages was used as reference:
          // http://www.ccs.neu.edu/home/fell/CSU540/programs/RayTracingFormulas.htm
          // http://wiki.cgsociety.org/index.php/Ray_Sphere_Intersection
          //
          // Please note that this is not written to be fast...

          // Begin by calculating the shortest distance from the
          // sphere's origin to the ray.

          // Origin coordinates for the ray:
          double x0 = ray.getOrigin().x;
          double y0 = ray.getOrigin().y;
          double z0 = ray.getOrigin().z;

          // Unit vector for the ray
          double dx = ray.getVector().getX();
          double dy = ray.getVector().getY();
          double dz = ray.getVector().getZ();

          double cx = origin.x;
          double cy = origin.y;
          double cz = origin.z;

          double a = dx * dx + dy * dy + dz * dz;
          double b = 2*dx*(x0-cx) + 2*dy*(y0-cy) + 2*dz*(z0-cz);
          double c =
              cx*cx + cy*cy + cz*cz
              + x0*x0 + y0*y0 + z0*z0
              - 2*(cx*x0 + cy*y0 + cz*z0) - radius*radius;

          double discr = b*b - 4*a*c;
          // The discriminant was negative, so we did not hit the
          // sphere, at least not in 3d space.
          if (discr < 0){
              return null;
          }

          // This means we actually have to solve for t in at^2 + bt + c = 0
          // $t = \frac{-b \pm \sqrt discr}{2a}$

          // Strangley enough, the ray hit the edge of the sphere
//          if (discr == 0){
//              double t = (0-b)/(2*a);
//              return new Coordinate3(x0+t*dx,y0+t*dy,z0+t*dz);
//          }

          // We hit the sphere on two places. We need to solve for both
          // and figure out which hit was closest to the rays origin.
          discr = Math.sqrt(discr);

          double t1 = (discr - b)/(2*a);
          double t2 = (0 - a - discr)/(2*a);

          // This part of the code follows some of the code in
          // http://wiki.cgsociety.org/index.php/Ray_Sphere_Intersection


          if (t1 > t2){
               double temp = t1;
               t1 = t2;
               t2 = temp;
          }

          // If t2 < 0, then the we hit the sphere on the wrong side
          // of the rays origin.
          if (t2 <= 0) {
               return null;
          }

          double t;
          // Figure out which answer is the closest to the ray.
	  if (t1 < 0){
               t = t2;
          }
          else {
               t = t1;
          }
          //  Save the hit with coordinates and normal, and return.

//          Coordinate3 t1Coord = new Coordinate3(x0+t1*dx, y0+t1*dy, z0+t1*dz);
//          Coordinate3 t2Coord = new Coordinate3(x0+t2*dx, y0+t2*dy, z0+t2*dz);
//
//          double t1Dist = t1Coord.distance(ray.getOrigin());
//          double t2Dist = t2Coord.distance(ray.getOrigin());
//
//          double t;
//          if (t1Dist > t2Dist){
//              t = t2Dist;
//          }
//          else {
//              t = t1Dist;
//          }

	  // Hit coordinates
          double xhit = x0+t*dx;
          double yhit = y0+t*dy;
          double zhit = z0+t*dz;

	  // Normals
	  double xnormal = xhit - cx;
	  double ynormal = yhit - cy;
	  double znormal = zhit - cz;

          Normal normal = new Normal(xnormal, ynormal, znormal);
          setNormal(normal);
          setIncoming(ray);
          setHitCoord(new Coordinate3(xhit, yhit, zhit));

          return getHitCoord();
      }

      private Ray transformedRay(Ray ray){
          Coordinate3 o = ray.getOrigin();
          o = new Coordinate3(o);
          o.add(origin);

          return new Ray(o, ray.getVector());
      }
}
