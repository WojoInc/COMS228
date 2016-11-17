package edu.iastate.cs228.hw2;

/**
 *  
 * @author Thomas Wesolowski
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Perform the four sorting algorithms over each sequence of integers, comparing 
	 * points by x-coordinate or by polar angle with respect to the lowest point.  
	 * 
	 * @param args
	 **/

	private static int order;
	private static boolean fromFile;
	private static String filename;
	private static Scanner s = new Scanner(System.in);
	private static int numPts;
	private static Random rand = new Random();
	private static String intro = "Comparison of Four Sorting Algorithms\n" +
			"keys:  1 (random integers)  2 (File Input)  3 (exit)\n" +
			"order: 1 (by x-coordinate)  2 (by polar angle)";
	private static String statsHeader = "\nAlgorithm        size     time (ns)\n" +
										  "------------------------------------\n";
	public static void main(String[] args) {
		// 
		// Conducts multiple sorting rounds. In each round, performs the following: 
		// 
		//    a) If asked to sort random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		//    b) Reassigns to elements in the array sorters[] (declared below) the references to the 
		//       four newly created objects of SelectionSort, InsertionSort, MergeSort and QuickSort. 
		//    c) Based on the input point order, carries out the four sorting algorithms in one for 
		//       loop that iterates over the array sorters[], to sort the randomly generated points
		//       or points from an input file.  
		//    d) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 2 of the project description. 
		//
		System.out.println(intro);
		int choice1 = 0;
		int trial = 0;
		while (choice1 != 3) {
			System.out.print("\nTrial " + ++trial + ": ");
			while (!(choice1 > 0 && choice1 < 4)) choice1 = s.nextInt();
			if (choice1 == 1) {
				System.out.print("\nEnter number of random points: ");
				while (numPts < 1) numPts = s.nextInt();

			} else if (choice1 == 2) {
				System.out.print("\nPoints from a file");
				System.out.print("\nFile name: ");
				filename = s.next();

			} else break;

			System.out.print("Order used in sorting: ");
			while (order < 1) order = s.nextInt();
			//skip two lines to make things look pretty
			System.out.println();
			System.out.println();
			System.out.print(statsHeader);

			AbstractSorter[] sorters = new AbstractSorter[4];
			//initialize sorters
			try {
				if (choice1==2) {
					sorters[0] = new QuickSorter(filename);
					sorters[1] = new MergeSorter(filename);
					sorters[2] = new SelectionSorter(filename);
					sorters[3] = new InsertionSorter(filename);
				} else if(choice1 == 1) {
					sorters[0] = new QuickSorter(generateRandomPoints(numPts, rand));
					sorters[1] = new MergeSorter(generateRandomPoints(numPts, rand));
					sorters[2] = new SelectionSorter(generateRandomPoints(numPts, rand));
					sorters[3] = new InsertionSorter(generateRandomPoints(numPts, rand));
				}
				//run sorter rounds
				for (AbstractSorter sorter :
						sorters) {
					sorter.sort(order);
					sorter.draw();
					System.out.println(sorter.stats());
					sorter.writeToFile();
				}
			} catch (FileNotFoundException ex) {
				System.out.println("Could not find file! Please try again.");
			}
			// Within a sorting round, have each sorter object call the sort and draw() methods
			// in the AbstractSorter class.  You can visualize the result of each sort. (Windows
			// have to be closed manually before rerun.)  Also, print out the statistics table
			// (cf. Section 2).

			choice1 = 0;
			numPts = 0;
			filename = "";
			order = 0;
		}

	}
	
	
	/**
	 * This method generates a given number of random points to initialize randomPoints[].
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{
		if(numPts <1) throw new IllegalArgumentException("Enter number of points greater than or equal to 1");
		Point[] points = new Point[numPts];
		for (int i = 0; i < numPts; i++) {
			points[i]=new Point(rand.nextInt(101)-50,rand.nextInt(101)-50);
		}
		return points;
	}
}
