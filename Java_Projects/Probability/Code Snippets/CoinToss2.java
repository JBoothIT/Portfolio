package HW3;
/**@author Jeremy Booth
 * CS355 - Probability & Statistics
 * 
 * Creates fair "coin" array. Tosses the coin three times and records the number of times it lands on heads.
 * Accepts values from command-line to indicate the number of iterations the program is to perform.
 * Provide 1000, 10000 and 100000 as a command-line arguments
 * */

import java.util.Random;

public class CoinToss2 
{
	private static int H3 = 0;
	private static int H2 = 0;
	private static int H1 = 0;
	private static int NH = 0;
	
	private static void tosscoin(String[] coin, double totalPoss)
	{
		int countH = 0;
		for(int i = 0; i<3; i++)
		{
			Random rand = new Random();
			int a = rand.nextInt(2);
			//System.out.println("Face is: " + coin[a]);
			if(coin[a] == "Heads")
			{
				//System.out.println("The resulting coin is: " + coin[a]);
				countH++;
			}
		}
		switch(countH) 
		{
			case 3: H3++;
			break;
			case 2: H2++;
			break;
			case 1: H1++;
			break;
			case 0: NH++;
			break;
		}
		//System.out.println();
		//System.out.println("Count Heads: " + countH + " Tails: " + countT);
		//System.out.println();
	}
	
	private static void results() 
	{
		System.out.println("Triple-H: " + H3 + "\nDouble-H: " + H2 + "\nSingle-H: " + H1 + "\nNo Heads: " + NH);
	}
	
	public static void main(String[] args) 
	{
		String[] coin = {"Heads", "Tails"};
		for(int i = 0; i < args.length; i++)
		{
			int runtimes = Integer.parseInt(args[i]);
			System.out.println("# Runs: " + runtimes);
			for(int j = 0; j < runtimes; j++)
			{
				tosscoin(coin, coin.length);
			}
			results();
			System.out.println();
		}
	}
}
