package edu.iastate.cs228.hw1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author
 */
public class WorldTest {

    World world;
    @Before
    public void setUp() throws Exception {
        world = new World(3);

    }

    @Test
    public void getWidth() throws Exception {
        assertEquals(3,world.getWidth());
    }

    @Test
    public void randomInit() throws Exception {
        world.randomInit();
        assertNotNull(world.grid[0][0]);
    }

    @Test
    public void toStringT() throws Exception {
        world.randomInit();
        String test= null;
        test = world.toString();
        assertNotNull(test);
    }

}