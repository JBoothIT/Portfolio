package Lab01;
import java.util.Scanner;

public class Sort
{
	public static int[] createArray(int n)
	{
		int arraySize = 5;
		int[] smallArray = new int[arraySize];
		for(int i = 0; i < smallArray.length; i++)
		{
			smallArray[i] += (Math.random() * n);
		}
		return smallArray;
	}
	/*public static void selectionSort(int[] v)
	{
		long start = System.nanoTime();
		for(int i = 0; i < v.length; i++)
		{
			int min = i;
			for(int j = i + 1; j < v.length; j++)
			{
				if(v[j] < v[min])
				{
					min = j;
				}
			}
			exch(v, i, min);
		}
		long stop = System.nanoTime();
		long elapsedTime = (stop - start);
		System.out.println("Nano Time: " + (elapsedTime/1000000000.0) + " Seconds " + elapsedTime + " Nano Seconds");
	}*/
	/**Cycle Sort algorithm*/
	/*public static void cycle(int[] v)
	{
		long start = System.nanoTime();
		for(int i = 0; i < v.length; i++)
		{
			sort(v, i);
		}
		long stop = System.nanoTime();
		long elapsedTime = (stop - start);
		System.out.println("Nano Time: " + (elapsedTime/1000000000.0) + " Seconds " + elapsedTime + " Nano Seconds");
	}
	public static void sort(int[] a, int i)
	{
		for(int j = 0; j < a.length; j++)
		{
			if(a[j] > a[i])
			{
				exch(a, i, j);
			}
		}
	}*/
/*	public static void insertion(int[] a)
	{
		for(int i = 1; i < a.length; i++)
		{
			for(int j = i - 1; j > -1; j--)
			{
				if(a[j] > a[j + 1])
				{
					exch(a, j+1, j);
				} 
				else 
				{
					break;
				}	
			}
		}
	}*/
	/**This is this recursive form of the two method algorithm Cycle() and Sort()
	 * @param v - One dimensional array to be sorted
	 * @param i - Starting element of the Cycle portion, usually begins with 0
	 * @param j - Starting element of the Sort portion, usually begins with the same value as i */
	public static void cycleSortRecursive(int[] v, int i, int j)
	{
		if(i == v.length - 1)
		{
			return;
		}
		if(j == v.length)
		{
			i++;
			j = i;
		}
		if(v[i] > v[j])
		{
			exch(v, i, j);
		}
		cycleSortRecursive(v, i, ++j);
	}
	public static void exch(int[] v, int i, int j)
	{
		int t = v[i];
		v[i] = v[j];
		v[j] = t;
	}
	public static boolean sortCheck(int[] array)
	{
		for(int i = 0; i < array.length; i++)
		{
			if(less(array[i], array[i-1]))
			{
				return false;
			}
		}
		return true;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean less(Comparable a, Comparable b)
	{
		return a.compareTo(b) < 0;
	}
	public static int compareTo(int a, int b)
	{
		if(a > b)
		{
			return 1;
		}
		if(a < b)
		{
			return -1;
		}
		return 0;
	}
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		byte smallInt = scan.nextByte();
		int[] smallArray = createArray(smallInt);
		System.out.println("Unsorted: ");
		for(int i = 0; i < smallArray.length; i++)
		{
			System.out.println(smallArray[i]);
		}
/*		insertion(a);*/
	/*	selectionSort(smallArray);*/
		cycleSortRecursive(smallArray, 0, 0);
		/*cycle(a);*/
		assert sortCheck(smallArray);
		System.out.println("Sorted");
		for(int i = 0; i < smallArray.length; i++)
		{	
			System.out.println(smallArray[i]);
		}	
	}
	
}