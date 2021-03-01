package Program2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Helper class for generating pond populations
 * 
 * @author Jeremy Booth
 * @version 1.0
 * @since 4/11/2020
 */
public class PopControl {

	public PopControl()
	{
		//Default
	}
	
	/**
	 * Creates a "dummy pond" for holding migrating algae between cycles
	 * @param size - size of the pond
	 * @return - returns an ArrayList which contain algaeVal number of algae
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Algae>[][][] dummyAPond(int size)
	{
		ArrayList<Algae>[][][] pond = new ArrayList[size][size][size];
		for (int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					pond[i][j][k] = new ArrayList<Algae>();
				}
			}
		}
		return pond;
	}
	
	/**
	 * Creates a "dummy pond" for holding migrating fish between cycles
	 * @param size - size of the pond
	 * @return - returns an ArrayList which contain algaeVal number of algae
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Fish>[][][] dummyFPond(int size)
	{
		ArrayList<Fish>[][][] pond = new ArrayList[size][size][size];
		for (int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					pond[i][j][k] = new ArrayList<Fish>();
				}
			}
		}
		return pond;
	}
	
	/**
	 * Populates a 3-D ArrayList with the specified initial algae
	 * @param algaeVal - number of algae the pond is to be populated with initially
	 * @param size - size of the pond
	 * @return - returns an ArrayList which contain algaeVal number of algae
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Algae>[][][] populateAlgae(int algaeVal, int size)
	{
		ArrayList<Algae>[][][] pond = new ArrayList[size][size][size];
		for (int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					pond[i][j][k] = new ArrayList<Algae>();
				}
			}
		}
		for (int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					for(int c = 0; c < algaeVal; c++)
					{
						pond[i][j][k].add(new Algae());
					}
				}
			}
		}
		return pond;
	}
	
	/***
	 * Populates a 3-D ArrayList with the specified initial fish
	 * @param numFish - Number of fish to populate the pond
	 * @param size - size of the pond cubed
	 * @param distribution - the ratio in which fish are added. Must be value of 1 or more. 10 = 1/10 probability
	 * @return returns fish pond
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Fish>[][][] populateFish(int numFish, int size, int distribution)
	{
		ArrayList<Fish>[][][] pond = new ArrayList[size][size][size];
		for (int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					pond[i][j][k] = new ArrayList<Fish>();
				}
			}
		}
		Random rand = new Random();
		while(numFish != 0)
		{
			for (int i = 0; i < size; i++)
			{
				for(int j = 0; j < size; j++)
				{
					for(int k = 0; k < size; k++)
					{
						int chance = rand.nextInt(distribution);
						if(chance == 0 &&  numFish != 0)
						{
							pond[i][j][k].add(new Fish());
							numFish--;
						}
					}
				}
			}
		}

		return pond;
	}
	

	
	//Method for local testing
	@SuppressWarnings("unchecked")
	public static void main(String[] args) 
	{
		PopControl pop = new PopControl();
		ArrayList<Algae>[][][] a = new ArrayList[100][100][100];
		a = pop.populateAlgae(2, 100);
		int count = 0;
		for(int i = 0; i < a.length; i++)
		{
			for(int j = 0; j < a.length; j++)
			{
				for(int k = 0; k < a.length; k++)
				{
					//System.out.print(a[i][j][k].size());
					count += a[i][j][k].size(); 
				}
				//System.out.println();
			}
		}
		System.out.println("Algae count: " + count);
		
		ArrayList<Fish>[][][] b = new ArrayList[100][100][100];
		b = pop.populateFish(10000, 100, 10000);
		int count1 = 0;
		for(int i = 0; i < b.length; i++)
		{
			for(int j = 0; j < b.length; j++)
			{
				for(int k = 0; k < b.length; k++)
				{
					//System.out.print(b[i][j][k].size());
					count1 += b[i][j][k].size(); 
				}
				//System.out.println();
			}
		}
		System.out.println("Fish count: " + count1);
	}

}
