package edu.iastate.cs228.hw1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author
 */
public class LivingTest {

    int [] population,population2;
    World w;
    Badger b = new Badger(null,0,0,0);
    @Before
    public void setUp() throws Exception {
        w = new World(5);
        w.randomInit();
        population = new int[5];
        population2 = new int[5];
    }

    @Test
    public void census() throws Exception {
        w.grid[0][0].census(population2);
        boolean diff = false;
        for (int i = 0; i < population.length; i++) {
            if(population[i] != population2[i]) diff = true;
        }
        assertTrue(diff);

    }
}