import ij.*;
import ij.blob.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;

public class different_color implements PlugIn {

	private ManyBlobs allBlobs;
	private Blob blob;
	private static final int GLEVELS = 256; 
	
	public void print()
	{
		final ImagePlus inputImg = IJ.getImage(); // Load the image
		// assuming that inputImg contains the RGB input...
		final ImagePlus img = new Duplicator().run(inputImg); // make a temp image to work with
		final ImageConverter iConv = new ImageConverter(img); // this object will convert color models
		iConv.convertToHSB(); // converts to hsb, which is enough like HSI to serve the purpose
		final ImageStack stack = img.getImageStack(); // gets the three result planes as an ImageJ "stack"
		final ImageProcessor hue = stack.getProcessor(1); // get an image processor to access the hue plane
		final ImageProcessor saturation = stack.getProcessor(2); // get an image processor to access the saturation
																	// plane
		final ImageProcessor brightness = stack.getProcessor(3); // get an image processor to access the brightness
																	// plane

		final int imageW = inputImg.getWidth();
		final int imageH = inputImg.getHeight();
		final int pixelSat = 0;
		final int pixelBri = 0;
		final int pixelHue = 0;
		new ImagePlus("s", saturation).show();
		new ImagePlus("B", brightness).show();
		new ImagePlus("hue changed", hue).show();
	}

	public void auto_threshold() {
		final ImagePlus inputImg = IJ.getImage(); // Load the image
		// assuming that inputImg contains the RGB input...
		final ImagePlus img = new Duplicator().run(inputImg); // make a temp image to work with
		final ImageConverter iConv = new ImageConverter(img); // this object will convert color models
		iConv.convertToHSB(); // converts to hsb, which is enough like HSI to serve the purpose
		final ImageStack stack = img.getImageStack(); // gets the three result planes as an ImageJ "stack"
		final ImageProcessor hue = stack.getProcessor(1); // get an image processor to access the hue plane
		final ImageProcessor saturation = stack.getProcessor(2); // get an image processor to access the saturation
																	// plane
		final ImageProcessor brightness = stack.getProcessor(3); // get an image processor to access the brightness
																	// plane

		final int imageW = inputImg.getWidth();
		final int imageH = inputImg.getHeight();

		//new ImagePlus("s", saturation).show();
		//new ImagePlus("B", brightness).show();
		//new ImagePlus("hue changed", hue).show();

		int pixelSat = 0;
		int pixelBri = 0;
		int pixelHue = 0;
		int peak = 0;
		int prev = 0;
		int now = 0;
		int next = 0;
		int count = 0;
		int hist[] = new int[GLEVELS]; 

		for (int h = 0; h < imageH; h++) {
			for (int w = 0; w < imageW; w++) {
				pixelHue = hue.getPixel(w, h);
				hist[pixelHue]++;
			}
		}
		int m = 0;
		int n = 0;
		int j = 0;
		int k = 0;

		for(int i = 0; i<255; i++)
		{
			if(k < 256)
			{
				m = i+1;
				n = i+2;
				j = i+3;
				k = i+4;
			}
			if(i == 0 && hist[i] > 0.01*imageH*imageW)
			{
				IJ.log("Intensity "+i+" value "+hist[i]);
				count++;
			}
			else if(hist[n]>hist[m]+100 && hist[n] > hist[j]+100 && hist[n]>hist[i]+500 && hist[n] > hist[k]+500)
			{
				if(hist[n]>0.01*imageH*imageW)
				{
					count++;
					IJ.log("Intensity "+n+" value "+hist[n]);
				}
				
			}
		}
		IJ.log("Peak: "+count);
	}

	

	public void run(final String arg) {
		//print();
		auto_threshold();
	}

}
