package HW3;

import java.util.Random;

/**@author Jeremy Booth
 * CS355 - Probability & Statistics
 * 
 * Creates an array of k jars, each with the same number of m white balls and n black balls.
 * The program randomly removes a ball from the first jar and places it into the next jar. The process continues until the k-1 ball
 * is placed into the k jar. The results are displayed and the fraction of times final ball is white is calculated.
 * 
 * Out of 10 white balls and 5 black balls probability of a white ball being removed at each jar is 10/15 = 2/3
 * While the probability of a black ball removed is 5/15 = 1/3.
 * */
public class RandJar 
{
	private int white = 0;
	private int black = 0;
	private static String firstBall;
	private static String lastBall;
	
	/* Constructors for creating jars*/
	public RandJar()
	{
		//default constructor
	}
	public RandJar(int m, int n)
	{
		this.white = m;
		this.black = n;
	}
	
	/* Add and remove specified number of balls from the jar.*/
	public void addWBall()
	{
		this.white += 1;
	}
	public void addBBall()
	{
		this.black += 1;
	}
	public void removeWBall()
	{
		this.white -= 1;
	}
	public void removeBBall()
	{
		this.black -= 1;
	}
	
	/* Returns number of balls within jar.*/
	public int getWBall()
	{
		return this.white;
	}
	public int getBBall()
	{
		return this.black;
	}
	
	/* Populates and return an array of jars containing a set number of balls*/
	public static RandJar[] setup(int numJ, int numW, int numB)
	{
		RandJar[] jars = new RandJar[numJ];
		for(int i = 0; i < numJ; i++)
		{
			jars[i] = new RandJar(numW, numB);
		}
		return jars;
	}
	
	/*
	 * Iterates through each jar. Adds the ball from the previous jar and randomly
	 * removes a ball from the current jar.
	 */
	public static RandJar[] iterate(RandJar[] jars)
	{
		int addSel = 0;
		for(int i = 0; i < jars.length; i++) 
		{
			Random rand = new Random();
			int remSel = rand.nextInt(3);
			if((i == 0)&&(remSel > 0))
			{
				jars[i].removeWBall();
				firstBall = "white";
				System.out.println("Jar " + (i+1) + ": white ball removed");
				addSel = 1; 
			}
			else if((i == 0)&&(remSel == 0))
			{
				jars[i].removeBBall();
				firstBall = "black";
				System.out.println("Jar " + (i+1) + ": black ball removed");
				addSel = 2;
			}
			else if(i == (jars.length-1))
			{
				if(addSel == 1)
				{
					jars[i].addWBall();
					System.out.println("Jar " + (i+1) + ": white ball added");
					int lastRand = rand.nextInt(3);
					if(lastRand > 0)
					{
						lastBall = "white";
						jars[i].removeWBall();
					}
					else if(lastRand == 0)
					{
						lastBall = "black";
						jars[i].removeBBall();
					}
				}
				else if (addSel == 2)
				{
					jars[i].addBBall();
					lastBall = "black";
					System.out.println("Jar " + (i+1) + ": black ball added");
				}
			}
			else if(remSel > 0)
			{
				switch(addSel)
				{
					case 1: jars[i].addWBall();
					System.out.print("Jar " + (i+1) + ": white ball added; ");
					break;
					case 2: jars[i].addBBall();
					System.out.print("Jar "+ (i+1) + ": black ball added; ");
					break;
				}
				jars[i].removeWBall();
				System.out.println("white ball removed");
				addSel = 1;
			}
			else if(remSel == 0)
			{
				switch(addSel)
				{
					case 1: jars[i].addWBall();
					System.out.print("Jar " + (i+1) + ": white ball added; ");
					break;
					case 2: jars[i].addBBall();
					System.out.print("Jar "+ (i+1) + ": black ball added; ");
					break;
				}
				jars[i].removeBBall();
				System.out.println("black ball removed");
				addSel = 2;
			}
		}
		return jars;
	}
	
	/*
	 * Displays the contents of all the jars and indicates the first and last balls
	 * chosen in the series
	 */
	public static void display(RandJar[] jar, int beginW, int beginB)
	{
		System.out.println("\nEach Jar begins with: " + beginW + " white balls and " + beginB + " black balls.\n");
		for(int i = 0; i < jar.length; i++)
		{
			System.out.println("Jar " + (i+1) + ": " + jar[i].getWBall() + " white balls and " + jar[i].getBBall() + " black balls");
		}
		System.out.println("\nFirst Ball: " + firstBall + " - with the first jar containing: " + jar[0].getWBall() + " white balls and " + jar[0].getBBall() + " black balls");
		System.out.println("Last Ball: " + lastBall + " - with the last jar containing: " + jar[jar.length-1].getWBall() + " white balls and " + jar[jar.length-1].getBBall() + " black balls");
	}
	
	public static void main(String[] args)
	{
		RandJar[] jars = setup(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		jars = iterate(jars);
		display(jars, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
	}

}
