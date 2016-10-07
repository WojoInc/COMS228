package edu.iastate.cs228.hw1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author
 */
public class PredatorPreyTest {

    World test1;
    World test2;

    @Before
    public void setUp() throws Exception {
        test1 = new World(1);
        test2 = new World(1);
        test1.randomInit();
        test2.randomInit();
    }

    @Test
    public void updateWorld() throws Exception {
        //Test that value in
        PredatorPrey.updateWorld(test1,test2);
            assertNotEquals(test1.grid[0][0],test2.grid[0][0]);
    }

}