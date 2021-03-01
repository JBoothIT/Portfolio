package Program2;

import java.util.ArrayList;

/**
 * Helper Object for storing and managing the Fish and Algae ponds
 * 
 * @author Jeremy Booth
 * @version 1.0
 * @since 4/8/2020
 */


public class MasterPond {

	private ArrayList<Algae>[][][] algaePond;
	private ArrayList<Fish>[][][] fishPond;
	
	/**
	 * @param algae - ArrayList of algae
	 * @param fish - ArrayList of fish
	 */
	public MasterPond(ArrayList<Algae>[][][] algae, ArrayList<Fish>[][][] fish)
	{
		this.algaePond = algae;
		this.fishPond = fish;
	}
	
	/**
	 * @return - Current stored ArrayList for algae 
	 */
	public ArrayList<Algae>[][][] getAlgae()
	{
		return this.algaePond;
	}
	
	/**
	 * @return - Current stored ArrayList for fish
	 */
	public ArrayList<Fish>[][][] getFish()
	{
		return this.fishPond;
	}
	
	/**
	 * @param - Stores the current ArrayList for algae
	 */
	public void setAlgae(ArrayList<Algae>[][][] algae)
	{
		this.algaePond = algae;
	}
	
	/**
	 * @param - Stores the current ArrayList for fish
	 */
	public void setFish(ArrayList<Fish>[][][] fish)
	{
		this.fishPond = fish;
	}
	
	//Method for local testing
	public static void main(String[] args) 
	{

	}

}
