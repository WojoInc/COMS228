package edu.iastate.cs228.hw2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wesolowski.thomas on 10/6/2016.
 */
public class QuickSorterTest {
    @Test
    public void sort() throws Exception {
        Point[] pts = new Point[]{new Point(16, 1),
                new Point(2, 1),
                new Point(5, 3),
                new Point(0, 0)};
        Point[] comparePts = new Point[]{new Point(0, 0),
                new Point(2, 1),
                new Point(5, 3),
                new Point(16, 1)};
        AbstractSorter sorter = new QuickSorter(pts);
        sorter.sort(1);
        System.out.println(sorter.toString());
        assertArrayEquals(comparePts, sorter.points);
    }

    @Test
    public void sort2() throws Exception {
        Point[] pts = new Point[]{new Point(16, 1),
                new Point(2, 1),
                new Point(5, 3),
                new Point(-1, -1)};
        Point[] comparePts = new Point[]{  new Point(-1,-1),
                new Point(16,1),
                new Point(2,1),
                new Point(5,3)};
        AbstractSorter sorter = new QuickSorter("C:\\Users\\97wes\\IdeaProjects\\COMS228\\src\\edu\\iastate\\cs228\\hw4\\test.txt");
        sorter.sort(2);
        System.out.println(sorter.toString());
        assertArrayEquals(comparePts, sorter.points);
    }
}