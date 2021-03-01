package Lab04;

public class IS
{
	public static int interpolationSearch(ST<Integer, Integer> sortedArray, int toFind)
	{
		// Returns index of toFind in sortedArray, or -1 if not found
		int low = 0;
		int high = sortedArray.size() - 1;
		int mid;
		while (sortedArray.get(low) <= toFind && (int)sortedArray.get(high) >= toFind) 
		{
			mid = low +
					((toFind - sortedArray.get(low)) * (high - low)) /
					(sortedArray.get(high) - sortedArray.get(low));  //out of range is possible  here
 
			if (sortedArray.get(mid) < toFind)
			{
				low = mid + 1;
			}
			else if (sortedArray.get(mid) > toFind)
				// Repetition of the comparison code is forced by syntax limitations.
			{
					high = mid - 1;
			}
			else
				return mid;
		}
 
		if (sortedArray.get(low) == toFind)
		{
			return low;
		}
		else
		{
			return -1; // Not found
		}
	}
}