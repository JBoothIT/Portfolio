package Lab07;

import java.util.Collections;
import java.util.LinkedList;

/**
 * IntBin.java
 * Represents a Bin that can contain Items (represented as Integers), used for working with the Bin-Packing problem
 * 
 * @author Stephen Martel
 */
public class IntBin implements Comparable 
{
	
	public int capacity = 0; // the capacity of the bin
	public int spaceFilled = 0; // the amount of space / weight already stored within the bin
	public int remainingSpace = 0; // the amount of space left within the bin
	public LinkedList<Integer> itemList; // a list of all the items contained within the bin
	
	public IntBin(int capacity) {
		this.capacity = capacity;
		remainingSpace = capacity;
		itemList = new LinkedList<Integer>();
		
		if (this.capacity < 0) { // negative space is not allowed
			this.capacity = 0;
		}
	}
	
	// various functions
	
	public boolean doesItFit(int weight) {
		if (remainingSpace >= weight) {
			return true;
		}
		return false;
	}
	
	public boolean insertItem(Integer item) 
	{
		if (!doesItFit(item)) 
		{ // ensures the item cannot be inserted if it would not fit
			return false;
		}

		itemList.add(item);
		remainingSpace -= item;
		spaceFilled += item;
		if (getNumberOfItemsContained() > 1) {
			sort();
		}
		
		
		// Was writing as a replacement for Collections.sort, but it seems to perform best. (internally, a sort of merge-sort?)
//		if (itemList.size() == 0) { // if it is an empty list, just add to the front
//			itemList.add(item);
//		} else { // otherwise, try to put it into a sorted position, ascending order
//			boolean inserted = false;
//			for (int x = 0; x < itemList.size(); x++) {
//				if (itemList.get(x) > item) {
//					if (x == 0) {
//						itemList.add(0, item);
//					} else {
//						itemList.add(x-1, item);
//					}
//					inserted = true;
//					break;
//				}
//			}
//			if (!inserted) {
//				itemList.add(item);
//			}
//		}
//		remainingSpace -= item;
//		spaceFilled += item;
		
		return true;
	}
	
	public void clear() {
		remainingSpace = capacity;
		spaceFilled = 0;
		itemList.clear();
	}
	
	public void sort() {
		Collections.sort(itemList);
	}
	
	// getters
	
	public boolean isFull() { // if the bin has no space remaining, it is full, returns true
		if (remainingSpace == 0) {
			return true;
		}
		return false;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public int getSpaceFilled() {
		return spaceFilled;
	}
	
	public int getRemainingSpace() {
		return remainingSpace;
	}
	
	public LinkedList<Integer> getItemList() {
		return itemList;
	}
	
	public int getNumberOfItemsContained() {
		return itemList.size();
	}
	
	public void setItemList(LinkedList<Integer> itemList) {
		this.itemList = itemList;
	}
	
	// copies this bin into another bin
	
	public void copy(IntBin other) {
		spaceFilled = other.getSpaceFilled();
		remainingSpace = other.getRemainingSpace();
		itemList = new LinkedList<Integer>();
		itemList.addAll(other.getItemList());
	}
	
	// output
	
	public String getContentsString() {
		String output = "{";
		for (int x = 0; x < itemList.size(); x++) {
			output += " " + itemList.get(x);
		}
		output += " }";
		return output;
	}

	// sorts not exactly based on logic, but on speed. if one bin is bigger, it goes first. if same, then it goes by elements
	public int compareTo(Object obj) {
		IntBin other = (IntBin)obj;
		if (itemList.size() > other.getItemList().size()) {
			return 1;
		} else if (itemList.size() < other.getItemList().size()) {
			return -1;
		} else {
			for (int x = 0; x < itemList.size(); x++) {
				int val = itemList.get(x).compareTo(other.getItemList().get(x));
				if (val != 0) {
					return val;
				}
			}
		}

		return 0; // complete tie / all identical
	}

	
	
}