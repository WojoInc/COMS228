package edu.iastate.cs228.hw1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author
 */
public class EmptyTest {
    private World world;
    private Empty empty;

    private String[][] worldSeed ={ {"E  ","R0 ","F0 ","E  "},
                                    {"R0 ","R0 ","E  ","F0 "},
                                    {"B2 ","G  ","E  ","E  "},
                                    {"E  ","B2 ","E  ","E  "}};

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
        empty = new Empty(null,0,0);
        assertEquals(State.EMPTY, empty.who());
    }

    @Test
    /**
     * Tests each of the survival rules for Grass
     */
    public void next() throws Exception {

        //Rule A:
        setWorld(worldSeed,2);
        assertEquals(State.RABBIT,world.grid[0][0].next(null).who());
        //Rule B:
        setWorld(worldSeed,4);
        assertEquals(State.FOX,world.grid[0][3].next(null).who());
        //Rule C:
        setWorld(worldSeed,4);
        assertEquals(State.BADGER,world.grid[3][0].next(null).who());
        //Rule D:
        setWorld(worldSeed,3);
        assertEquals(State.GRASS,world.grid[2][2].next(null).who());
        //Rule E:
        setWorld(worldSeed,4);
        assertEquals(State.EMPTY,world.grid[3][3].next(null).who());
    }
}