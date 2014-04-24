 package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.material;

  import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.Material;
  import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Color;
  import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.color.Radiance;
  import se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math.*;

 /**
  * This class is a material definition for refractive materials. This
  * class follows rules for refraction, and uses the Schlick
  * approximation instead of Snell's law.
  */
 public class RefractiveMaterial implements Material
  {
      private final Color color;
      private final double indexOfRefraction;

      public RefractiveMaterial(Color color, double indexOfRefraction){
          this.color = color;
          this.indexOfRefraction = indexOfRefraction;
      }

      public Ray getRandomRay(Ray ray, Normal normal, Coordinate3 origin){
          if (Math.random() > reflectance(ray, normal)){
              return refractedRay(ray, normal, origin);
          }
          return reflectedRay(ray, normal, origin);
      }

      private double reflectance(Ray ray, Normal normal){
          return RayMath.reflectance(normal, ray, indexOfRefraction);
      }

      private Ray refractedRay(Ray ray, Normal normal, Coordinate3 origin){
          // If the ray is inside something, we need to invert the
          // index of refraction.

          // Variables

          Vector3 incoming = new Vector3(ray.getVector()); // The vector from the ray
          Vector3 norm = new Vector3(normal); // The normal vector
          incoming.normalize();
          norm.normalize();

          // The normal vector needs to be facing towards the rays
          // vector This is just a security check, shapes are like
          // supposed to return a correct normal.
          if (incoming.dotProduct(norm) > 0){
              norm.negate();
          }

          // If the ray is inside the object this material belongs to,
          // invert the refraction index.
          double n1 = 1;
          double n2 = indexOfRefraction;
          if (ray.isInsideSomething()){
              n1 = n2;
              n2 = 1;
          }
          double r = n1/n2;

          double cosI = - norm.dotProduct(incoming);
          double sinT2 = r*r*(1.0-cosI*cosI);

          if (sinT2 > 1.0){
              return reflectedRay(ray, normal, origin);
          }
          double cosT = Math.sqrt(1.0-sinT2);

          incoming.scale(r);
          norm.scale(r * cosI - cosT);
          incoming.add(norm);

          boolean insideSomething = ray.isInsideSomething();
          if (incoming.dotProduct(normal) < 0){
              insideSomething = ! insideSomething;
          }

           return new Ray(origin, incoming, insideSomething);
      }

      private Ray reflectedRay(Ray ray, Normal normal, Coordinate3 origin){
	  return RayMath.reflectedRay(ray, normal, origin);
 /*       Vector3 vec = new Vector3(ray.getVector());
        vec.normalize();

        Vector3 norm = new Vector3(normal);

        norm.scale(2*(norm.dotProduct(vec)));

        norm.subtract(vec);
        norm.negate();
        norm.normalize();
        return new Ray(origin, norm, ray.isInsideSomething());*/
      }

      public void applyToRadiance(Ray incoming,
                                      Ray outgoing,
                                      Normal normal,
                                      Radiance incomingRadiance){
          incomingRadiance.applyColor(color);
      }
  }
