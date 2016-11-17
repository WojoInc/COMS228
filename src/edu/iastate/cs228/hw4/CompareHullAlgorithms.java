package edu.iastate.cs228.hw4;

/**
 *  
 * @author
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
import java.util.Scanner; 
import java.util.Random; 


public class CompareHullAlgorithms 
{
	private static int order;
	private static boolean fromFile;
	private static String filename;
	private static Scanner s = new Scanner(System.in);
	private static int numPts;
	private static Random rand = new Random();
	private static String intro = "Comparison of Four Sorting Algorithms\n" +
			"keys:  1 (random integers)  2 (File Input)  3 (exit)\n";
	private static String statsHeader = "\nAlgorithm        size     time (ns)\n" +
			"------------------------------------\n";
	/**
	 * Repeatedly take points either randomly generated or read from files. Perform Graham's scan and 
	 * Jarvis' march over the input set of points, comparing their performances.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) 
	{		
		// TODO 
		// 
		// Conducts multiple rounds of convex hull construction. Within each round, performs the following: 
		// 
		//    1) If the input are random points, calls generateRandomPoints() to initialize an array 
		//       pts[] of random points. Use pts[] to create two objects of GrahamScan and JarvisMarch, 
		//       respectively.
		//
		//    2) If the input is from a file, construct two objects of the classes GrahamScan and  
		//       JarvisMarch, respectively, using the file.     
		//
		//    3) Have each object call constructHull() to build the convex hull of the input points.
		//
		//    4) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 4 of the project description. 
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

			//skip two lines to make things look pretty
			System.out.println();
			System.out.println();
			System.out.print(statsHeader);

		ConvexHull[] algorithms = new ConvexHull[2]; 
		
		// Within a hull construction round, have each algorithm call the constructHull() and draw()
		// methods in the ConvexHull class.  You can visually check the result. (Windows 
		// have to be closed manually before rerun.)  Also, print out the statistics table 
		// (see Section 4).

		//initialize hulls
		try {
			if(choice1 == 1) {
				algorithms[0] = new GrahamScan(generateRandomPoints(numPts, rand));
				algorithms[1] = new JarvisMarch(generateRandomPoints(numPts, rand));
			}
			else if (choice1==2) {
				algorithms[0] = new GrahamScan(filename);
				algorithms[1] = new JarvisMarch(filename);
			}

			//run sorter rounds
			for (ConvexHull method : algorithms) {
				method.constructHull();
				method.draw();
				System.out.println(method.stats());
				method.writeHullToFile();
			}

		} catch (FileNotFoundException ex) {
			System.out.println("Could not find file! Please try again.");
		}

		//reset menu settings for next trial
		choice1 = 0;
		numPts = 0;
		filename = "";
		order = 0;
	}
		
	}
	
	
	/**
	 * This method generates a given number of random points.  The coordinates of these points are 
	 * pseudo-random numbers within the range [-50,50] � [-50,50]. 
	 * 
	 * Reuse your implementation of this method in the CompareSorter class from Project 2.
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{
		if(numPts <1) throw new IllegalArgumentException("Enter number of points greater than or equal to 1");
		Point[] points = new Point[numPts];
		for (int i = 0; i < numPts; i++) {
			points[i]=new Point(rand.nextInt(101)-50,rand.nextInt(101)-50);
		}
		return points;
	}
}
