package edu.iastate.cs228.hw1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author
 */
public class GrassTest {
    private World world;
    private Grass grass;

    private String[][] worldSeed ={ {"G  ","R0 ","R0 ","G  "},
                                    {"R0 ","R0 ","G  ","G  "},
                                    {"G  ","R0 ","G  ","G  "},
                                    {"G  ","R0 ","G  ","G "}};

    public void setWorld(String [][] testworld, int width) {
        this.world = new World(width);
        world.randomInit();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {

                switch (testworld[i][j].charAt(0)) {
                    case 'B':
                        world.grid[i][j] = new Badger(world, i, j, testworld[i][j].charAt(1)-0x30); // subtract 0x30 ASCII offset
                        break;
                    case 'F':
                        world.grid[i][j] = new Fox(world, i, j, testworld[i][j].charAt(1)-0x30); // subtract 0x30 ASCII offset
                        break;
                    case 'G':
                        world.grid[i][j] = new Grass(world, i, j);
                        break;
                    case 'E':
                        world.grid[i][j] = new Empty(world, i, j);
                        break;
                    case 'R':
                        world.grid[i][j] = new Rabbit(world, i, j, testworld[i][j].charAt(1)-0x30); // subtract 0x30 ASCII offset
                        break;
                }
            }
        }
    }

    @Test
    public void who() throws Exception {
        grass = new Grass(null,0,0);
        assertEquals(State.GRASS, grass.who());
    }

    @Test
    /**
     * Tests each of the survival rules for Grass
     */
    public void next() throws Exception {

        //Rule A:
        setWorld(worldSeed,2);
        assertEquals(State.EMPTY,world.grid[0][0].next(null).who());
        //Rule B:
        setWorld(worldSeed,4);
        assertEquals(State.RABBIT,world.grid[2][2].next(null).who());
        //Rule C:
        setWorld(worldSeed,4);
        assertEquals(State.GRASS,world.grid[3][0].next(null).who());
    }
}