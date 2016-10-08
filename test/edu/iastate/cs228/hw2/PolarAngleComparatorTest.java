package edu.iastate.cs228.hw2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 97wes on 10/7/2016.
 */
public class PolarAngleComparatorTest {
    Point ref,p1,p2;
    PolarAngleComparator comparator;
    @Before
    public void setUp() throws Exception {
        ref= new Point(0,0);
        p1 = new Point(1,6);
        p2 = new Point(6,1);
        comparator = new PolarAngleComparator(ref);

    }

    @Test
    public void compare() throws Exception {
        assertTrue(p1.compareTo(p2)<0);
        assertTrue(comparator.compare(ref,p1)<0);
        assertTrue(comparator.compare(p1,p1) == 0);
        assertTrue(comparator.compare(p2,p1) < 0);
        assertTrue(comparator.compare(p1,p2) > 0);
        p1 = new Point(2,2);
        p2 = new Point(4,4);
        assertTrue(comparator.compare(p1,p2) <0);


    }

}