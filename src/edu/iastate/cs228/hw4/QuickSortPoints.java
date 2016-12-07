package edu.iastate.cs228.hw4;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Thomas Wesolowski
 * This class sorts an array of Point objects using a provided Comparator.  You may 
 * modify your implementation of quicksort from Project 2.  
 */

public class QuickSortPoints
{
	private Point[] points;  	// Array of points to be sorted.

	/**
	 * Constructor takes an array of Point objects. 
	 * 
	 * @param pts
	 */
	QuickSortPoints(Point[] pts)
	{
		points = pts;
	}
	
	
	/**
	 * Copy the sorted array to pts[]. 
	 * 
	 * @param pts  array to copy onto
	 */
	void getSortedPoints(Point[] pts)
	{
		pts = points;
	}

	
	/**
	 * Perform quicksort on the array points[] with a supplied comparator. 
	 *
	 * @param comp
	 */
	public void quickSort(Comparator<Point> comp)
	{
		quickSortRec(0,points.length-1,comp);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last, Comparator<Point> comp)
	{
		int index = partition(first,last,comp);
		if (first < index - 1)
			quickSortRec(first, index - 1, comp);
		if (index < last)
			quickSortRec(index, last, comp);
	}

	/**
	 * Operates on the subarray of points[] with indices between first and right.
	 *
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last, Comparator<Point> comp)
	{
		int i = first, j = last;
		int tmp;
		Point pivot = points[(first + last) / 2];

		while (i <= j) {
			while (comp.compare(points[i], pivot)<0)
				i++;
			while (comp.compare(points[j], pivot)>0)
				j--;
			if (i <= j) {
				swap(i,j);
				i++;
				j--;
			}
		}

		return i;
	}

	/**
	 * Swap the two elements indexed at i and j respectively in the array points[].
	 *
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j)
	{
		edu.iastate.cs228.hw4.Point tmp = points[i];
		points[i] = points[j];
		points[j] = tmp;
	}
}


