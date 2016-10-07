package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.nio.file.WatchEvent;
import java.util.Scanner;

/**
 *  
 * @author
 *
 */

/**
 * 
 * The PredatorPrey class performs the predator-prey simulation over a grid world 
 * with squares occupied by badgers, foxes, rabbits, grass, or none. 
 *
 */
public class PredatorPrey 
{
	/**
	 * Update the new world from the old world in one cycle. 
	 * @param wOld  old world
	 * @param wNew  new world 
	 */
	public static void updateWorld(World wOld, World wNew)
	{

        for (int i = 0; i < wOld.getWidth(); i++) {

            for (int j = 0; j < wOld.getWidth() ; j++) {
                wNew.grid[i][j] = wOld.grid[i][j].next(wNew);
            }

        }
    }
	
	/**
	 * Repeatedly generates worlds either randomly or from reading files. 
	 * Over each world, carries out an input number of cycles of evolution. 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		// 
		// Generate predator-prey simulations repeatedly like shown in the 
		// sample run in the project description.
		
		World wEven = new World(1);                    // the world after an even number of cycles

		World wOdd = new World(1);                   // the world after an odd number of cycles


        int trial = 1;
        String intro = "The Predator-Prey Simulator\nChoose option: " +
                "1 (Random World) | 2 (Read from file) | 3 (Exit)\n";

        Scanner input = new Scanner(System.in);
        System.out.println(intro);
        int choice = 0, gridint, cycleint, cycles = 0;

        System.out.print("Trial "+ trial + ": ");

        //check if user entered a '3'
        while (choice!=3) {
            choice = input.nextInt();
            gridint=0;
            cycleint = 0;
            switch (choice) {
                case 1:

                    //Get initial width and number of cycles
                    System.out.print("\nRandom World\n");

                    System.out.print("Enter grid width: ");
                    gridint = input.nextInt();
                    while(gridint<1) gridint = input.nextInt(); //wait for input greater than 0
                    wEven = new World(gridint);

                    System.out.print("\nEnter the number of cycles: ");
                    cycleint = input.nextInt();
                    while(cycleint<1) cycleint = input.nextInt(); //wait for input greater than 0
                    cycles = cycleint;

                    /*
                     * Randomly init wEven
                     */
                    wEven.randomInit();

                    // Print value of initial world
                    System.out.println("Initial World: \n");
                    System.out.println(wEven.toString());

                    //Update world n times, according to number of cycles
                    for (int i = 1; i <= cycles; i++) {
                        if (i % 2 == 0) {
                            wEven = new World(wOdd.getWidth());
                            updateWorld(wOdd, wEven);
                        } else {
                            wOdd = new World(wEven.getWidth());
                            updateWorld(wEven, wOdd);
                        }
                    }
                    //Print final world, depending on the number of cycles
                    if(cycles %2 ==0) System.out.println("\nFinal World: \n\n" + wEven.toString());
                    else System.out.println("\nFinal World: \n\n" + wOdd.toString());
                    System.out.print("Trial "+ ++trial + ": ");
                    break;

                /*
                Case Allowing for input from a file
                 */
                case 2:
                    //Get File Name
                    System.out.print("\nWorld input from a file\n" +
                            "File name: ");
                    wEven = new World(input.next());

                    // Print value of initial world
                    System.out.print("Initial World: \n");
                    System.out.print(wEven.toString());

                    //Get number of cycles
                    System.out.print("\nEnter the number of cycles: ");
                    cycleint = input.nextInt();
                    while(cycleint<1) cycleint = input.nextInt(); //wait for input greater than 0
                    cycles = cycleint;

                    /*
                    Update world n times, according to number of cycles,
                    alternate between wEven and WOdd.
                     */

                    for (int i = 1; i <= cycles; i++) {
                        if(i %2 ==0) {
                            wEven = new World(wOdd.getWidth());
                            updateWorld(wOdd, wEven);
                        }
                        else {
                            wOdd = new World(wEven.getWidth());
                            updateWorld(wEven,wOdd);
                        }
                    }

                    //Print final world, depending on the number of cycles
                    if(cycles %2 ==0) System.out.println("\nFinal World: \n\n" + wEven.toString());
                    else System.out.println("\nFinal World: \n\n" + wOdd.toString());

                    //Print Start of next trial
                    System.out.print("Trial "+ ++trial + ": ");

                    break;

                /*
                Default case, allows user to type any number without
                causing an exception, also allows the user to type '3'
                to close program, due to while loop at top of method.
                  */
                default:
                    break;
            }

        }
	}
}
