package edu.iastate.cs228.hw4;

import java.util.Arrays;
import java.util.Comparator;

/**
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
        if(last<=first) return;
        int pivot = first;
        int index = partition(first, last, comp);
        quickSortRec(first, index, comp);
        quickSortRec(index+1, last, comp);
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
        int i = first;
        int j = last + 1;
		while(first < last) {
            // find element greater than pivot
            while (comp.compare(points[++i],points[first])<0)
                if (i == last) break;

            // find element less than pivot
            while (comp.compare(points[first],points[--j])<0)
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


