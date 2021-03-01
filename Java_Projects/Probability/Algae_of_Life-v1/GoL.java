import java.text.DecimalFormat;
import java.util.Random;

/**
 * <h1>Algae of Life 3D</h1>
 * This program simulates the life cycles of "algae" within a pond.
 * Rules:
 * 	Each cell starts with two algae
 * 	During each iteration each algae, with it's associated probability, performs one of the following actions:
 * 		Cells containing 3 or less:
 *			Die: 		probability = .1
 *			Reproduce: 	probability = .3
 *			Survive: 	probability = .6  			
 * 		Cells containing 4:
 * 			Die: 		probability = .3
 *			Migrate: 	probability = .3
 *			Survive: 	probability = .6
 * 		Cells containing 5 or more:
 * 			Die: 		until cell population = 4
 * 
 * 
 * @author Jeremy Booth
 * @version 1.0
 * @since 2/10/2020
 * */
public class GoL 
{
	
	private int[] migPos = {-1, 0, 1}; 
	private double[] probDRS = {.1, .3, .6}; //Probabilities for cells containing less than 4
	private double[] probMDS = {.3, .3, .4}; //Probabilities for cells containing exactly 4
	private int popCount = 0;
	private int size = 0;
	private int Errs = 0;
	private int Fours = 0;
	private int Threes = 0;
	private int Twos = 0;
	private int Ones = 0;
	private int Zeros = 0;
	private int[][][] futureAdd;
	
	/**
	 * Constructor to form a "Game of Life" object with a set size.
	 * @param sz	the cubes size 
	 */
	public GoL(int sz)
	{
		this.size = sz;
		initHelpers();
	}
	
	private void initHelpers()
	{
		futureAdd = new int[this.size][this.size][this.size];
	}
	
	/**
	 * Populates the cube with its initial values.
	 * @param sz	the cubes size
	 * @param val	the starting value at each element in the array.
	 * @return		returns a new populated 3-dimensional array
	 */
	public int[][][] populate(int sz, int val)
	{
		int[][][] pond = new int[size][size][size];
		for (int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					pond[i][j][k] = val;
					if(pond[i][j][k] == 0)
					{
						incZero();
					}
					else if(pond[i][j][k] == 1)
					{
						incOne();
					}
					else if(pond[i][j][k] == 2)
					{
						incTwo();
					}
					else if(pond[i][j][k] == 3)
					{
						incThree();
					}
					popCount += pond[i][j][k];
				}
				//System.out.print(pond[i][j][0]);
			}
			//System.out.println();
		}
		//System.out.println();
		return pond;
	}
	
	/**
	 * Iterates through the array and performs 
	 * @param sz	the cubes size 
	 */
	public int[][][] calculate(int[][][] pond)
	{
		Random rand = new Random();
		for (int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					int cellPop = pond[i][j][k];
					for(int q = 0; q < cellPop; q++)
					{
						if(pond[i][j][k] < 4)
						{
							if(probDRS[0] != probDRS[1])
							{
								if(probDRS[0] > probDRS[1])
								{
									DecimalFormat Deci = new DecimalFormat("#.#");
									double chance = Double.parseDouble(Deci.format(rand.nextDouble()));
									//reproduce
									if(chance <= probDRS[1])
									{
										pond[i][j][k]++;
									}
									//die
									else if(chance <= probDRS[0])
									{
										pond[i][j][k]--;
									}
									//else do nothing
								}
								else if(probDRS[0] < probDRS[1])
								{
									DecimalFormat Deci = new DecimalFormat("#.#");
									double chance = Double.parseDouble(Deci.format(rand.nextDouble()));
									//die
									if(chance <= probDRS[0])
									{
										pond[i][j][k]--;
									}
									//reproduce
									else if(chance <= probDRS[1])
									{
										pond[i][j][k]++;
									}
									//else do nothing
								}
							}
							else if(probDRS[0] == probDRS[1])
							{
								DecimalFormat Deci = new DecimalFormat("#.#");
								double chance = Double.parseDouble(Deci.format(rand.nextDouble()));
								//die
								if(chance <= probDRS[0])
								{
									int coinflip = rand.nextInt(2);
									//die
									if(coinflip == 0) 
									{
										pond[i][j][k]--;
									}
									//reproduce
									else if(coinflip == 1)
									{
										pond[i][j][k]++;
									}
								}
								//else do nothing
							}
						}
						else if(pond[i][j][k] == 4)
						{
							if(probMDS[0] != probMDS[1])
							{
								if(probMDS[0] > probMDS[1])
								{
									DecimalFormat Deci = new DecimalFormat("#.#");
									double chance = Double.parseDouble(Deci.format(rand.nextDouble()));
									//die or migrate
									if(chance <= probMDS[1]) //migrate
									{
										int x = migPos[rand.nextInt(3)];
										int y = migPos[rand.nextInt(3)];
										int z = migPos[rand.nextInt(3)];
										//System.out.println("values: X: " + (x+i) + " Y: " + (y+j) + " Z: " + (z+k));
										if((((i+x) != i) && ((j+y) != j) && ((k+z) != k)))
										{
											if(futureAdd[i][j][k] > 0)
											{
												pond[i][j][k] += this.futureAdd[i][j][k];
												futureAdd[i][j][k] = 0;
											}
											if(futureAdd[i][j][k] < 0)
											{
												pond[i][j][k] -= this.futureAdd[i][j][k];
												futureAdd[i][j][k] = 0;
											}
											futureAdd[rangeComp(i+x)][rangeComp(j+y)][rangeComp(k+z)] += 1;
											futureAdd[i][j][k] -= 1;
										}
									}
									else if(chance <= probMDS[0])
									{
										pond[i][j][k]--;
									}
									//else do nothing
								}
								else if(probMDS[0] < probMDS[1])
								{
									DecimalFormat Deci = new DecimalFormat("#.#");
									double chance = Double.parseDouble(Deci.format(rand.nextDouble()));
									//die or migrate
									if(chance <= probMDS[0]) //die
									{
										pond[i][j][k]--;
									}
									else if(chance <= probMDS[1]) //migrate
									{
										int x = migPos[rand.nextInt(3)];
										int y = migPos[rand.nextInt(3)];
										int z = migPos[rand.nextInt(3)];
										//System.out.println("values: X: " + (x+i) + " Y: " + (y+j) + " Z: " + (z+k));
										if((((i+x) != i) && ((j+y) != j) && ((k+z) != k)))
										{
											if(pond[rangeComp(i+x)][rangeComp(j+y)][rangeComp(k+z)] < 4)
											{
												if(futureAdd[i][j][k] > 0)
												{
													pond[i][j][k] += this.futureAdd[i][j][k];
													futureAdd[i][j][k] = 0;
												}
												if(futureAdd[i][j][k] < 0)
												{
													pond[i][j][k] -= this.futureAdd[i][j][k];
													futureAdd[i][j][k] = 0;
												}
												futureAdd[rangeComp(i+x)][rangeComp(j+y)][rangeComp(k+z)] += 1;
												futureAdd[i][j][k] -= 1;
											}
											//else do nothing
										}
									}
									//else do nothing
								}
							}
							else if(probMDS[0] == probMDS[1])
							{
								DecimalFormat Deci = new DecimalFormat("#.#");
								double chance = Double.parseDouble(Deci.format(rand.nextDouble()));
								//die or migrate
								if(chance <= probMDS[0])
								{
									int coinflip = rand.nextInt(2);
									if(coinflip == 0) 	//die
									{
										pond[i][j][k]--;
									}
									else if(coinflip == 1) //migrate
									{
										int x = migPos[rand.nextInt(3)];
										int y = migPos[rand.nextInt(3)];
										int z = migPos[rand.nextInt(3)];
										//System.out.println("values: X: " + (x+i) + " Y: " + (y+j) + " Z: " + (z+k));
										if((((i+x) != i) && ((j+y) != j) && ((k+z) != k)))
										{
											if(futureAdd[i][j][k] > 0)
											{
												pond[i][j][k] += this.futureAdd[i][j][k];
												futureAdd[i][j][k] = 0;
											}
											if(futureAdd[i][j][k] < 0)
											{
												pond[i][j][k] -= this.futureAdd[i][j][k];
												futureAdd[i][j][k] = 0;
											}
											futureAdd[rangeComp(i+x)][rangeComp(j+y)][rangeComp(k+z)] += 1;
											futureAdd[i][j][k] -= 1;
										}
									}
								}
							}
						}
						else if(pond[i][j][k] > 4 )
						{
							//the purge
							pond[i][j][k]--;
						}
					}
					if(pond[i][j][k] == 0)
					{
						incZero();
					}
					else if(pond[i][j][k] == 1)
					{
						incOne();
					}
					else if(pond[i][j][k] == 2)
					{
						incTwo();
					}
					else if(pond[i][j][k] == 3)
					{
						incThree();
					}
					else if(pond[i][j][k] == 4)
					{
						incFour();
					}
					else if(pond[i][j][k] > 4)
					{
						incErrs();
					}
					popCount += pond[i][j][k];

				}
				//System.out.print(pond[i][j][0]);
			}
			//System.out.println();
		}
		//System.out.println();
		return pond;
	}
	
	/**
	 * Compares and ensures the value passed is within the range [0,100].
	 * @param	x	value to be compared, and "fixed" if necessary.
	 * */
	private int rangeComp(int x)
	{
		if(x >= 100)
		{
			return (x-100);
		}
		else if(x < 0)
		{
			return Math.abs(x);
		}
		return x;
	}
	
	/**
	 * Increments and resets the counters
	 * */
	private void incFour()
	{
		this.Fours++;
	}
	private void incThree()
	{
		this.Threes++;
	}
	private void incTwo()
	{
		this.Twos++;
	}
	private void incOne()
	{
		this.Ones++;
	}
	private void incZero()
	{
		this.Zeros++;
	}
	private void incErrs()
	{
		this.Errs++;
	}
	private void resetCounts()
	{
		this.Zeros = 0;
		this.Ones = 0;
		this.Twos = 0;
		this.Threes = 0;
		this.Fours = 0;
		this.Errs = 0;
		this.popCount = 0;
	}
	
	/**
	 * Performs calculate function n times.
	 * @param	pond	The pond 3-dimensional array to be changed.
	 * @param	cycles	The number of times (n) the array to be changed. 
	 * */
	public int[][][] iterate(int[][][] pond, int cycles)
	{
			int[][][] a = null;
			for(int i = 0; i < cycles; i++)
			{
				a = calculate(pond);
				display(i+1);
			}
			return a;
	}
	
	/**
	 * Displays the totals found in individual cells and the overall population as a whole.
	 * @param	iteration	specifies the current iteration that the display method is called. 
	 * */
	public void display(int iteration)
	{
		System.out.println("Iteration: " + iteration + "; Cell Populations: " + this.Zeros + " Zeros, " + this.Ones + " Ones, " + this.Twos + " Twos, " + this.Threes + " Threes, " + this.Fours + " Fours." + ": Five or More:" + Errs);
		System.out.println("Total Population: " + this.popCount + "\n");
		resetCounts();
	}
	
	/**
	 * Where the magic happens.
	 * */
	public static void main(String[] args)
	{
		GoL pond = new GoL(100);
		int[][][] pondA = pond.populate(100, 2);
		pond.display(0);
		pondA = pond.iterate(pondA, 10);
	}
}
