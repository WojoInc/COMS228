package edu.iastate.cs228.hw4;

import edu.iastate.cs228.hw4.Point;

/**
 * 
 * Fully implemented class to represent a line segment for drawing. Used by the class Plot and the 
 * draw() method in the class AbstractSorter.
 *
 */
public class Segment 
{
	private edu.iastate.cs228.hw4.Point p;
	private edu.iastate.cs228.hw4.Point q;

	public Segment(edu.iastate.cs228.hw4.Point p0, edu.iastate.cs228.hw4.Point q0)
	{
		p = new edu.iastate.cs228.hw4.Point(p0);
		q = new edu.iastate.cs228.hw4.Point(q0);
	}

	public edu.iastate.cs228.hw4.Point getP()
	{
		return p;
	}

	public Point getQ()
	{
		return q; 
	}
}
