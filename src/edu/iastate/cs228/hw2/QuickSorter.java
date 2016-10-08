package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;


/**
 *  
 * @author Thomas Wesolowski
 *
 */

/**
 * 
 * This class implements the version of the quicksort algorithm presented in the lecture.   
 *
 */

public class QuickSorter extends AbstractSorter
{
	private int order;
	// Other private instance variables if you need ... 
		
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/** 
	 * Constructor accepts an input array of points. 
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts)
	{
		super(pts);
		algorithm="quicksort";
		outputFileName="quick.txt";
	}
		

	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 */
	public QuickSorter(String inputFileName) throws FileNotFoundException,InputMismatchException
	{
		super(inputFileName);
		algorithm="quicksort";
		outputFileName="quick.txt";
	}


	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 *
	 */
	@Override 
	public void sort(int order)
	{
		setComparator(order);
		this.order=order;
		quickSortRec(0,points.length-1);
		stopwatch.stop();
		sortingTime = stopwatch.getExeTime();
		stopwatch.clear();
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last)
	{
		stopwatch.start();
		if (last <= first) return;
		int j = partition(first, last);
		quickSortRec(first, j-1);
		quickSortRec(j+1, last);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first first index of the subarray
	 * @param last last index of the subarray
	 * @return the midpoint of the new subarray
	 */
	private int partition(int first, int last)
	{
		int i = first;
		int j = last + 1;
		while (order ==1) {

			// find element greater than pivot
			while (points[++i].compareTo(points[first])<0)
				if (i == last) break;

			// find element less than pivot
			while (points[first].compareTo(points[--j])<0)
				if (j == first) break;

			// check if markers cross
			if (i >= j) break;

			swap(i, j);
		}
		while (order ==2) {

			// find element greater than pivot
			while (pointComparator.compare(points[++i],points[first])<0)
				if (i == last) break;

			// find element less than pivot
			while (pointComparator.compare(points[first],points[--j])<0)
				if (j == first) break;

			// check if markers cross
			if (i >= j) break;

			swap(i, j);
		}

		// swap pivot element with element at position where markers cross
		swap(first, j);

		//return new midpoint by which to divide into sub-arrays
		return j;
	}	
		


	
	// Other private methods in case you need ...
}
