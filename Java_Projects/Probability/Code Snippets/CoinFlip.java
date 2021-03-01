package HW2;
import java.util.Random;

/**
 * @author Jeremy Booth
 * CS355 - Probability & Statistics
 * 
 * We are given three coins: One has heads on both faces, the second has tails on both faces and the third is a regular coin.
 * Choose from a coin at random, toss it, and the result is heads.
 * What is the possibility the opposite face is tails?*/
public class CoinFlip 
{
	private String facer;
	private String backer;
	
	public CoinFlip(String face, String back)
	{
		this.facer = face;
		this.backer = back;
	}
	
	/*Returns the face side of the coin*/
	private String retFace()
	{
		return this.facer;
	}
	
	/*Returns the tails side of the coin*/
	private String retBack()
	{
		return this.backer;
	}
	
	//Choose
	/*Chooses a coin at random*/
	public static void choose(CoinFlip[] coin)
	{
		Random rand = new Random();
		int a = rand.nextInt(3);
		toss(coin[a]);
	}
	
	//Toss
	/*Random toss of the selected coin*/
	private static void toss(CoinFlip coin)
	{
		Random rand = new Random();
		int a = rand.nextInt(2);
		if(a == 0)
		{
			System.out.println("First Side: " + coin.retFace());
			flip(coin, a);
		}
		else
		{
			System.out.println("First Side: " + coin.retBack());
			flip(coin, a);
		}
	}
	
	//Flip
	/*Reveals the second side of the selected coin*/
	public static void flip(CoinFlip coin, int a)
	{
		if(a == 1)
		{
			System.out.println("Second Side: " + coin.retFace());
		}
		else
		{
			System.out.println("Second Side: " + coin.retBack());
		}
	}
	
	//Magic Time
	public static void main(String[] args)
	{
		CoinFlip a = new CoinFlip("heads", "heads");
		CoinFlip b = new CoinFlip("tails", "tails");
		CoinFlip c = new CoinFlip("heads", "tails");
		CoinFlip[] d = {a, b, c};
		int rolls = 6;
		for(int i = 0; i < rolls; i++)
		{
			choose(d);
			System.out.println();
		}
	}
	
}
