package Program2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * <h1>Algae of Life 3D</h1>
 * This program simulates the life cycles of "algae" within a pond. Now with Fish!
 * Rules:
 * 	Each cell starts with two algae
 * 	In addition: a set number of fish are randomly distributed around the pond
 * 	During each iteration each algae, with it's associated probability, performs one of the following actions:
 * 		Cells containing 3 or less:
 *			Die: 		probability = .1
 *			Reproduce: 	probability = .6
 *			Survive: 	probability = .1  			
 *			Migrate: 	probability = .2
 * 		Cells containing 5 or more:
 * 			Die: 		until cell population = 4
 * 
 * During each iteration each fish will either eat, migrate or both.
 * 		Fish will:
 * 			Die: 
 * 				If: 
 * 					Energy Cells = 0;
 * 			Reproduce(upon migrating to another cell):
 * 				If:
 * 					Two fish are present
 * 					Both have energy cells > 2
 * 					One, or Both, of the fish is/are not the result of the previous two fish
 * 			Eat:
 * 				If:
 * 					Energy Cells < 5
 * 					Fish can only have a maximum of 5 Energy Cells (program compensates to ensure maximum is not exceeded)
 * 					Chance = Probability = .5
 * 				
 * 					   
 * 
 * 
 * @author Jeremy Booth
 * @version 2.0
 * @since 3/18/2020
 * */
public class GoL2 
{
	//Global Variables
	private int[] migPos = {-1, 0, 1}; 
	private double[] probDRSM = new double[4]; //Probabilities for cells containing less than
	private ArrayList<Algae>[][][] algaePond;
	private ArrayList<Fish>[][][] fishPond;
	private MasterPond masterpond;
	private ExeOrder eo;
	private ArrayList<Algae>[][][] futureAddAlgae; //Algae iteration helper array
	private ArrayList<Fish>[][][] futureAddFish;
	
	//Counters
	private int popCount = 0;
	private int size = 0;
	private int Errs = 0;
	private int Fours = 0;
	private int Threes = 0;
	private int Twos = 0;
	private int Ones = 0;
	private int Zeros = 0;
	private int f0 = 0;
	private int f1 = 0;
	private int f2 = 0;
	private int f3 = 0;
	private int FMore = 0;
	private int fishCount = 0;
	
	
	/**
	 * Constructor to form a "Game of Life" object with a set size.
	 * @param sz	the cubes size 
	 */
	public GoL2(int sz, String[] probs)
	{
		initHelpers(sz, probs);
	}
	
	/**
	 * Helper method for initializing key components
	 * @param sz - size of the array
	 * @param args - probabilities for the algae
	 */
	@SuppressWarnings("unchecked")
	private void initHelpers(int sz, String[] args)
	{
		this.size = sz;
		this.algaePond = new ArrayList[this.size][this.size][this.size];
		this.fishPond = new ArrayList[this.size][this.size][this.size];
		this.masterpond = new MasterPond(algaePond, fishPond);
		
		//probabilities		
		for(int i = 0; i < 4; i++)
		{
			this.probDRSM[i] = Double.parseDouble(args[i]);
		}
		PopControl pop = new PopControl();
		this.futureAddAlgae = pop.dummyAPond(sz);
		this.futureAddFish = pop.dummyFPond(sz);
		this.eo = new ExeOrder(probDRSM);
	}
	
	/**
	 * Populates the cube with its initial values.
	 * @param sz	the cubes size
	 * @param val	the starting value at each element in the array.
	 * @return		returns a new populated 3-dimensional array
	 */
	@SuppressWarnings("unchecked")
	public void populate(int algaeVal, int fishVal, int fishDist)
	{
		ArrayList<Algae>[][][] apond = new ArrayList[this.size][this.size][this.size];
		ArrayList<Fish>[][][] fpond = new ArrayList[this.size][this.size][this.size];
		PopControl pop = new PopControl();
		apond = pop.populateAlgae(algaeVal, this.size);
		
		//Initial population count the population of algae 
		for (int i = 0; i < this.size; i++)
		{
			for(int j = 0; j < this.size; j++)
			{
				for(int k = 0; k < this.size; k++)
				{
					aPopCount(apond[i][j][k].size());
				}	
			}	
		}
		
		//Initial population count the population of fish
		 fpond = pop.populateFish(fishVal, this.size, fishDist); 
		 for (int i = 0; i < this.size; i++) 
		 { 
			 for(int j = 0; j < this.size; j++) 
			 { 
				 for(int k = 0; k < this.size; k++)
				 { 
					 fPopCount(fpond[i][j][k].size());
				 } 
			 } 
		 }	
		
		masterpond.setAlgae(apond);
		masterpond.setFish(fpond);
	}
	
	/**
	 * Iterates through the Algae ArrayList and performs operations 
	 * @param sz	the cubes size 
	 */
	public void calcAlgae()
	{
		Random rand = new Random();
		ArrayList<Algae>[][][] algae = masterpond.getAlgae();
		for (int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					int CellPop = algae[i][j][k].size();
					for(int q = 0; q < CellPop; q++)
					{
						if(algae[i][j][k].size() < 5)
						{	
							DecimalFormat Deci = new DecimalFormat("#.#");
							double chance = Double.parseDouble(Deci.format(rand.nextDouble()));
							if(this.eo.areEquals())
							{
								int[] temp1 = eo.stringToIntArray(eo.dupsProbs());
								if(eo.numDups() == 1)														//One set of duplicate values, the other two values are different
								{
									if((eo.minValue(temp1[0], probDRSM) != -1) && (eo.midValue(temp1[0], probDRSM) != -1)) //min -> mid -> dups
									{
										if(chance <= probDRSM[eo.minValue(temp1[0], probDRSM)])
										{
											int result = eo.minValue(temp1[0], probDRSM);
											stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
										else if(chance <= probDRSM[eo.midValue(temp1[0], probDRSM)])
										{
											int result = eo.midValue(temp1[0], probDRSM);
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
										else if(chance <= probDRSM[temp1[0]])
										{
											int coinflip = rand.nextInt(2);
											int result = 0;
											result = temp1[coinflip];
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
									}
									else if((eo.minValue(temp1[0], probDRSM) != -1) && (eo.maxValue(temp1[0], probDRSM) != -1)) //min -> dups -> max
									{
										if(chance <= probDRSM[eo.minValue(temp1[0], probDRSM)])
										{
											int result = eo.minValue(temp1[0], probDRSM);
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
										else if(chance <= probDRSM[temp1[0]])
										{
											int coinflip = rand.nextInt(2);
											int result = 0;
											result = temp1[coinflip];
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
										else if(chance <= probDRSM[eo.maxValue(temp1[0], probDRSM)])
										{
											int result = eo.maxValue(temp1[0], probDRSM);
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
									}
									else if((eo.midValue(temp1[0], probDRSM) != -1) && (eo.maxValue(temp1[0], probDRSM) != -1)) //dups -> mid -> max
									{
										if(chance <= probDRSM[temp1[0]])
										{
											int coinflip = rand.nextInt(2);
											int result = 0;
											result = temp1[coinflip];
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
										else if(chance <= probDRSM[eo.midValue(temp1[0], probDRSM)])
										{
											int result = eo.midValue(temp1[0], probDRSM);
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
										else if(chance <= probDRSM[eo.maxValue(temp1[0], probDRSM)])
										{
											int result = eo.maxValue(temp1[0], probDRSM);
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
									}
								}
								else if(eo.numDups() == 2 && eo.stringToIntArray(eo.dupsProbs()).length == 3)					//One set of duplicate values, contains three of the same value.
								{
									if(eo.minValue(temp1[0], probDRSM) != -1)
									{
										if(chance <= probDRSM[eo.minValue(temp1[0], probDRSM)])
										{
											int result = eo.minValue(temp1[0], probDRSM);
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
										else if(chance <= probDRSM[temp1[0]])
										{
											int coinflip = rand.nextInt(3);
											int result = 0;
											result = temp1[coinflip];
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
									}
									else if(eo.maxValue(temp1[0], probDRSM) != -1)
									{
										if(chance <= probDRSM[temp1[0]])
										{
											int coinflip = rand.nextInt(3);
											int result = 0;
											result = temp1[coinflip];
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
										else if(chance <= probDRSM[eo.maxValue(temp1[0], probDRSM)])
										{
											int result = eo.maxValue(temp1[0], probDRSM);
											algae = stdCalcCoin(algae, i, j, k, rand, q, result);
											break;
											/*
											 * if(result == 0) //die { algae[i][j][k].get(q).isDead(); break; } else
											 * if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break; }
											 * else if(result == 2) //survive { //do nothing break; } else if(result ==
											 * 3) //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
											 */
										}
									}
								}
								else if(eo.numDups() == 2 && eo.stringToIntArray(eo.dupsProbs()).length == 4)				//Two sets of duplicate values, contains two different sets of identical values
								{
									int[] temp2 = eo.orderOfEval();
									if(chance <= probDRSM[temp2[0]])
									{
										int coinflip = rand.nextInt(2);
										int result = 0;
										if(coinflip == 0)
										{
											result = temp2[0];
										}
										else if(coinflip == 1)
										{
											result = temp2[1];
										}
										algae = stdCalcCoin(algae, i, j, k, rand, q, result);
										break;
										/*
										 * //do something if(result == 0) //die { algae[i][j][k].get(q).isDead(); break;
										 * } else if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break;
										 * } else if(result == 2) //survive { //do nothing break; } else if(result == 3)
										 * //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
										 */
									}
									else if(chance <= probDRSM[temp1[2]])
									{
										int coinflip = rand.nextInt(2);
										int result = 0;
										if(coinflip == 0)
										{
											result = temp2[2];
										}
										else if(coinflip == 1)
										{
											result = temp2[3];
										}
										
										algae = stdCalcCoin(algae, i, j, k, rand, q, result);
										break;
										/*
										 * //do something if(result == 0) //die { algae[i][j][k].get(q).isDead(); break;
										 * } else if(result == 1) //reproduce { algae[i][j][k].add(new Algae()); break;
										 * } else if(result == 2) //survive { //do nothing break; } else if(result == 3)
										 * //migrate { algae = willMigrate(algae, i, j, k, rand, q); break; }
										 */
									}
								}
								else if(eo.numDups() == 3)												//All probabilities are the same value
								{
									int coinflip = rand.nextInt(4);
									if(coinflip == 0)//die
									{
										algae[i][j][k].get(q).isDead();
										break;
									}
									else if(coinflip == 1) //reproduce
									{
										algae[i][j][k].add(new Algae());
										break;
									}
									else if(coinflip == 2) //survive
									{
										//do nothing
										break;
									}
									else if(coinflip == 3) //migrate
									{
										algae = willMigrate(algae, i, j, k, rand, q);
										break;
									}
								}
							}
							else																		//All probabilities are different with no duplicates
							{
								int[] temp1 = eo.orderOfEval();
								for(int p = 0; p < temp1.length; p++)
								{
									if(chance <= probDRSM[temp1[p]])
									{
										if(temp1[p] == 0) //die
										{
											//die
											algae[i][j][k].get(q).isDead();
											break;
										}
										else if(temp1[p] == 1)
										{
											//reproduce
											algae[i][j][k].add(new Algae());
											break;
										}
										else if(temp1[p] == 2)
										{
											//survive - do nothing
											break;
										}
										else if(temp1[p] == 3)
										{
											//migrate
											algae = willMigrate(algae, i, j, k, rand, q);
											break;
										}
									}
								}
							}
						}
						else if(algae[i][j][k].size() > 4)
						{
							algae[i][j][k].get(q).isDead();
						}
					}
					//purge dead algae from the pond
					for(int q = 0; q < algae[i][j][k].size(); q++)
					{
						if(algae[i][j][k].get(q).getStatus() == 0)
						{
							algae[i][j][k].remove(q);
						}
					}
					aPopCount(algae[i][j][k].size());
				}
			}
		}
		masterpond.setAlgae(algae);
	} 																						//End-of CalcAlgae()
	
	private ArrayList<Algae>[][][] stdCalcCoin(ArrayList<Algae>[][][] algae, int i, int j, int k, Random rand, int q, int result)
	{	
		//do something
		if(result == 0)	//die
		{
			algae[i][j][k].get(q).isDead();
			return algae;
		}
		else if(result == 1) //reproduce
		{
			algae[i][j][k].add(new Algae());
			return algae;
		}
		else if(result == 2) //survive
		{
			//do nothing
			return algae;
		}
		else if(result == 3) //migrate
		{
			algae = willMigrate(algae, i, j, k, rand, q);
			return algae;
		}
		return algae;
	}
	
	/**
	 * Iterates through the Fish ArrayList and performs operations 
	 * @param sz	the cubes size 
	 */
	public void calcFish()
	{
		Random rand = new Random();
		ArrayList<Algae>[][][] algae = masterpond.getAlgae();
		ArrayList<Fish>[][][] fish = masterpond.getFish();
		for (int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					int CellPop = fish[i][j][k].size();
					if(CellPop > 0)
					{
						for(int q = 0; q < CellPop; q++)
						{
							//DecimalFormat Deci = new DecimalFormat("#.#");
							//double chance = Double.parseDouble(Deci.format(rand.nextDouble()));
							int chance = rand.nextInt(2);
							if(fish[i][j][k].get(q).isAlive() > 0)
							{
								if(algae[i][j][k].size() > 0)
								{
									if(fish[i][j][k].get(q).retEnergy() < 5 && chance == 0)
									{
										for(int eat = 0; eat < algae[i][j][k].size(); eat++)
										{
											if(algae[i][j][k].get(eat).getStatus() > 0)
											{
												fish[i][j][k].get(q).hasEaten();
												algae[i][j][k].get(eat).isDead();
												break;
											}
										}
									}	
								}
								//migrate
								fish[i][j][k].get(q).newFish(0);
								fish = willMigrate(fish, i, j, k, q);									
							}
						}
					}
					//purge dead algae from the pond
					for(int q = 0; q < algae[i][j][k].size(); q++)
					{
						if(algae[i][j][k].get(q).getStatus() == 0)
						{
							algae[i][j][k].remove(q);
						}
					}
					//purge dead fish from the pond
					for(int q = 0; q < fish[i][j][k].size(); q++)
					{
						if(fish[i][j][k].get(q).isAlive() == 0)
						{
							fish[i][j][k].remove(q);
						}
					}
					aPopCount(algae[i][j][k].size());
					fPopCount(fish[i][j][k].size());
				}
			}
		}
		masterpond.setFish(fish);
	}																				//End-of CalcFish()
	
	private void aPopCount(int algae)
	{
		if(algae == 0)
		{
			incZero();
		}
		else if(algae == 1)
		{
			incOne();
		}
		else if(algae == 2)
		{
			incTwo();
		}
		else if(algae == 3)
		{
			incThree();
		}
		else if(algae == 4)
		{
			incFour();
		}
		else if(algae > 4)
		{
			incErrs();
		}
		popCount += algae;
	}
	
	private void fPopCount(int fish)
	{
		if(fish == 0)
		{
			incFZero();
		}
		else if(fish == 1)
		{
			incFOne();
		}
		else if(fish == 2)
		{
			incFTwo();
		}
		else if(fish == 3)
		{
			incFThree();
		}
		else if(fish >= 4)
		{
			incFMore();
		}
		fishCount += fish;
	}
	
	private ArrayList<Algae>[][][] willMigrate(ArrayList<Algae>[][][] algae, int i, int j, int k, Random rand, int q)
	{
		int x = migPos[rand.nextInt(3)];
		int y = migPos[rand.nextInt(3)];
		int z = migPos[rand.nextInt(3)];
		//System.out.println("values: X: " + (x+i) + " Y: " + (y+j) + " Z: " + (z+k));
		if(((i+x) != i) && ((j+y) != j) && ((k+z) != k))
		{
			if(futureAddAlgae[i][j][k].size() > 0)
			{
				for(int r = 0; r < futureAddAlgae[i][j][k].size(); r++)
				{
					algae[i][j][k].add(this.futureAddAlgae[i][j][k].get(r));
					futureAddAlgae[i][j][k].remove(r);
				}
			}
			/*
			 * if(futureAddAlgae[i][j][k].size() == 0) { pond[i][j][k] -=
			 * this.futureAddAlgae[i][j][k]; futureAddAlgae[i][j][k] = 0; }
			 */
			futureAddAlgae[rangeComp(i, x)][rangeComp(j, y)][rangeComp(k, z)].add(algae[i][j][k].get(q));
			algae[i][j][k].remove(q);

		}
		return algae;
	}
	
	private ArrayList<Fish>[][][] willMigrate(ArrayList<Fish>[][][] fish, int i, int j, int k, int q)
	{	
		Random rand = new Random();
		int x = migPos[rand.nextInt(3)];
		int y = migPos[rand.nextInt(3)];
		int z = migPos[rand.nextInt(3)];
		//System.out.println("values: X: " + (x+i) + " Y: " + (y+j) + " Z: " + (z+k));
		if(((i+x) != i) && ((j+y) != j) && ((k+z) != k))
		{
			fish[i][j][k].get(q).cycle();
			futureAddFish[rangeComp(i, x)][rangeComp(j, y)][rangeComp(k, z)].add(fish[i][j][k].get(q));
			if(futureAddFish[rangeComp(i, x)][rangeComp(j, y)][rangeComp(k, z)].size() > 2)
			{
				int size = futureAddFish[rangeComp(i, x)][rangeComp(j, y)][rangeComp(k, z)].size();
				for(int u = 0; u < size; )
				{
					if((u+1) < size)
					{
						if(futureAddFish[rangeComp(i, x)][rangeComp(j, y)] [rangeComp(k, z)].get(u).retEnergy() > 1 && 
							futureAddFish[rangeComp(i, x)][rangeComp(j, y)][rangeComp(k, z)].get(u+1).retEnergy() > 1
							&& futureAddFish[rangeComp(i, x)][rangeComp(j, y)][rangeComp(k, z)].get(u).newFish() != 1 &&
							futureAddFish[rangeComp(i, x)][rangeComp(j, y)][rangeComp(k, z)].get(u+1).newFish() != 1)
						{
							futureAddFish[rangeComp(i, x)][rangeComp(j, y)][rangeComp(k, z)].add(new Fish());
							futureAddFish[rangeComp(i, x)][rangeComp(j, y)][rangeComp(k, z)].get(u).cycle();
							futureAddFish[rangeComp(i, x)][rangeComp(j, y)][rangeComp(k, z)].get(u+1).cycle();
							u = u+2;
						}
					}
					else
						break;
				}
				if(futureAddFish[i][j][k].size() > 0)
				{
					for(int r = 0; r < futureAddFish[i][j][k].size(); r++)
					{
						fish[i][j][k].add(futureAddFish[i][j][k].get(r));
						futureAddFish[i][j][k].remove(r);
					}
				}
				/*
				 * if(futureAddAlgae[i][j][k].size() == 0) { pond[i][j][k] -=
				 * this.futureAddAlgae[i][j][k]; futureAddAlgae[i][j][k] = 0; }
				 */
			}
			fish[i][j][k].remove(q);

		}
		return fish;
	}
	
	/**
	 * Compares and ensures the value passed is within the range [0,100].
	 * If the resulting value violates the limit of the array. The organism doesn't move.
	 * @param	x	current location value.
	 * @param	i	modPos value
	 * */
	private int rangeComp(int x, int i)
	{
		if((x+i) >= 100)
		{
			return x;
		}
		else if((x+i) < 0)
		{
			return x;
		}
		return (x+i);
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
	private void incFZero()
	{
		this.f0++;
	}
	private void incFOne()
	{
		this.f1++;
	}
	private void incFTwo()
	{
		this.f2++;
	}
	private void incFThree()
	{
		this.f3++;
	}
	private void incFMore()
	{
		this.FMore++;
	}
	private void incErrs()
	{
		this.Errs++;
	}
	private void resetACounts()
	{
		this.Zeros = 0;
		this.Ones = 0;
		this.Twos = 0;
		this.Threes = 0;
		this.Fours = 0;
		this.Errs = 0;
		this.popCount = 0;
	}
	private void resetFCounts()
	{
		this.f0 = 0;
		this.f1 = 0;
		this.f2 = 0;
		this.f3 = 0;
		this.FMore = 0;
		this.fishCount = 0;
	}
	
	/**
	 * Performs calculate functions for the Algae and Fish ArrayLists (n) times.
	 * @param	cycles	The number of times (n) the array to be changed. 
	 * */
	public void iterate(int cycles)
	{
			for(int i = 0; i < cycles; i++)
			{
				calcAlgae();
				displayAlgae(i+1);
				calcFish();
				//displayFish(i+1);
				displayAlgae(i+1);
			}
	}
	
	/**
	 * Displays the totals found in individual cells and the overall population as a whole.
	 * @param	iteration	specifies the current iteration that the display method is called. 
	 * */
	public void displayAlgae(int iteration)
	{
		System.out.print("Algae: ");
		System.out.print("Iteration: " + iteration + "; Cell Populations: " + this.Zeros + " Zeros, " + this.Ones + " Ones, " + this.Twos + " Twos, " + this.Threes + " Threes, " + this.Fours + " Fours." + ": Five or More:" + Errs);
		System.out.println(" Total Population: " + this.popCount + "\n");
		resetACounts();
	}
	
	public void displayFish(int iteration)
	{
		System.out.print("Fish: ");
		System.out.print("Iteration: " + iteration + "; Cell Populations: " + this.f0 + " Zeros, " + this.f1 + " Ones, " + this.f2 + " Twos, " + this.f3 + " Threes, " + this.FMore + " Fours or More");
		System.out.println(" Total Population: " + this.fishCount + "\n");
		resetFCounts();
	}
	
	/**
	 * Where the magic happens.
	 * */
	public static void main(String[] args)
	{
		//double[] probabilities = {.1, .6, .1, .2};
		
		int fishNum = 1000;
		int fishDist = 1000;
		int algaeNum = 2;
		int numIter = 100;
		
		GoL2 pond = new GoL2(100, args); //initial starting arguments for creating a new pond
		pond.populate(algaeNum, fishNum, fishDist); //populating the pond with algae and fish
		pond.displayAlgae(0);
		pond.displayFish(0);
		pond.iterate(numIter);
	}
}
