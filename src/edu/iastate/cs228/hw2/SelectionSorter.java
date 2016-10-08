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
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/**
	 * Constructor takes an array of points.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts);
		algorithm="selection sort";
		outputFileName="select.txt";
	}	

	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 */
	public SelectionSorter(String inputFileName) throws InputMismatchException,FileNotFoundException
	{
		super(inputFileName);
		algorithm="selection sort";
		outputFileName="select.txt";
	}
	
	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 *
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 *
	 */
	@Override 
	public void sort(int order)
	{
		int j=0;
		int k=0;
		int lowest;
		switch (order){
			case 1:
				setComparator(1);
				lowest=0;
				stopwatch.start();
				//set lowest point equal to the first point in the array for first iteration
				while(k<points.length){
					for (int i = j; i < points.length; i++) {
						if (points[i].compareTo(points[lowest])<0)lowest =i;
					}
					//if lowest point is not already in the proper position, swap it
					if (lowest!=j)swap(lowest,j++);
					k++;
				}
				break;
			case 2:
				setComparator(2);
				lowest=0;
				stopwatch.start();
				//set lowest point equal to the first point in the array for first iteration
				while(k<points.length){
					for (int i = j; i < points.length; i++) {
						if (pointComparator.compare(points[i],points[lowest])<0)lowest =i;
					}
					//if lowest point is not already in the proper position, swap it
					if (lowest!=j)swap(lowest,j++);
					k++;
				}
				break;

		}
		stopwatch.stop();
		sortingTime = stopwatch.getExeTime();
		stopwatch.clear();
	}
}
