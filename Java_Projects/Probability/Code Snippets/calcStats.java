package HW9;
import java.text.DecimalFormat;

/**@author Jeremy Booth
 * CS355 - Probability & Statistics
 * 
 * The program takes sample data provided by the user, calculates the mean, variance, standard deviation, median, five point sum, interquartile range and any outliers and prints them to the console.
 * */

public class calcStats {
	
	public static void printSample(String[] args)
	{
		if(args.length % 2 == 0)
		{
			for(int i = 0; i<args.length; i++)
			{
				System.out.print(args[i] + " ");
				if(args[i] == args[args.length/2-1])
				{
					System.out.println();
				}
				else if(args[i] == (args[(int) (args.length*(25*.01))]))
				{
					System.out.println();
				}
				else if(args[i] == (args[(int) (args.length*(75*.01))]))
				{
					System.out.println();
				}
			}
		}
		else
		{
			for(int i = 0; i<args.length; i++)
			{
				System.out.print(args[i] + " ");
				if(args[i] == args[args.length/2])
				{
					System.out.println();
				}
				else if(args[i] == (args[(int) (args.length*(25*.01))]))
				{
					System.out.println();
				}
				else if(args[i] == (args[(int) (args.length*(75*.01))]))
				{
					System.out.println();
				}
			}
		}
	}
	public static String calcMean(String[] args)
	{
		
		double total = 0;
		for(int i = 0; i < args.length; i++)
		{
			total += Double.parseDouble(args[i]);
		}
		return formatValue(total/args.length);
	}
	
	public static String calcVar(String[] args)
	{
		double total = 0;
		double mean = Double.parseDouble(calcMean(args));
		for(int i = 0; i < args.length; i++)
		{
			//System.out.println(Math.pow((Double.parseDouble(args[i]) - mean), 2));
			total += Math.pow((Double.parseDouble(args[i]) - mean), 2); 
		}
		DecimalFormat a = new DecimalFormat("#.###");
		return total + "/" + (args.length-1) +  " = " + a.format(total/(args.length-1));
	}
	private static String calcVar(String[] args, int indicator)
	{
		double total = 0;
		double mean = Double.parseDouble(calcMean(args));
		for(int i = 0; i < args.length; i++)
		{
			//System.out.println(Math.pow((Double.parseDouble(args[i]) - mean), 2));
			total += Math.pow((Double.parseDouble(args[i]) - mean), 2); 
		}
		
		return formatValue(total/(args.length-1));
	} 
	
	public static String calcStdDev(String[] args)
	{
		return formatValue(Math.sqrt(Double.parseDouble(calcVar(args, 0))));
	}
	
	private static String formatValue(double decimal)
	{
		DecimalFormat a = new DecimalFormat("#.###");
		return a.format(decimal);
	}
	
	public static double calcMedian(String[] args, int indicator)
	{
		if(indicator == 0)
		{
			if(args.length % 2 == 0)
			{
				return (Double.parseDouble(args[args.length/2]) + Double.parseDouble(args[args.length/2-1]))/2;
			}
			else
			{
				return (Double.parseDouble(args[args.length/2]));
			}
		}
		else
		{
			if(args.length % 2 == 0)
			{
				System.out.print("(" + Double.parseDouble(args[args.length/2]) + " + " + Double.parseDouble(args[args.length/2-1]) + ") / " + 2 + " = ");
				return (Double.parseDouble(args[args.length/2]) + Double.parseDouble(args[args.length/2-1]))/2;
			}
			else
			{
				System.out.print("n / " + 2 + " = ");
				return (Double.parseDouble(args[args.length/2]));
			}
		}
	}
	
	public static String getFirstQuartile(String[] args)
	{
		int index = (int) (args.length*(25*.01));
		return args[index];
	}
	
	public static String getThirdQuartile(String[] args)
	{
		int index = (int) (args.length*(75*.01));
		return args[index];
	}
	
	public static String calcInterQuartileRange(String[] args)
	{
		return formatValue(Double.parseDouble(getThirdQuartile(args))-Double.parseDouble(getFirstQuartile(args)));
	}
	
	public static String calcOulier(String[] args)
	{
		Double multiplier = Double.parseDouble(calcInterQuartileRange(args))*1.5;
		Double lBound = Double.parseDouble(getThirdQuartile(args)) + multiplier;
		Double sBound = Double.parseDouble(getFirstQuartile(args)) - multiplier;
		String outliers = "";
		for(int i = 0; i < args.length; i++)
		{
			if(Double.parseDouble(args[i]) <= sBound || Double.parseDouble(args[i]) >= lBound)
			{
				outliers += args[i] + " ";
			}
		}
		if(outliers != "")
		{
			return "There are outliers: " + outliers;
		}
		return "no outliers found";
	}
	
	public static String[] sort(String[] args)
	{
		double[] arr = new double[args.length];
		String newS = "";
		for(int i = 0; i < args.length; i++)
		{
			arr[i] = Double.parseDouble(args[i]);
		}
		MergeSort ar = new MergeSort();
		ar.sort(arr, 0, arr.length-1);
		for(int i = 0; i < arr.length; i++)
		{
			newS += arr[i] + " ";
		}
		return newS.split(" ");
	}
	
	//Prints the provided sample data, the mean, variance, standard deviation, median, five point sum, interquartile range and any outliers to the console.
	public static void printValues(String[] args)
	{
		//Prints Sample Data
		System.out.println("Sample:");
		printSample(args);
		System.out.println("\nof "+ args.length + " values\n");
		
		//Sample Mean
		System.out.println("Sample Mean: " + calcMean(args) +"\n");
		
		//Variance
		System.out.println("Variance: " + calcVar(args) + "\n");
		
		//Standard Deviation
		System.out.println("Standard Deviation: " + calcStdDev(args) + "\n");
		
		//Median
		System.out.print("Median: ");
		System.out.println(calcMedian(args, 1) + "\n");
		
		//Five Point Sum.
		System.out.println("Five Point Summary: (min, Q1, Median, Q3, Max)");
		System.out.println("(" + args[0] + ", " + getFirstQuartile(args) + ", " + calcMedian(args, 0) + ", " + getThirdQuartile(args) + ", " + args[args.length-1] + ")\n");
		
		//Interquartile Range
		System.out.println("Interquartile Range: " + calcInterQuartileRange(args) + "\n");
		
		//Outlier
		System.out.println("Outlier? " + calcOulier(args));
	}
	
	public static void main(String[] args) 
	{
		//Quickly Sort Sample Data
		args = sort(args);
		printValues(args);		
	}

}
