package edu.iastate.cs228.hw2;

/**
 *  
 * @author Thomas Wesolowski
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort, and QuickSort.
 * It stores the input (later the sorted) sequence and records the employed sorting algorithm, 
 * the comparison method, and the time spent on sorting. 
 *
 */


public abstract class AbstractSorter
{
	
	protected Point[] points;    // Array of points operated on by a sorting algorithm. 
	                             // The number of points is given by points.length.
	
	protected String algorithm = null; // "selection sort", "insertion sort", "mergesort", or
	                                   // "quicksort". Initialized by a subclass. 
									   // constructor.
	protected boolean sortByAngle;     // true if the last sorting was done by polar angle and  
									   // false if by x-coordinate 
	
	protected String outputFileName;   // "select.txt", "insert.txt", "merge.txt", or "quick.txt"
	
	protected long sortingTime; 	   // execution time in nanoseconds. 
	 
	protected Comparator<Point> pointComparator;  // comparator which compares polar angle if 
												  // sortByAngle == true and x-coordinate if 
												  // sortByAngle == false 
	
	private Point lowestPoint; 	    // lowest point in the array, or in case of a tie, the
									// leftmost of the lowest points. This point is used 
									// as the reference point for polar angle based comparison.


	protected Stopwatch stopwatch = new Stopwatch();
	private String statsMask="                                    ";
	// Add other protected or private instance variables you may need. 
	
	protected AbstractSorter()
	{
		// No implementation needed. Provides a default super constructor to subclasses. 
		// Removable after implementing SelectionSorter, InsertionSorter, MergeSorter, and QuickSorter.
	}
	
	
	/**
	 * This constructor accepts an array of points as input. Copy the points into the array points[]. 
	 * Sets the instance variable lowestPoint.
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException
	{
		if(pts.length==0||pts==null) throw new IllegalArgumentException();
		points = pts.clone();

		for (Point p :
				points) {
			if (lowestPoint==null || lowestPoint.compareTo(p) > 0)lowestPoint=p;
		}
	}

	
	/**
	 * This constructor reads points from a file. Sets the instance variables lowestPoint and 
	 * outputFileName.
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	protected AbstractSorter(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		ArrayList<Point> temp = new ArrayList<>();
		File f = new File(inputFileName);
		Scanner s = new Scanner(f);
		while(s.hasNextInt()){
			try{
				temp.add(new Point(s.nextInt(),s.nextInt()));

			}catch(NullPointerException ex){
				throw new InputMismatchException();
			}
		}
		temp.toArray(points);
		for (Point p :
				points) {
			if (lowestPoint==null || lowestPoint.compareTo(p) > 0)lowestPoint=p;
		}
	}
	

	/**
	 * Sorts the elements in points[]. 
	 * 
	 *     a) in the non-decreasing order of x-coordinate if order == 1
	 *     b) in the non-decreasing order of polar angle w.e.t. lowestPoint if order == 2
	 *        (lowestPoint will be at index 0 after sorting)
	 * 
	 * Sets the instance variable sortByAngle based on the value of order. Calls the method 
	 * setComparator() to set the variable pointComparator and use it in sorting.    
	 * Records the sorting time (in nanoseconds) using the System.nanoTime() method. 
	 * (Assign the time spent to the variable sortingTime.)  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle w.e.t lowestPoint
	 *
	 * @throws IllegalArgumentException if order is less than 1 or greater than 2
	 */
	public abstract void sort(int order) throws IllegalArgumentException; 
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		algorithm = algorithm+ statsMask.substring(0,17-algorithm.length());
		String size = points.length + statsMask.substring(0,9-(new String(""+points.length).length()));
		return algorithm + size + sortingTime;
	}
	
	
	/**
	 * Write points[] to a string.  When printed, the points will appear in order of increasing
	 * index with every point occupying a separate line.  The x and y coordinates of the point are 
	 * displayed on the same line with exactly one blank space in between. 
	 */
	@Override
	public String toString()
	{
		String output = "";
		for (Point p :
				points) {
			output = output + "\n" + p.getX() + " " + p.getY();
		}
		return output;
	}

	/**
	 * This method is called after sorting for visually check whether the result is correct.  You  
	 * just need to generate a list of points and a list of segments, depending on the value of 
	 * sortByAngle, as detailed in Section 4.1. Then create a Plot object to call the method myFrame().  
	 */
	public void draw()
	{
		// Based on Section 4.1, generate the line segments to draw for display of the sorting result.

		/*
			Implemented arraylist which is casted to segments[] later for simplicity and shorter code
			by avoiding unnecessary calculation of number of segments in order to specify array dimensions
			for segments[]
		 */
		ArrayList<Segment> segList = new ArrayList<>();
		if(!sortByAngle){
			for (int i = 0; i < points.length-1; i++) {
				segList.add(new Segment(points[i],points[i+1]));
			}
		}
		else {
			for (int i = 1; i < points.length; i++) {
				segList.add(new Segment(points[0],points[i]));
			}
		}
		Segment[] segments = new Segment[segList.size()];
		segList.toArray(segments);

		// The following statement creates a window to display the sorting result.
		Plot.myFrame(points,segments, getClass().getName());
		
	}
		
	/**
	 * Generates a comparator on the fly that compares by polar angle if sortByAngle == true
	 * and by x-coordinate if sortByAngle == false. Set the protected variable pointComparator
	 * to it. Need to create an object of the PolarAngleComparator class and call the compareTo() 
	 * method in the Point class, respectively for the two possible values of sortByAngle.  
	 * 
	 * @param order
	 */
	protected void setComparator(int order) 
	{
		switch(order){
			case 1:
				sortByAngle=false;
				break;
			case 2:
				pointComparator = new PolarAngleComparator(lowestPoint);
				sortByAngle=true;
				break;
		}

	}

	
	/**
	 * Swap the two elements indexed at i and j respectively in the array points[]. 
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j)
	{
		Point tmp = points[i];
		points[i] = points[j];
		points[j] = tmp;
	}
	protected void writeToFile(){
		File f = new File(outputFileName);
		try {
			PrintWriter w = new PrintWriter(f);
			w.write(this.toString());
			w.close();
		} catch (IOException e) {
			System.out.println("Could not output results to file!");
		}
	}
}
