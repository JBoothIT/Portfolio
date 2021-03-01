package Lab07;
import java.util.LinkedList;


/**
 * FirstFitSeq.java
 * An implementation of the first-fit algorithm for bin packing, where items are inserted into bins into the lowest index'd bin it will fit in.
 * The order of items input is kept, so you can supply a sorted or unsorted list for best-fit-decreasing
 * 
 * @author Stephen Martel  sam8277
 */
public class FirstFitSeq {
	
	// The size for every bin, given as input.
	public static int binSize = 0;
	
	// A list of values (items) to be stored within bins, given as input.
	// Preserves the order the items are input in, allowing for sorted or random lists to be given.
	public static LinkedList<Integer> itemList = new LinkedList<Integer>();
	
	// the bins required for this algorithm
	public static LinkedList<IntBin> bins = new LinkedList<IntBin>();
	
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		// Initialization and Usage Checking
		
		if (args.length < 2) {
			usage();
		}
		try {
			binSize = Integer.parseInt(args[0]);
			for (int x = 1; x < args.length; x++) {
				int currentItem = Integer.parseInt(args[x]);
				itemList.add(currentItem);
			}
		} catch (NumberFormatException e) {
			System.out.println("Input values must be integers!");
			usage();
		} catch (Exception e) {
			System.out.println(e);
			usage();
		}
		
		
		// Outputs the conditions we are running in

		System.out.println("First-Fit Algorithm");
		System.out.println("Bin size: " + binSize);
		System.out.print("Items:");
		for (int x = 0; x < itemList.size(); x++) {
			System.out.print(" " + itemList.get(x));
		}
		System.out.println();
		
		for (int x = 0; x < itemList.size(); x++) {
			boolean doesItFit = false; // does this item fit in any existing bins?
			Integer thisItem = itemList.get(x);
			for (int y = 0; y < bins.size(); y++) {
				IntBin thisBin = bins.get(y);
				if (thisBin.doesItFit(thisItem)) {
					doesItFit = true;
					thisBin.insertItem(thisItem);
					break; // breaks out of the bin loop, since we found a bin to insert it in
				}
			}
			if (! doesItFit) { // didn't fit, initialize a bin for it
				IntBin bin = new IntBin(binSize);
				bins.add(bin);
				bin.insertItem(itemList.get(x));
			}
		}


		
		// print out contents of bins
		for (int x = 0; x < bins.size(); x++) {
			System.out.println(bins.get(x).getContentsString());
		}
		
		System.out.println("Number of bins needed: " + bins.size());

		long endTime = System.currentTimeMillis();
		long timeTaken = endTime - startTime;
		System.out.println("\n" + timeTaken + " msecs");
		
	}
	
	
	
	public static void usage() {
		System.out.println("Usage: FirstFitSeq int:bin_size int:item_1 ... int:item_n");
		System.exit(0);
	}
	
}