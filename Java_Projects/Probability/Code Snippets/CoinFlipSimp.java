package HW2;
/**@author Jeremy Booth
 * CS355 - Probability & Statistics
 * 
 * Creates three "coin" arrays and puts them in a "jar". One coin is randomly selected, tossed, and the face is displayed.
 * The probabilities of "Heads or Tails" on the flip-side is calculated and displayed.
 * 
 * */

import java.util.Random;

public class CoinFlipSimp 
{
		private static void chooseNtoss(Object[] coin, double totalPoss)
	{
		Random rand = new Random();
		String[] sCoin = (String[]) coin[rand.nextInt(3)];
		
		int a = rand.nextInt(2);
		System.out.println("Face is: " + sCoin[a]);
		double pos = (sCoin.length/totalPoss)/((totalPoss/2)/totalPoss);  //Formula for calculating the probability
		if(sCoin[a] == "Heads")
		{
			System.out.println("Chance of flip-side being heads: " + String.format("%.3f", pos));
			System.out.println("Chance of flip-side being tails: " + String.format("%.3f", (1 - pos)));
			System.out.println();
			flip(sCoin);
		}
		if(sCoin[a] == "Tails")
		{
			System.out.println("Chance of flip-side being tails: " + String.format("%.3f", (1 - pos)));
			System.out.println("Chance of flip-side being tails: " + String.format("%.3f", pos));
			System.out.println();
			flip(sCoin);
		}
	}
	
	private static void flip(String[] coin)
	{
		System.out.println("The two sides of the selected coin are:\n");
		System.out.println(coin[0] + " and " + coin[1]);
	}
	
	public static void main(String[] args) 
	{
		String[] coinA = {"Heads", "Heads"};
		String[] coinB = {"Tails", "Tails"};
		String[] coinC = {"Heads", "Tails"};
		Object[] jar = {coinA, coinB, coinC};
		double poss = coinA.length * jar.length;
		chooseNtoss(jar, poss);
		
	}
}
