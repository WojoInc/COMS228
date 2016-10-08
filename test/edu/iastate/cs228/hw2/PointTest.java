package edu.iastate.cs228.hw2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wesolowski.thomas on 9/22/2016.
 */
public class PointTest {

    Point test, test2;
    @Before
    public void setUp() throws Exception {
        test = new Point(1,1);
        test2 = new Point(1,5);

    }

    @Test
    public void getX() throws Exception {
        assertTrue(test.getX()==1);
    }

    @Test
    public void getY() throws Exception {
        assertTrue(test.getY()==1);
    }

    @Test
    public void equals() throws Exception {
        assertTrue(!test.equals(test2));
    }

    @Test
    public void compareTo() throws Exception {
        assertTrue(test.compareTo(test2)<0);
        test2 = new Point(5,6);
        assertTrue(test.compareTo(test2)<0);
        test2 = new Point(0,0);
        assertTrue(test.compareTo(test2)>0);
    }

    @Test
    public void toString1() throws Exception {
        assertTrue(test.toString().compareTo("(1,1)")==0);
    }

}