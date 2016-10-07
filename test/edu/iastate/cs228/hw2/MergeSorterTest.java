package edu.iastate.cs228.hw2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wesolowski.thomas on 10/6/2016.
 */
public class MergeSorterTest {
    @Test
    public void sort() throws Exception {
        Point[] pts = new Point[]{  new Point(16,1),
                                    new Point(2,1),
                                    new Point(5,3),
                                    new Point(0,5)};
        AbstractSorter sorter = new MergeSorter(pts);
        sorter.sort(1);
        System.out.println(sorter.toString());
    }

}