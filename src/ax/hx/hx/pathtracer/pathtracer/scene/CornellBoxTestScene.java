// package ax.hx.hx.pathtracer.pathtracer.scene;

// import ax.hx.hx.pathtracer.image.ImageOutput;
// import ax.hx.hx.pathtracer.image.PPMOutput;
// import ax.hx.hx.pathtracer.image.RGBImage;
// import ax.hx.hx.pathtracer.pathtracer.AbstractScene;
// import ax.hx.hx.pathtracer.pathtracer.AbstractShape;
// import ax.hx.hx.pathtracer.pathtracer.camera.Camera;
// import ax.hx.hx.pathtracer.pathtracer.color.Color;
// import ax.hx.hx.pathtracer.pathtracer.material.*;
// import ax.hx.hx.pathtracer.pathtracer.math.Coordinate3;
// import ax.hx.hx.pathtracer.pathtracer.Material;
// import ax.hx.hx.pathtracer.pathtracer.math.Normal;
// import ax.hx.hx.pathtracer.pathtracer.shape.PlaneShape;
// import ax.hx.hx.pathtracer.pathtracer.Scene;
// import ax.hx.hx.pathtracer.pathtracer.shape.SphereShape;

// import java.io.File;

// /**
//  * Created by hx on 3/10/14.
//  */
// class CornellBoxTestScene extends AbstractScene
// {
//     private CornellBoxTestScene(){
//         // Create a Cornell box of size 3x3x3

//         Color white = new Color(.9,.9,.9);
//         Color leftColor = new Color(0.75, 0.25, 0.25);
//         Color rightColor = new Color(0.24, 0.25, 0.75);
//         Color checker1 = new Color(.9,.9,.9);
//         Color checker2 = new Color(.9,.9,.9);
//         double checkerSize = 0.25;

//         // Add a light center
//         Coordinate3 origin = new Coordinate3(0,-1.5,4.5);
//         AbstractShape shape = new SphereShape(origin, 0.5);
//         Color color = new Color(1,1,1);
//         Material material = new LightMaterial(color);
//         shape.setMaterial(material);
//         getShapes().add(shape);

//         // Floor
//         origin = new Coordinate3(0,-1.5,0);
//         Normal normal = new Normal(0,1,0);
//         shape = new PlaneShape(normal, origin);
//         material = new CheckeredDiffuseMaterial(checker1, checker2, checkerSize);
//         shape.setMaterial(material);
//         getShapes().add(shape);

//         // Roof
//         origin = new Coordinate3(0,1.5,0);
//         normal = new Normal(0,1,0);
//         shape = new PlaneShape(normal, origin);
//         material = new DiffuseMaterial(white);
//         shape.setMaterial(material);
//         getShapes().add(shape);

//         // Left
//         origin = new Coordinate3(1.5,0,0);
//         normal = new Normal(1,0,0);
//         shape = new PlaneShape(normal, origin);
//         material = new DiffuseMaterial(leftColor);
//         shape.setMaterial(material);
//         getShapes().add(shape);


//         // Right
//         origin = new Coordinate3(-1.5,0,0);
//         normal = new Normal(1,0,0);
//         shape = new PlaneShape(normal, origin);
//         material = new DiffuseMaterial(rightColor);
//         shape.setMaterial(material);
//         getShapes().add(shape);

//         // Back
//         origin = new Coordinate3(0,0,-6);
//         normal = new Normal(0,0,1);
//         color = new Color(.2,.2,.8);
//         shape = new PlaneShape(normal, origin);
//         material = new CheckeredDiffuseMaterial(checker1, checker2, checkerSize);
//         shape.setMaterial(material);
//         getShapes().add(shape);


//         // Black lamp behind camera to increase performance
//         origin = new Coordinate3(0,0,0.1);
//         normal = new Normal(0,0,1);
//         shape = new PlaneShape(normal, origin);
//         material = new DiffuseMaterial(white);
//         shape.setMaterial(material);
//         getShapes().add(shape);

//         // Add a sphere
//         origin = new Coordinate3(-.5, 1, 4.0);
//         shape = new SphereShape(origin, 0.5);
//         material = new RefractiveMaterial(white, 1.4);
//         shape.setMaterial(material);
//         getShapes().add(shape);

//         // Add a sphere
//         origin = new Coordinate3(.4, 1, 5.4);
//         shape = new SphereShape(origin, 0.5);
//         color = new Color(.7,.7,1.0);
//         material = new DiffuseMaterial(white);
//         shape.setMaterial(material);
//         getShapes().add(shape);

// //	// Add a triangle
// //        Coordinate3 a = new Coordinate3(-.9, -0.5, 4.4);
// //        Coordinate3 b = new Coordinate3(0,-.5,3);
// //        Coordinate3 c = new Coordinate3(.9,-.5,4.4);
// //	shape = new TriangleShape(a,b,c);
// //	color = new Color(1,1,1);
// //	material = new DiffuseMaterial(color);
// //	shape.setMaterial(material);
// //        getShapes().add(shape);
//     }

//     public static void main(String[] args){
//         RGBImage image = new RGBImage(1024,1024);
//         ImageOutput output = new PPMOutput(image, new File("/home/hx/tmp/FirstCameraTest.ppm"));
//         image.setOutputModule(output);
//         Scene scene = new CornellBoxTestScene();
//         Camera camera = new Camera(scene, 1.0, image, 16, 1.0, 7);
//         while (true){
//             camera.doPasses(8);
//             camera.render();
//             image.output();
//             System.out.println("Wrote to file");
//         }
//     }
// }
