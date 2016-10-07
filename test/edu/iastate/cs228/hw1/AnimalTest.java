package edu.iastate.cs228.hw1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author
 */
public class AnimalTest {

    Badger b;

    @Before
    public void setUp() throws Exception {
        b = new Badger(null,0,0,3);

    }

    @Test
    public void myAge() throws Exception {
        assertEquals(3,b.myAge());
    }
}