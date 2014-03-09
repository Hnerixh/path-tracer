 package ax.hx.hx.pathtracer.pathtracer;

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
              + x0*x0 + y0*90 + z0*z0
              - 2*(cx*x0 + cy*y0 + cz*z0) - radius*radius;

          double discr = b*b - 4*a*c;
          // The discriminant was negative, so we did not hit the
          // sphere, at least not in 3d space.
          if (discr < 0){
              return null;
          }

          // This means we actually have to solve for t in at^2 + bt + c = 0
          // $t = \frac{-b \pm \sqrt discr}{2a}$

          // To actually pay some respect to performance, we will
          // duplicate the code, and solve it separately for discr == 0
          // and discr > 0;

          // Strangley enough, the ray hit the edge of the sphere
          if (discr == 0){
              double t = (0-b)/(2*a);
              return new Coordinate3(x0+t*dx,y0+t*dy,z0+t*dz);
          }

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
          if (t2 < 0) {
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

          // Hit coordinates:
          double xhit = x0+t*dx;
          double yhit = y0+t*dy;
          double zhit = z0+t*dz;

          // The normal is just the hit coordinates offset by the
          // spheres origin and normalized.
          double xnormal = xhit - cx;
          double ynormal = yhit - cy;
          double znormal = zhit - cz;

          setNormal(new Normal(xnormal, ynormal, znormal));
          setIncoming(ray);
          setHitCoord(new Coordinate3(x0+t*dx,y0+t*dy,z0+t*dz));

          return getHitCoord();
      }

      private Ray transformedRay(Ray ray){
          Coordinate3 o = ray.getOrigin();
          o = new Coordinate3(o);
          o.add(origin);

          return new Ray(o, ray.getVector());
      }
}
