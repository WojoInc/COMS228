package edu.iastate.cs228.hw4;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.ArrayList; 

public class GrahamScan extends ConvexHull
{
	/**
	 * Stack used by Grahma's scan to store the vertices of the convex hull of the points 
	 * scanned so far.  At the end of the scan, it stores the hull vertices in the 
	 * counterclockwise order. 
	 */
	private PureStack<Point> vertexStack;  


	/**
	 * Call corresponding constructor of the super class.  Initialize the variables algorithm 
	 * (from the class ConvexHull) and vertexStack. 
	 *
	 * @throws IllegalArgumentException  if pts.length == 0
	 */
	public GrahamScan(Point[] pts) throws IllegalArgumentException 
	{
		super(pts); 
		vertexStack = new ArrayBasedStack<>();
		algorithm = "Graham Scan";
	}
	

	/**
	 * Call corresponding constructor of the super class.  Initialize algorithm and vertexStack.  
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public GrahamScan(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		super(inputFileName); 
		algorithm = "Graham Scan";
        vertexStack = new ArrayBasedStack<>();
	}

	
	// -------------
	// Graham's scan
	// -------------
	
	/**
	 * This method carries out Graham's scan in several steps below: 
	 * 
	 *     1) Call the private method setUpScan() to sort all the points in the array 
	 *        pointsNoDuplicate[] by polar angle with respect to lowestPoint.    
	 *        
	 *     2) Perform Graham's scan. To initialize the scan, push pointsNoDuplicate[0] and 
	 *        pointsNoDuplicate[1] onto vertexStack.  
	 * 
     *     3) As the scan terminates, vertexStack holds the vertices of the convex hull.  Pop the 
     *        vertices out of the stack and add them to the array hullVertices[], starting at index
     *        vertexStack.size() - 1, and decreasing the index toward 0.    
     *        
     * Two degenerate cases below must be handled: 
     * 
     *     1) The array pointsNoDuplicates[] contains just one point, in which case the convex
     *        hull is the point itself. 
     *     
     *     2) The array contains only two points, in which case the hull is the line segment 
     *        connecting them.   
	 */
	public void constructHull()
	{
        stopwatch.start();
		if(pointsNoDuplicate.length==1){
            hullVertices = new Point[1];
            hullVertices[0] = pointsNoDuplicate[0];
            return;
        }
        if(pointsNoDuplicate.length==2){
            hullVertices = new Point[2];
            hullVertices[0] = pointsNoDuplicate[0];
            hullVertices[1] = pointsNoDuplicate[1];
            return;
        }

        setUpScan();

        vertexStack.push(pointsNoDuplicate[0]);
        vertexStack.push(pointsNoDuplicate[1]);
        vertexStack.push(pointsNoDuplicate[2]);
        for (int i = 3; i < pointsNoDuplicate.length; i++) {
            while (direction(getBelowTop(),vertexStack.peek(),pointsNoDuplicate[i])!=1)
            {
                vertexStack.pop();
            }
            vertexStack.push(pointsNoDuplicate[i]);
        }
        hullVertices = new Point[vertexStack.size()];
        for (int i = vertexStack.size()-1; i >=0 ; i--) {
            hullVertices[i] = vertexStack.pop();
        }
        stopwatch.stop();
        sortingTime = stopwatch.getExeTime();
    }
	
	
	/**
	 * Set the variable quicksorter from the class ConvexHull to sort by polar angle with respect 
	 * to lowestPoint, and call quickSort() from the QuickSortPoints class on pointsNoDuplicate[].
	 * The argument supplied to quickSort() is an object created by the constructor call 
	 * PolarAngleComparator(lowestPoint, true).       
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 *
	 */
	public void setUpScan()
	{
        quicksorter = new QuickSortPoints(pointsNoDuplicate);
        quicksorter.quickSort(new PolarAngleComparator(lowestPoint, true));
        quicksorter.getSortedPoints(pointsNoDuplicate);
    }

    /**
     * Determine direction of 3 successive points to determine if the points follow a clockwise, straight, or counter
     * clockwise orientation.
     * @param p
     * @param q
     * @param r
     * @return
     */
    private int direction(Point p, Point q, Point r)
    {
        int val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) return 0;  // straight line
        return (val > 0)? -1: 1;  // clockwise or counter clockwise
    }

    /**
     * Returns the second element of vertexStack. Used in constructHull().
     * @return second element in stack
     */
    private Point getBelowTop(){
        Point top = vertexStack.pop();
        Point second = vertexStack.peek();
        vertexStack.push(top);
        return second;
    }

}
