package edu.iastate.cs228.hw1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author
 */
public class FoxTest {

    private World world;
    private Fox fox;

    private String[][] worldSeed ={ {"F6 ","F0 ","F0 "},
                                    {"R0 ","B0 ","B0 "},
                                    {"F2 ","R0 ","F0 "}};

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
        fox = new Fox(null,0,0,0);
        assertEquals(State.FOX, fox.who());
    }

    @Test
    /**
     * Tests each of the survival rules for Fox
     */
    public void next() throws Exception {

        //Rule A:
        setWorld(worldSeed,1);
        assertEquals(State.EMPTY,world.grid[0][0].next(null).who());
        //Rule B:
        setWorld(worldSeed,3);
        assertEquals(State.BADGER,world.grid[2][2].next(null).who());
        //Rule C:
        setWorld(worldSeed,3);
        assertEquals(State.EMPTY,world.grid[0][1].next(null).who());
        //Rule D:
        setWorld(worldSeed,3);
        assertEquals(State.FOX,world.grid[2][0].next(null).who());

    }
}