package Program2;

/**
 * Creates an Algae object for use in the Game of Algae program
 * 
 * @author Jeremy Booth
 * @version 1.0
 * @since 3/27/2020
 */

public class Algae 
{
	//Global Variables
	private int live = 0;
	
	/**
	 * Default Constructor
	 */
	public Algae()
	{
		this.live = 1;
	}
	
	/**
	 * The algae is set to the dead state
	 */
	public void isDead()
	{
		this.live = 0;
	}
	
	/**
	 * 1 = live, 0 = dead
	 * @return returns a value for the status of the algae.
	 */
	public int getStatus()
	{
		return this.live;
	}
	
	//Method for local testing
	public static void main(String[] args)
	{
		
	}
}
