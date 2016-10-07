package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;


/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements insertion sort.   
 *
 */

public class InsertionSorter extends AbstractSorter 
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
	public InsertionSorter(Point[] pts) 
	{

		super(pts);
	}	

	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 */
	public InsertionSorter(String inputFileName) throws FileNotFoundException,InputMismatchException
	{
		super(inputFileName);
	}
	
	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 */
	@Override 
	public void sort(int order)
	{
		int j;
		boolean end;
		switch (order){
			case 1:
				setComparator(1);
				stopwatch.start();
				for (int i = 1; i < points.length; i++) {
					j=i;
					end=false;
					//uses j and j-1 as a walker variable to walk down the array
					while(!end){
						if(points[j].compareTo(points[j-1])<0 )swap(j,j-1);
						if((j-1)<=0)end=true;
						j--;
					}
				}
				break;
			case 2:
				setComparator(2);
				stopwatch.start();
				for (int i = 1; i < points.length; i++) {
					j=i;
					end=false;
					//uses j and j-1 as a walker variable to walk down the array
					while(!end){
						if(pointComparator.compare(points[j],points[j-1])<0 )swap(j,j-1);
						if((j-1)<=0)end=true;
						j--;
					}
				}
				break;
		}

		stopwatch.stop();
		sortingTime = stopwatch.getExeTime();
		stopwatch.clear();
	}		
}
