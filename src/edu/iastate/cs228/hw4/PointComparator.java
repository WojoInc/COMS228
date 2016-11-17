package edu.iastate.cs228.hw4;

import java.util.Comparator;

/**
 * edu.iastate.cs228.hw4 : PointComparator
 * <p>
 * Created by Thomas John Wesolowski on 11/16/2016.
 * Contact email: wojoinc@gmail.com
 * GitHub username: WojoInc
 */
public class PointComparator implements Comparator<Point>{
    @Override
    public int compare(Point p1, Point p2) {
        return p1.compareTo(p2);
    }
}
