package edu.iastate.cs228.hw1;

/**
 *  
 * @author
 *
 */

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.Random; 

/**
 * 
 * The world is represented as a square grid of size width X width. 
 *
 */
public class World 
{
	private int width; // grid size: width X width 
	
	public Living[][] grid; 
	
	/**
	 *  Default constructor reads from a file 
	 */
	public World(String inputFileName) throws FileNotFoundException
	{

		// Open file and set up scanner
        File f = new File(inputFileName);
        Scanner s = new Scanner(f);

		width = s.nextLine().length()/3;

        s = new Scanner(f);

		//create empty grid array
		grid = new Living[width][width];

		//begin filling array
        String input;
        int line = 0;

		while(s.hasNextLine()){

            input = s.nextLine();
            for (int i = 0; i < width*3; i+=3) {
                switch(input.charAt(i)){
                    case 'B':
                        grid[line][i/3] = new Badger(this,line,i/3,input.charAt(i+1)-0x30); // subtract 0x30 ASCII offset
                        break;
                    case 'F':
                        grid[line][i/3] = new Fox(this,line,i/3,input.charAt(i+1)-0x30); // subtract 0x30 ASCII offset
                        break;
                    case 'G':
                        grid[line][i/3] = new Grass(this,line,i/3);
                        break;
                    case 'E':
                        grid[line][i/3] = new Empty(this,line,i/3);
                        break;
                    case 'R':
                        grid[line][i/3] = new Rabbit(this,line,i/3,input.charAt(i+1)-0x30); // subtract 0x30 ASCII offset
                        break;
                }
            }
            line++;
		}

        //close scanner's filestream
        s.close();
	}
	
	/**
	 * Constructor that builds a w X w grid without initializing it. 
	 * @param w  the grid
	 */
	public World(int w)
	{
		this.width = w;
		grid = new Living[this.width][this.width];

	}

	/**
	 * Return the width of the world.
	 * @return the width
     */
	public int getWidth()
	{
		return this.width;  // to be modified
	}

	/**
	 * Initialize the world by randomly assigning to every square of the grid  
	 * one of BADGER, FOX, RABBIT, GRASS, or EMPTY.  
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit()
	{
		Random generator = new Random();
		for (int i = 0; i <width ; i++) {
			for (int j = 0; j <width ; j++) {

                switch (State.values()[generator.nextInt(5)]){

                    case BADGER:
                        grid[i][j]= new Badger(this,i,j,0);
                        break;
                    case EMPTY:
                        grid[i][j] = new Empty(this,i,j);
                        break;
                    case FOX:
                        grid[i][j] = new Fox(this,i,j,0);
                        break;
                    case GRASS:
                        grid[i][j] = new Grass(this,i,j);
                        break;
                    case RABBIT:
                        grid[i][j] = new Rabbit(this,i,j,0);
                        break;
                }
			}

		}
	}
	
	
	/**
	 * Output the world grid. For each square, output the first letter of the living form
	 * occupying the square. If the living form is an animal, then output the age of the animal 
	 * followed by a blank space; otherwise, output two blanks.  
	 */
	public String toString()
	{
		String output="";
        if(grid[0][0] != null) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < width; j++) {
                    switch (grid[i][j].who()) {
                        case BADGER:
                            output = output + "B" + ((Animal) grid[i][j]).myAge() + " ";
                            break;
                        case EMPTY:
                            output = output + "E" + "  ";
                            break;
                        case FOX:
                            output = output + "F" + ((Animal) grid[i][j]).myAge() + " ";
                            break;
                        case GRASS:
                            output = output + "G" + "  ";
                            break;
                        case RABBIT:
                            output = output + "R" + ((Animal) grid[i][j]).myAge() + " ";
                            break;
                    }
                }
                //split the array line by line so no additional formatting is needed before printing or writing output
                output = output + "\n";
            }
        }

		return output;
	}
	

    public World(String[][] testworld){

    }

	/**
	 * Write the world grid to an output file.  Also useful for saving a randomly 
	 * generated world for debugging purpose. 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException
	{
        //open file for writing
        File f = new File(outputFileName);
        PrintWriter pw = new PrintWriter(f);

        //write contents of world object via toString() -- already properly formatted
        pw.write(this.toString());

        //close file
        pw.close();
	}			
}
