package edu.iastate.cs228.hw1;

/**
 *  
 * @author
 *
 */

/**
 * 
 * Living refers to the life form occupying a square in a world grid. It is a 
 * superclass of Empty, Grass, and Animal, the latter of which is in turn a superclass
 * of Badger, Fox, and Rabbit. Living has two abstract methods awaiting implementation. 
 *
 */
public abstract class Living 
{
	protected World world; // the world in which the life form resides
	protected int row;     // location of the square on which 
	protected int column;  // the life form resides
	
	// constants to be used as indices. 
	protected static final int BADGER = 0; 
	protected static final int EMPTY = 1; 
	protected static final int FOX = 2; 
	protected static final int GRASS = 3; 
	protected static final int RABBIT = 4; 
	
	public static final int NUM_LIFE_FORMS = 5; 
	
	// life expectancies 
	public static final int BADGER_MAX_AGE = 4; 
	public static final int FOX_MAX_AGE = 6; 
	public static final int RABBIT_MAX_AGE = 3; 
	
	
	/**
	 * Censuses all life forms in the 3 X 3 neighborhood in a world. 
	 * @param population  counts of all life forms
	 */
	protected void census(int [] population)
	{
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {

                /*  to handle case where a particular square does not exist, ie
                        the center of the neighborhood lies on either a corner or edge.
                    */

                if((row+i >=0 && column+j >=0 )&& (row+i<world.getWidth() && column+j<world.getWidth())){

                    switch (world.grid[row + i][column + j].who()) {

                        case BADGER:
                            population[BADGER]++;
                            break;
                        case EMPTY:
                            population[EMPTY]++;
                            break;
                        case FOX:
                            population[FOX]++;
                            break;
                        case GRASS:
                            population[GRASS]++;
                            break;
                        case RABBIT:
                            population[RABBIT]++;
                            break;
                        default:
                            break;
                    }

                }
            }

        }

    }

	/**
	 * Gets the identity of the life form on the square.
	 * @return State
	 */
	public abstract State who();
	// To be implemented in each class of Badger, Empty, Fox, Grass, and Rabbit. 
	// 
	// There are five states given in State.java. Include the prefix State in   
	// the return value, e.g., return State.Fox instead of Fox.  
	
	/**
	 * Determines the life form on the square in the next cycle.
	 * @param  wNew  world of the next cycle
	 * @return Living 
	 */
	public abstract Living next(World wNew); 
	// To be implemented in the classes Badger, Empty, Fox, Grass, and Rabbit. 
	// 
	// For each class (life form), carry out the following: 
	// 
	// 1. Obtains counts of life forms in the 3X3 neighborhood of the class object. 

	// 2. Applies the survival rules for the life form to determine the life form  
	//    (on the same square) in the next cycle.  These rules are given in the  
	//    project description. 
	// 
	// 3. Generate this new life form at the same location in the world wNew.      

}
