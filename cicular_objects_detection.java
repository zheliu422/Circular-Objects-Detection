import ij.*;
import ij.blob.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;

public class c_o_d implements PlugIn {

private ManyBlobs allBlobs;
private Blob blob;
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

	public void Red_Blob() {
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
		int pixelSat = 0;
		int pixelBri = 0;
		int pixelHue = 0;
		// new ImagePlus("s", saturation).show();
		// new ImagePlus("B", brightness).show();
		// new ImagePlus("hue changed", hue).show();
		final ImageProcessor copyHue_Red = hue.duplicate();
		for (int h = 0; h < imageH; h++) {
			for (int w = 0; w < imageW; w++) {
				pixelHue = hue.getPixel(w, h);
				pixelBri = brightness.getPixel(w, h);
				pixelSat = saturation.getPixel(w, h);
				if (pixelHue < 15 || pixelHue > 248) // &&
				{
					copyHue_Red.putPixel(w, h, 0);
				} else {
					copyHue_Red.putPixel(w, h, 255);
				}
			}
		}
		final ImagePlus imp_red = new ImagePlus("Result", copyHue_Red);

		// display
		ImageProcessor hue_red = imp_red.getProcessor();

		//new ImagePlus("hue binary", hue_red).show();

		// blob
		final ManyBlobs allBlobs = new ManyBlobs(imp_red); // Extended ArrayList
		allBlobs.findConnectedComponents(); // Start the Connected Component Algorithm
		allBlobs.setBackground(1); // 0 for black, 1 for 255
		int count = 0;
		double surface = 0;
		double delta = 0;
		int k = 0;
		//Frame frame = WindowManager.getFrame("ROI Manager");
		//if (frame == null)
			//IJ.run("ROI Manager...");
		//frame = WindowManager.getFrame("ROI Manager");
		//RoiManager roiManager = (RoiManager) frame;

		for (int i = 0; i < allBlobs.size(); i++) {
			final double convex = allBlobs.get(i).getAreaConvexHull();
			final double area = allBlobs.get(i).getEnclosedArea();
			final double preimeter = allBlobs.get(i).getPerimeter();
			
		if(area > 2000)
		{
				//Polygon p = allBlobs.get(i).getOuterContour();
				//int n = p.npoints;
				//float[] x = new float[p.npoints];
				//float[] y = new float[p.npoints];   
				//for (int j=0; j<n; j++) {
			//		   x[j] = p.xpoints[j]+0.5f;
				//	y[j] = p.ypoints[j]+0.5f;
				//}
				//Roi roi = new PolygonRoi(x,y,n,Roi.TRACED_ROI);             
			//	Roi.setColor(Color.green);
			//	roiManager.add(imp_red, roi, i);

			count++;
			surface = surface + area;
			delta = convex - area;

			if (delta > 1000 && delta < 3000) {
				if (area > 7000) {
					count++;
					//IJ.log("+1Delta################## ");
				}
			}
			else if(delta > 3000 && delta < 5000)
			{
				if(area > 5000)
				{
					count = count + 2;
					//IJ.log("+2Delta################## ");
				}
				else{
					count = count + 1;
					//IJ.log("+1DeltaButArea################## ");
				}
			}
			else if(delta > 5000 && delta < 9000)
			{
				if(area > 10000)
				{
					count = count + 3;
					//IJ.log("+3Delta################## ");
				}
				else{
					count = count + 2;
					//IJ.log("+2DeltaButArea################## ");
				}
			}  
			else if(delta > 9000 && delta < 30000)
			{
				count = count + 4;
				//IJ.log("+4Delta################## ");
			} 
			else if(delta > 30000)
			{
				count = count + 9;
				//IJ.log("+9Delta################## ");
			} 
			else if (preimeter > 400) {
				count++;
				//IJ.log("+1Per################## ");
			}
			
				//k++;				 
				//IJ.log("#: "+k);
				//IJ.log("#: "+k+" delta: "+delta);
				//IJ.log("#: "+k+" area: "+area);
				//IJ.log("real #: "+count);
		}
	}
		IJ.log("Red: " + count + " m&ms, total area: " + surface + " pixels.");

	}

	public void Yellow_Blob() {
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
		int pixelSat = 0;
		int pixelBri = 0;
		int pixelHue = 0;
		//new ImagePlus("s", saturation).show();
		//new ImagePlus("B", brightness).show();
		//new ImagePlus("h changed", hue).show();
		final ImageProcessor copyHue = hue.duplicate();

		for (int h = 0; h < imageH; h++) {
			for (int w = 0; w < imageW; w++) {
				pixelBri = brightness.getPixel(w, h);
				pixelSat = saturation.getPixel(w, h);
				pixelHue = hue.getPixel(w, h);
				if (pixelHue > 30 && pixelHue < 41)
				// if(pixelHue <140)
				{
					copyHue.putPixel(w, h, 0);
				} else {
					copyHue.putPixel(w, h, 255);
				}
			}
		}
		final ImagePlus imp2 = new ImagePlus("Result", copyHue);
		// display
		final ImageProcessor hue2 = imp2.getProcessor();

		//new ImagePlus("hue binary", hue2).show();

		// blob
		final ManyBlobs allBlobs = new ManyBlobs(imp2); // Extended ArrayList
		allBlobs.findConnectedComponents(); // Start the Connected Component Algorithm
		allBlobs.setBackground(1); // 0 for black, 1 for 255
		int count = 0;
		double surface = 0;
		double delta = 0;
		int k = 0;
		
		for (int i = 0; i < allBlobs.size(); i++) {
			
			final double convex = allBlobs.get(i).getAreaConvexHull();
			final double area = allBlobs.get(i).getEnclosedArea();
			final double preimeter = allBlobs.get(i).getPerimeter();
			if (area > 2000) {
				count++;
				surface = surface + area;
				delta = convex - area;
				if (delta > 1000 && delta < 2600) {
					if (area > 7000) {
						count++;
						//IJ.log("+1Delta################## ");
					}
				}
				else if(delta > 2600 && delta < 5000)
				{
					count = count + 2;
					//IJ.log("+2Delta################## ");
				}
				else if(delta > 5000 && delta < 7300)
				{
					count = count + 3;
					//IJ.log("+3Delta################## ");
				}  
				else if(delta > 7300)
				{
					count = count + 4;
					//IJ.log("+4Delta################## ");
				}  
				else if (preimeter > 400) {
					count++;
					//IJ.log("+1Per################## ");
				}
				
				//k++;				// 
				//IJ.log("#: "+k);
				//IJ.log("#: "+k+" delta: "+delta);
				//IJ.log("#: "+k+" preimeter: "+preimeter);
				//IJ.log("real #: "+count);
			}
		}
		IJ.log("Yellow: " + count + " m&ms, total area: " + surface + " pixels.");
	}

	public void Green_Blob(){
		ImagePlus inputImg = IJ.getImage(); //Load the image
		// assuming that inputImg contains the RGB input...
		ImagePlus img = new Duplicator().run(inputImg);        // make a temp image to work with
		ImageConverter iConv = new ImageConverter(img);        // this object will convert color models
		iConv.convertToHSB();       					       // converts to hsb, which is enough like HSI to serve the purpose
		ImageStack stack = img.getImageStack();                // gets the three result planes as an ImageJ "stack"
		ImageProcessor hue = stack.getProcessor(1);        // get an image processor to access the hue plane
		ImageProcessor saturation = stack.getProcessor(2);        // get an image processor to access the saturation plane
		ImageProcessor brightness = stack.getProcessor(3);        // get an image processor to access the brightness plane
		
		int imageW = inputImg.getWidth();
		int imageH = inputImg.getHeight();
		int pixelSat = 0;
		int pixelBri = 0;
		int pixelHue = 0;

		ImageProcessor copyHue = hue.duplicate();
		for(int h = 0; h < imageH; h++)
        {
            for(int w = 0; w < imageW; w++)
            {
				pixelBri = brightness.getPixel(w,h);
				pixelSat = saturation.getPixel(w,h);
				pixelHue = hue.getPixel(w,h);
				if(pixelHue > 67 && pixelHue < 99) 
				//if(pixelHue <140) 
				{
					copyHue.putPixel(w,h,0);
				}
				else
				{
					copyHue.putPixel(w,h,255);
				}
            }
        }
		ImagePlus imp2 = new ImagePlus("Result", copyHue);
			//display
		ImageProcessor hue2 = imp2.getProcessor();
		//new ImagePlus("hue binary", hue2).show();

	 //blob
		ManyBlobs allBlobs = new ManyBlobs(imp2); // Extended ArrayList
    	allBlobs.findConnectedComponents(); // Start the Connected Component Algorithm
		allBlobs.setBackground(1); // 0 for black, 1 for 255
		int count = 0;
		double surface = 0;
		double delta = 0;
		double connect[];
		int k = 0;
		
		for(int i = 0; i< allBlobs.size(); i++)
		{
			double convex = allBlobs.get(i).getAreaConvexHull();
			double area = allBlobs.get(i).getEnclosedArea();
			double preimeter = allBlobs.get(i).getPerimeter();

			if(area > 1000)
			{
			count++;
			surface = surface + area;
			delta = convex - area;
			if (delta > 1000 && delta < 2600) {
				if (area > 7000) {
					count++;
					//IJ.log("+1Delta################## ");
				}
			}
			else if(delta > 2600 && delta < 5000)
			{
				count = count + 2;
				//IJ.log("+2Delta################## ");
			}
			else if(delta > 5000 && delta < 7300)
			{
				count = count + 3;
				//IJ.log("+3Delta################## ");
			}  
			else if(delta > 7300)
			{
				count = count + 4;
				//IJ.log("+4Delta################## ");
			}  
			else if (preimeter > 400) {
				count++;
				//IJ.log("+1Per################## ");
			}
			
				//k++;				// 
				//IJ.log("#: "+k);
				//IJ.log("#: "+k+" delta: "+delta);
				//IJ.log("#: "+k+" preimeter: "+preimeter);
				//IJ.log("real #: "+count);
			}
		}
		IJ.log("Green: "+count+" m&ms, total area: "+surface+" pixels.");
	}

	public void Blue_Blob(){
		ImagePlus inputImg = IJ.getImage(); //Load the image
		// assuming that inputImg contains the RGB input...
		ImagePlus img = new Duplicator().run(inputImg);        // make a temp image to work with
		ImageConverter iConv = new ImageConverter(img);        // this object will convert color models
		iConv.convertToHSB();       					       // converts to hsb, which is enough like HSI to serve the purpose
		ImageStack stack = img.getImageStack();                // gets the three result planes as an ImageJ "stack"
		ImageProcessor hue = stack.getProcessor(1);        // get an image processor to access the hue plane
		ImageProcessor saturation = stack.getProcessor(2);        // get an image processor to access the saturation plane
		ImageProcessor brightness = stack.getProcessor(3);        // get an image processor to access the brightness plane
		
		int imageW = inputImg.getWidth();
		int imageH = inputImg.getHeight();
		int pixelSat = 0;
		int pixelBri = 0;
		int pixelHue = 0;
		//new ImagePlus("s", saturation).show();
		//new ImagePlus("B", brightness).show();
		new ImagePlus("hue changed", hue).show();

		ImageProcessor copyHue = hue.duplicate();
		for(int h = 0; h < imageH; h++)
        {
            for(int w = 0; w < imageW; w++)
            {
				pixelBri = brightness.getPixel(w,h);
				pixelSat = saturation.getPixel(w,h);
				pixelHue = hue.getPixel(w,h);
				if(pixelHue > 150 && pixelHue < 168) 
				//if(pixelHue <140) 
				{
					copyHue.putPixel(w,h,0);
				}
				else
				{
					copyHue.putPixel(w,h,255);
				}
            }
        }
		ImagePlus imp2 = new ImagePlus("Result", copyHue);
		
			//display
		ImageProcessor hue2 = imp2.getProcessor();
		//new ImagePlus("hue binary", hue2).show();
	 
	 //blob
	 
		ManyBlobs allBlobs = new ManyBlobs(imp2); // Extended ArrayList
    	allBlobs.findConnectedComponents(); // Start the Connected Component Algorithm
		allBlobs.setBackground(1); // 0 for black, 1 for 255
		int count = 0;
		double surface = 0;
		double delta = 0;
		double connect[];
		int k = 0;
/*
		Frame frame = WindowManager.getFrame("ROI Manager");
		if (frame == null)
			IJ.run("ROI Manager...");
		frame = WindowManager.getFrame("ROI Manager");
		RoiManager roiManager = (RoiManager) frame;
*/
		for(int i = 0; i< allBlobs.size(); i++)
		{
			double convex = allBlobs.get(i).getAreaConvexHull();
			double area = allBlobs.get(i).getEnclosedArea();
			double preimeter = allBlobs.get(i).getPerimeter();
			
			if(area > 1500)
			{
				/*
				Polygon p = allBlobs.get(i).getOuterContour();
				int n = p.npoints;
				float[] x = new float[p.npoints];
				float[] y = new float[p.npoints];   
				for (int j=0; j<n; j++) {
					   x[j] = p.xpoints[j]+0.5f;
					y[j] = p.ypoints[j]+0.5f;
				}
				Roi roi = new PolygonRoi(x,y,n,Roi.TRACED_ROI);             
				Roi.setColor(Color.green);
				roiManager.add(imp2, roi, i);
*/
			count++;
			surface = surface + area;
			delta = convex - area;

			if (delta > 1000 && delta < 2600) {
				if (area > 7000) {
					count++;
					//IJ.log("+1Delta################## ");
				}
			}
			else if(delta > 2600 && delta < 5000)
			{
				count = count + 2;
				//IJ.log("+2Delta################## ");
			}
			else if(delta > 5000 && delta < 7300)
			{
				count = count + 3;
				//IJ.log("+3Delta################## ");
			}  
			else if(delta > 7300 && delta < 30000)
			{
				count = count + 4;
				//IJ.log("+4Delta################## ");
			} 
			else if(delta > 30000)
			{
				count = count + 9;
				//IJ.log("+9Delta################## ");
			} 
			else if (preimeter > 400) {
				count++;
				//IJ.log("+1Per################## ");
			}
			
				//k++;				 
				//IJ.log("#: "+k);
				//IJ.log("#: "+k+" delta: "+delta);
				//IJ.log("#: "+k+" area: "+area);
				//IJ.log("real #: "+count);
			}
		}
		
		IJ.log("BLUE: "+count+" m&ms, total area: "+surface+" pixels.");
	}

	public void run(final String arg) {
		//print();
		Red_Blob();
		Yellow_Blob();
		Green_Blob();
		Blue_Blob();
	}

}
