package Program2;

/**
 * Creates an Fish object for use in the Game of Algae program
 * 
 * @author Jeremy Booth
 * @version 1.0
 * @since 3/27/2020
 */

public class Fish 
{
	//Global variables
	private int energy = 0;
	private int live_dead = 1;
	private int thing = 1;
	
	/**
	 * Default Constructor:
	 * 			New instantiated fish objects start with 2 cycles of energy. 
	 */
	public Fish()
	{
		this.energy = 2;
	}
	
	/**
	 * Fish eats algae only if it's alive.
	 * Fish can only gain up to a maximum of 5 energy.
	 * Each algae eaten += 3 energy
	 */
	public void hasEaten()
	{
		if(isAlive() == 1)
		{
			if(this.energy < 3)
			{
				this.energy += 3;
			}
			else
				this.energy = 5;
		}
	}
	
	/**
	 * Indicates if a fish has just been introduced to the pond
	 * If 1, the fish is new.
	 * If 0, the fish old.
	 */
	public int newFish()
	{
		return this.thing;
	}
	
	/**
	 * Sets whether a fish has just been introduced to the pond
	 * If 1, the fish is new.
	 * If 0, the fish is old.
	 */
	public void newFish(int thn)
	{
		this.thing = thn;
	}
	
	/**
	 * @return the amount of energy the fish has remaining
	 */
	public int retEnergy()
	{
		return this.energy;
	}
	
	/**
	 * Performs a cycle for the fish
	 * Reduces energy by one each time method is called.
	 * If energy is 0, the fish is dead.
	 */
	public void cycle()
	{
		if(this.energy > 0)
		{
			this.energy--;
		}
		else
			this.live_dead = 0;
	}
	
	/**
	 * Indicates if the fish is alive or dead.
	 * If the value is 1, the fish is alive
	 * If the value is 0, the fish is dead.
	 * @return returns the current status of the fish
	 */
	public int isAlive()
	{
		return this.live_dead;
	}	
	
	//pay no attention to this method.
	public static void main(String[] args)
	{
		/*
		 * Stop looking at it.
		 * There's nothing to see here.
		 * 
		 * :P
		 * 
		 * */
	}
}
