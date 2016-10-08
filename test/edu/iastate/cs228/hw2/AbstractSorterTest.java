package edu.iastate.cs228.hw2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 97wes on 10/7/2016.
 */
public class AbstractSorterTest {
    Point[] pts,comparePts;
    AbstractSorter sorter;

    @Before
    public void setUp() throws Exception {
        pts = new Point[]{  new Point(0,0),
                new Point(16,1),
                new Point(2,1),
                new Point(5,3)};
        comparePts = new Point[]{  new Point(0,0),
                new Point(2,1),
                new Point(16,1),
                new Point(5,3)};
         sorter = new QuickSorter(pts);
    }

    @Test
    public void draw() throws Exception {

    }

    @Test
    public void setComparator() throws Exception {
        sorter.setComparator(1);
        assertTrue(sorter.pointComparator==null&&sorter.sortByAngle==false);
        sorter.setComparator(2);
        assertTrue(sorter.pointComparator!=null&&sorter.sortByAngle==true);
    }

    @Test
    public void swap() throws Exception {
        sorter.swap(1,2);
        assertArrayEquals(comparePts,sorter.points);
    }

}