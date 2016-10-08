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
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ...
	int order;
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/** 
	 * Constructor accepts an input array of points. 
	 * in the array. 
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm="mergesort";
		outputFileName="merge.txt";
	}
	
	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 */
	public MergeSorter(String inputFileName) throws FileNotFoundException,InputMismatchException
	{
		super(inputFileName);
		algorithm="mergesort";
		outputFileName="merge.txt";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 *
	 */
	@Override 
	public void sort(int order)
	{
		switch (order){
			case 1:
				setComparator(1);
				this.order = 1;
				stopwatch.start();
				mergeSortRec(points,0,points.length-1);
				break;
			case 2:
				setComparator(2);
				this.order = 2;
				stopwatch.start();
				mergeSortRec(points,0,points.length-1);
				break;
		}
		stopwatch.stop();
		sortingTime=stopwatch.getExeTime();
		stopwatch.clear();
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts, int b, int e)
	{
		int m=0;
		if(b < e){
			m=(b+e)/2;
			mergeSortRec(pts,b,m);
			mergeSortRec(pts,m+1,e);
			merge(pts,b,m,e);

		}
		points = pts;
		
	}
	private void merge(Point[] pts, int b, int m, int e){
		Point[] temp = new Point[pts.length];
		int i,k;
		i=k=b; // first element in first half of array
		int j = m+1; //first element in second half of array

		/*
		choose correct sort method based on value of order
		Then compare both the left and right halves of the array, and
		begin combining into temp array
		 */
		if(order==1) {
			while (i <= m && j <= e) {
				if (pts[i].compareTo(pts[j]) <= 0) temp[k++] = pts[i++];
				else temp[k++] = pts[j++];
			}
		}
		if(order==2) {
			while (i <= m && j <= e) {
				if (pointComparator.compare(pts[i],pts[j]) <= 0) temp[k++] = pts[i++];
				else temp[k++] = pts[j++];
			}
		}
		/*
		Combine remaining elements into temp array
		and then copy temp array overwriting that section of pts
		with their sorted counterparts
		 */
		while(i<=m)temp[k++]=pts[i++];
		while(j<=e)temp[k++]=pts[j++];
		for (i = b; i <=e ; i++){
			pts[i] = temp[i];
		}
	}

	
	// Other private methods in case you need ...

}
