package edu.iastate.cs228.hw1;

/**
 *  
 * @author
 *
 */

/**
 * Grass remains if more than rabbits in the neighborhood;
 * otherwise, it is eaten. 
 *
 */
public class Grass extends Living
{

	public Grass (World w, int r, int c) 
	{
		world=w;
        row=r;
        column=c;
	}
	
	public State who()
	{
		return State.GRASS;
	}
	
	/**
	 * Grass can be eaten out by too many rabbits in the neighborhood. Rabbits may also 
	 * multiply fast enough to take over Grass. 
	 */
	public Living next(World wNew)
	{
		// 
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for grass.

		int [] population = new int[NUM_LIFE_FORMS];
		census(population);


		if (population[RABBIT] >= (population[GRASS]*3)) return new Empty(wNew,row,column);
		else if (population[RABBIT] >= 3) return new Rabbit(wNew,row,column,0);
		else return new Grass(wNew,row,column);
	}
}
