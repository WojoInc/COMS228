package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;


/**
 *  
 * @author
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
	}
		

	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 */
	public QuickSorter(String inputFileName) throws FileNotFoundException,InputMismatchException
	{
		super(inputFileName);
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
		int i = first;
		int j = last;
		// calculate pivot number, for this implementation, the middle index is used.
		int pivot = (first+last)/2;
		// Divide into two arrays
		switch(order) {
			case 1:
				if(first>=last)break;
			while (i <= j) {
				while (points[i].compareTo(points[pivot])<0) {
					i++;
				}
				while (points[i].compareTo(points[pivot])>0) {
					j--;
				}
					swap(i++, j--);
			}
			break;
			case 2:
				while (i <= j) {
					while (pointComparator.compare(points[i],points[pivot])<0) {
						i++;
					}
					while (pointComparator.compare(points[i],points[pivot])>0) {
						j--;
					}
					if (i <= j) {
						swap(i++, j--);
					}
				}
		}
		// call recursively on sub array left of the current marker positions, then on the sub array to the right
		if (first < j)
			quickSortRec(first, j);
		if (i < last)
			quickSortRec(i, last);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last)
	{
		// TODO 
		return 0; 
	}	
		


	
	// Other private methods in case you need ...
}
