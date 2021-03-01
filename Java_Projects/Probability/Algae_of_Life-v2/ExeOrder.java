package Program2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Takes in a series of probabilities and determines the optimal ordering in which they are to be evaluated.
 * 
 * @author Jeremy Booth
 * @version 1.0
 * @since 4/11/2020
 */

public class ExeOrder
{
	private double[] prbs;
	
	/**
	 * @param probs - double array containing the probabilities used during the execution of the program
	 */
	public ExeOrder(double[] probs)
	{
		this.prbs = probs;
	}
	
	/**
	 * Determine if any duplicate values are present
	 * @return true if any duplicates are detected
	 */
	public boolean areEquals()
	{
		for(int i = 0; i < prbs.length; i++)
		{
			for(int j = i+1; j < prbs.length; j++)
			{
				if(prbs[i] == prbs[j])
				{
					return true;
				}
			}
			
		}
		return false;
	}
	
	/**
	 * Determine the number of duplicate values
	 * @return returns a count of duplicates
	 */
	public int numDups()
	{
		int count = 0;
		for(int i = 0; i < prbs.length; i++)
		{
			for(int j = i+1; j < prbs.length; j++)
			{
				if(prbs[i] == prbs[j])
				{
					count++;
					break;
				}
			}
			
		}
		return count;
	}
	
	/**
	 * Generates a string containing the indexes of any duplicates
	 * @return String containing index values
	 */
	public String dupsProbs()
	{		
		String temp = "";
		for(int i = 0; i < prbs.length; i++)
		{
			for(int j = i+1; j < prbs.length; j++)
			{
				if(prbs[i] == prbs[j])
				{
					temp += i + " " + j + " ";
					break;
				}
			}	
		}			
		return temp;
	}
	
	/**
	 * Convert a string into an array of ints. String cannot be empty.
	 * @param temp
	 * @return integer array of all indexes containing duplicates
	 */
	public int[] stringToIntArray(String temp)
	{
		String [] t = temp.split(" ");
		ArrayList<String> tTemp = new ArrayList<String>();
		for(int i=0; i < t.length; i++){
		    
		    if( !tTemp.contains(t[i]) ){
		        tTemp.add(t[i]);
		    }
		} 
		t = tTemp.toArray(new String[tTemp.size()]);
		
		
		int[] dups = new int[t.length];
		for(int i = 0; i < t.length; i++)
		{
			dups[i] = Integer.parseInt(t[i]);
		}

		return dups;
	}
	
	/**
	 * Takes integer array and prints it to screen
	 * @param dups - array of integers
	 */
	public void printArray(int[] dups)
	{
		for(int i = 0; i < dups.length; i++)
		{
			System.out.print(dups[i] + " ");
		}
	}
	
	/**
	 * Determines if values in an array are in ascending order. 
	 * If values are not in ascending sorted order, the array must have two sets of duplicate values.
	 * @param integer array of indexes. Array must be length 4.
	 * */
	public boolean isAscending(int[] probs)
	{  
		if(probs.length == 4)
		{
			for(int i = 0; i < probs.length-1; i++)
			{
				if(probs[i] >= probs[i+1])
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Determines the order in which probabilities should be evaluated.
	 * If numDups = 1
	 * If numDups = 2 and yields 4 values, split the array in two and perform two coin flips
	 * If numDups = 2 and yields 3 values, determine the stand alone value and perform a coin flip on the other three.
	 * If numDups = 3, perform a coin flip on all values.
	 * @return integer array of indexes
	 */
	public int[] orderOfEval()
	{
		double[] tmp = new double[prbs.length];
		for(int i = 0; i < tmp.length; i++)
		{
			tmp[i] = prbs[i];
		}
		Arrays.sort(tmp);
		int[] temp = new int[prbs.length];
		
		if(numDups() == 1)
		{
			for(int i = 0; i < prbs.length; i++)
			{
				
			}
			return temp;
		}
		else if(numDups() == 2)
		{
			int count = 0;
			int[] tmp1 = stringToIntArray(dupsProbs());
			for(int i = 0; i < tmp1.length; i++)
			{
				if(prbs[tmp1[0]] == prbs[tmp1[i]])
				{
					count++;
				}
			}
			System.out.println(count);
			if(count == 2)
			{
				if(prbs[tmp1[0]] > prbs[tmp1[2]])
				{
					String a = "";
					String b = "";
					for(int i = 0; i < 2; i++)
					{
						a += tmp1[i] + " ";
						b += tmp1[i+2] + " ";
					}
					String[] tmp2 = b.concat(a).split(" ");
					for(int i = 0; i < 4; i++)
					{
						tmp1[i] = Integer.parseInt(tmp2[i]);
					}
					return tmp1;
				}
				else 
					return tmp1;
			}
			else
				return tmp1;
			
		}
		else if(numDups() == 3)
		{
			 return stringToIntArray(dupsProbs());
		}
		else
		{
			/*
			 * double max = prbs[0]; double mid2 = 0; double mid1 = 0; double min = prbs[0];
			 */
			int first = 0, sec = 0, third = 0, last = 0;
			for(int i = 0; i < prbs.length; i++)
			{
				if(prbs[i] == tmp[3])
				{
					//max = prbs[i];
					last = i;
				}
				if(prbs[i] == tmp[0])
				{
					//min = prbs[i];
					first = i;
				}
				if(prbs[i] == tmp[2])
				{
					//mid2 = prbs[i];
					third = i;
				}
				if(prbs[i] == tmp[1])
				{
					//mid1 = prbs[i];
					sec = i;
				}
			}
			temp[0] = first;
			temp[1] = sec;
			temp[2] = third;
			temp[3] = last;
			return temp;
		}
	}
	/**
	 * Determines the max value in an array and returns it's index
	 * @param tmp - element from dupsProbs()
	 * @param probs - array of probabilities
	 * @return Outputs index of the maximum value in the array of probabilities. Returns -1 if none found.
	 */
	public int maxValue(int tmp, double[] probs)
	{
		for(int i = 0; i < probs.length; i++)
		{
			if(probs[tmp] < probs[i])
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Determines the minimum value in an array and returns it's index. 
	 * @param tmp - element from dupsProbs()
	 * @param probs - array of probabilities
	 * @return Outputs index of the minimum value in the array of probabilities. Returns -1 if none found.
	 */
	public int minValue(int tmp, double[] probs)
	{
		for(int i = 0; i < probs.length; i++)
		{
			if(probs[i] < probs[tmp])
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Takes array of integers. Order of evaluation results. 
	 * @param tmp - element of the dupsProbs() results
	 * @param probs - probabilities
	 * @return Outputs index of the middle value in the array of probabilities. Returns -1 if none found.
	 */
	  public int midValue(int tmp, double[] probs) 
	 {
		  for(int i = 0; i < probs.length; i++)
		  {
			  if((probs[i] > probs[tmp]) && (probs[i] < probs[maxValue(tmp, probs)]))
			  {
				  return i;
			  }
			  else if(probs[i] < probs[tmp] && probs[i] > probs[minValue(tmp, probs)])
			  {
				  return i;
			  }
		  }
		  return -1;
	 }
	
	
	//Method for testing purposes
	public static void main(String[] args)
	{
		double[] test= {.2, .1, .4, .3};
		ExeOrder eo = new ExeOrder(test);
		
		//Handle Duplicate Values
		System.out.println(eo.areEquals());
		System.out.println(eo.numDups());
		if(eo.areEquals())
		{
			eo.printArray(eo.stringToIntArray(eo.dupsProbs()));
			System.out.println("\nIs ascending: " + eo.isAscending(eo.stringToIntArray(eo.dupsProbs())));
		}
		else
		{
			System.out.println("No duplicates");
		}
		
		//Handle order of evaluation
		eo.printArray(eo.orderOfEval());
		int[] num = eo.stringToIntArray(eo.dupsProbs());
		System.out.println();
		System.out.println("max: " + eo.maxValue(num[0], test));
		System.out.println("min: " + eo.minValue(num[0], test));
		System.out.println("mid: " + eo.midValue(num[0], test));
	}
}
