package Lab07;
import java.text.DecimalFormat;
import java.util.*;

public class binPack 
{
	public static int size = 1;											//Bin Size
	public static LinkedList<Double> list = new LinkedList<Double>();   //item list of doubles
	public static LinkedList<Bin> bins = new LinkedList<Bin>();			//list of bins
	/**Generates random double values which populate the items list*/
	public static Double[] generate(int size)
	{
		Double[] a = new Double[size];
		for(int i = 0; i < size; i++)
		{
			DecimalFormat decim = new DecimalFormat("0.000");			//Formats the values to the third decimal place.
			/*a[i] = Double.parseDouble(decim.format(Math.random()));  */   //creates a Double array
			list.add(Double.parseDouble(decim.format(Math.random())));
		}
		return a;
	}
	/**Assigns values to the list*/
	/*public static void assign(Double[] a)
	{
		for(int i = 0; i < a.length; i++)
		{
			list.add(a[i]);
		}
	}
*/	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter size of item list: ");  
		generate(scan.nextInt());													//Original generate method call
		Collections.sort(list);
		Collections.reverse(list);
		/*Double[] a = generate(scan.nextInt());*/										//user inserts the size of the item list
/*		Merge.sort(a);	*/															//Sorts the values using merge sort
	/*	assign(a);*/																	//Assigns values to the list
		/*Output*/
		System.out.println("Bin Size: " + size);
		System.out.print("Item:");
		/*Prints the values within the item list*/
		for(int i = 0; i < list.size(); i++)							
		{
			System.out.print(" " + list.get(i)+ ",");
		}
		System.out.println();
		/*Determines whether items fit within the current bins*/
		for (int x = 0; x < list.size(); x++) 
		{
			boolean doesItFit = false; // does this item fit in any existing bins?
			Double thisItem = list.get(x);
			for (int y = 0; y < bins.size(); y++) 
			{
				Bin thisBin = bins.get(y);
				if (thisBin.fits(thisItem)) 
				{
					doesItFit = true;
					thisBin.insert(thisItem);
					break; // breaks out of the bin loop, since we found a bin to insert it in
				}
			}
			if (! doesItFit) { // didn't fit, initialize a bin for it
				Bin bin = new Bin(size);
				bins.add(bin);
				bin.insert(list.get(x));
			}
		}
		/*Format and prints the values within the bins*/
		System.out.println("Bins: ");
		for(int i = 0; i < bins.size(); i++)
		{
			System.out.println(bins.get(i).toString());
		}
		
		System.out.println("Number of bins needed: " + bins.size());		//Prints number of bins needs to fit all values
	}
}
