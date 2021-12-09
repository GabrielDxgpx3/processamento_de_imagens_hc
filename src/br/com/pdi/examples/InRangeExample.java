package br.com.pdi.examples;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import br.com.pdi.util.ImShow;

public class InRangeExample {
	
	static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		
		Mat source = Imgcodecs.imread("resources/images/oranges.jfif");
		Mat result = Imgcodecs.imread("resources/images/oranges.jfif");
		
		Mat hsv = new Mat();
		Imgproc.cvtColor(source, hsv, Imgproc.COLOR_BGR2HSV);
		
		Mat gray = new Mat();
        Imgproc.cvtColor(hsv, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.medianBlur(gray, gray, 17);
        
        Mat bw = new Mat();
        Imgproc.threshold(gray, bw, 190.0, 255.0, Imgproc.THRESH_BINARY);
        
		Mat circles = new Mat();
		Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1, 40, 230.0, 13.3, 1, 75);
		
		
		for (int x = 0; x < circles.cols(); x++) {
			
			double[] c = circles.get(0, x);
			Point center = new Point(Math.round(c[0]), Math.round(c[1]));
		
			Imgproc.circle(result, center, 1, new Scalar(0, 100, 100), 3, 8, 0);
		
			int radius = (int) Math.round(c[2]);
			Imgproc.circle(result, center, radius, new Scalar(255, 0, 255), 3, 8, 0);
		}
	    
		new ImShow("ORIGINAL - 1").showImage(source);
		new ImShow("HSV - 2").showImage(hsv);
		new ImShow("GRAY - 3").showImage(gray);
		new ImShow("B&G - 4").showImage(bw);
		new ImShow("RESULT - 5").showImage(result);
	}
}
