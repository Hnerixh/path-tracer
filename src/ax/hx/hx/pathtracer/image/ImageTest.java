package ax.hx.hx.pathtracer.image;

import java.io.File;

final class ImageTest {
    private ImageTest() {}

    public static void main(String[] args){
	// Yes, here we use magic numbers in quick and dirty test code.
        RGBImage image = new RGBImage(16,16);
	// AntiWarning: Quick and dirty test code, don't run it on Windows, please.
        image.setOutputModule(new PPMOutput(image, new File("/home/hx/test_output.ppm")));
        for (int i = 0; i < 256; i++) {
            image.appendPixel(new RGBPixel(i,i,i));
        }
        image.output();
    }
}
