package Program2;

/**
 * Driver for the Game of Algae program
 * 
 * @author Jeremy Booth
 * @version 1.0
 * @since 4/12/2020
 */

public class Driver {

	/**
	 * Converts a (size 4) double array into an array of strings (don't ask).
	 * @param args - double array containing probabilities for Algae
	 * @return array of strings
	 */
	public static String[] convert(double[] args)
	{
		String str = "";
		for(int i = 0; i < 4; i++)
		{
			str += args[i] + " ";
		}
		return str.split(" ");
	}
	
	/**
	 * Where the magic happens.
	 * */
	public static void main(String[] args) 
	{
		double[] probabilities = {.1, .6, .1, .2};
		int fishNum = 10000;		//Number of fish to be added initially
		int fishDist = 100; 		//How far apart the fish are when initially added to the pond (recommend a high number)
		int algaeNum = 2;		//Number of algae to be added initially
		int numIter = 100;		//How many cycles the program is to run
		
		//initial starting arguments for creating a new pond
		GoL2 pond = new GoL2(100, convert(probabilities));
		
		//populating the pond with algae and fish
		pond.populate(algaeNum, fishNum, fishDist);
		
		//Display the initial values
		pond.displayAlgae(0);
		//pond.displayFish(0); 		//Note = The value of Zeros indicates the number empty cells. Not dead fish.
		
		//Run the program for the specified number of cycles.
		pond.iterate(numIter);
	}

}
