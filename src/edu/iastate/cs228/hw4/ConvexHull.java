package edu.iastate.cs228.hw4;

/**
 *  
 * @author
 *
 */

import edu.iastate.cs228.hw2.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.InputMismatchException; 
import java.io.PrintWriter;
import java.util.Random; 
import java.util.Scanner;



/**
 * 
 * This class implements construction of the convex hull of a finite set of points. 
 *
 */

public abstract class ConvexHull 
{
	// ---------------
	// Data Structures 
	// ---------------
	protected String algorithm;  // has value either "Graham's scan" or "Jarvis' march". Initialized by a subclass.
	
	/**
	 * The array points[] holds an input set of Points, which may be randomly generated or 
	 * input from a file.  Duplicates are possible. 
	 */
	private Point[] points;    
	

	/**
	 * Lowest point from points[]; and in case of a tie, the leftmost one of all such points. 
	 * To be set by a constructor. 
	 */
	protected Point lowestPoint; 

	
	/**
	 * This array stores the same set of points from points[] with all duplicates removed. 
	 * These are the points on which Graham's scan and Jarvis' march will be performed. 
	 */
	protected Point[] pointsNoDuplicate; 
	
	
	/**
	 * Vertices of the convex hull in counterclockwise order are stored in the array 
	 * hullVertices[], with hullVertices[0] storing lowestPoint. 
	 */
	protected Point[] hullVertices;
	
	
	protected QuickSortPoints quicksorter;  // used (and reset) by this class and its subclass GrahamScan

    private String statsMask="                                    ";
    protected Stopwatch stopwatch = new Stopwatch();
    protected long sortingTime; 	   // execution time in nanoseconds.
	
	// ------------
	// Constructors
	// ------------
	
	
	/**
	 * Constructor over an array of points.  
	 * 
	 *    1) Store the points in the private array points[].
	 *    
	 *    2) Initialize quicksorter. 
	 *    
	 *    3) Call removeDuplicates() to store distinct points from the input in pointsNoDuplicate[].
	 *    
	 *    4) Set lowestPoint to pointsNoDuplicate[0]. 
	 * 
	 * @param pts
	 * @throws IllegalArgumentException  if pts.length == 0
	 */
	public ConvexHull(Point[] pts) throws IllegalArgumentException 
	{
        if(pts.length==0) throw new IllegalArgumentException("Array is empty!");
		points = pts;
        quicksorter = new QuickSortPoints(points);
        removeDuplicates();
        lowestPoint = pointsNoDuplicate[0];
	}
	
	/**
	 * Read integers from an input file.  Every pair of integers represent the x- and y-coordinates 
	 * of a point.  Generate the points and store them in the private array points[]. The total 
	 * number of integers in the file must be even.
	 * 
	 * You may declare a Scanner object and call its methods such as hasNext(), hasNextInt() 
	 * and nextInt(). An ArrayList may be used to store the input integers as they are read in 
	 * from the file.  
	 * 
	 * Perform the operations 1)-4) for the first constructor. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public ConvexHull(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
        File f = new File(inputFileName);
        Scanner s = new Scanner(f);
        ArrayList<Point> pts = new ArrayList<>();
        int x,y;
        while (s.hasNextInt()){
            x = s.nextInt();
            y = s.nextInt();
            pts.add(new Point(x,y));
        }
        points = new Point[pts.size()];
        pts.toArray(points);
        quicksorter = new QuickSortPoints(points);
        removeDuplicates();
        lowestPoint = pointsNoDuplicate[0];
	}

	
	/**
	 * Construct the convex hull of the points in the array pointsNoDuplicate[]. 
	 */
	public abstract void constructHull(); 

	
		
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <convex hull algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * Graham's scan   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 4 of the project description. 
	 */
	public String stats()
	{
        algorithm = algorithm+ statsMask.substring(0,17-algorithm.length());
        String size = points.length + statsMask.substring(0,9-(new String(""+points.length).length()));
        return algorithm + size + sortingTime;
	}
	
	
	/**
	 * The string displays the convex hull with vertices in counterclockwise order starting at  
	 * lowestPoint.  When printed out, it will list five points per line with three blanks in 
	 * between. Every point appears in the format "(x, y)".  
	 * 
	 * For illustration, the convex hull example in the project description will have its 
	 * toString() generate the output below: 
	 * 
	 * (-7, -10)   (0, -10)   (10, 5)   (0, 8)   (-10, 0)   
	 * 
	 * lowestPoint is listed only ONCE.  
	 */
	public String toString()
	{
		String output = "";
        for (int i = 0; i < hullVertices.length; i++) {
            for (int j = 0; j < 5; j++) {
                output = output + hullVertices[i++] + "   ";
            }
            output = output + "\n";
        }
        return output;
    }
	
	
	/** 
	 * 
	 * Writes to the file "hull.txt" the vertices of the constructed convex hull in counterclockwise 
	 * order.  These vertices are in the array hullVertices[], starting with lowestPoint.  Every line
	 * in the file displays the x and y coordinates of only one point.  
	 * 
	 * For instance, the file "hull.txt" generated for the convex hull example in the project 
	 * description will have the following content: 
	 * 
     *  -7 -10 
     *  0 -10
     *  10 5
     *  0  8
     *  -10 0
	 * 
	 * The generated file is useful for debugging as well as grading. 
	 * 
	 * Called only after constructHull().  
	 * 
	 * 
	 * @throws IllegalStateException  if hullVertices[] has not been populated (i.e., the convex 
	 *                                   hull has not been constructed)
	 */
	public void writeHullToFile() throws IllegalStateException 
	{
        if(hullVertices.length==0) throw new IllegalStateException("Hull not constructed");

        File f = new File("hull.txt");
        try {
            PrintWriter pw = new PrintWriter(f);
            for (Point p : hullVertices) {
                pw.write(p.toString() + "\n");
            }

        }
        catch(FileNotFoundException ex){
            System.out.println("Could not create output file!");
    }
	}
	

	/**
	 * Draw the points and their convex hull.  This method is called after construction of the 
	 * convex hull.  You just need to make use of hullVertices[] to generate a list of segments 
	 * as the edges. Then create a Plot object to call the method myFrame().  
	 */
	public void draw()
	{
		// Based on Section 4.1, generate the line segments to draw for display of the convex hull.
		// Assign their number to numSegs, and store them in segments[] in the order. 

		/*
			Implemented arraylist which is casted to segments[] later for simplicity and shorter code
			by avoiding unnecessary calculation of number of segments in order to specify array dimensions
			for segments[]
		 */
        ArrayList<Segment> segList = new ArrayList<>();
            for (int i = 0; i < hullVertices.length-1; i++) {
                segList.add(new Segment(hullVertices[i],hullVertices[i+1]));
            }
            segList.add(new Segment(hullVertices[hullVertices.length-1],hullVertices[0]));

        Segment[] segments = new Segment[segList.size()];
        segList.toArray(segments);
		
		// The following statement creates a window to display the convex hull.
		Plot.myFrame(pointsNoDuplicate, segments, getClass().getName());
		
	}

		
	/**
	 * Sort the array points[] by y-coordinate in the non-decreasing order.  Have quicksorter 
	 * invoke quicksort() with a comparator object which uses the compareTo() method of the Point 
	 * class. Copy the sorted sequence onto the array pointsNoDuplicate[] with duplicates removed.
	 *     
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void removeDuplicates()
	{
        quicksorter.quickSort(new PointComparator());
        quicksorter.getSortedPoints(points);
        ArrayBasedStack<Point> temp = new ArrayBasedStack<>();
        int j = 0;
        for (int i = 0; i < points.length; i++) {
            if(temp.isEmpty()) temp.push(points[i]);
            else if(points[i].compareTo(temp.peek())!=0) temp.push(points[i]);
        }
        int stackHeight = temp.size();
        pointsNoDuplicate = new Point[stackHeight];
        for (int i = stackHeight-1; i >= 0; i--) {
            pointsNoDuplicate[i] = temp.pop();
        }
    }
}
