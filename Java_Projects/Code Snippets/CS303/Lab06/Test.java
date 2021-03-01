package Lab06;
import Libs.*;

public class Test 
{
	public static int[] InsertionSort(int[] a)
	{
		for(int i = 0; i < a.length; i++)
		{
			for(int j = i; j > 0; j--)
			{
				swap(a, j);
			}
		}
		return a;
	}
	private static void swap(int[] a, int j)
	{
		if(a[j - 1] > a[j])
		{
			int temp = a[j];
			a[j] = a[j-1];
			a[j-1] = temp;
		}
	}
	@SuppressWarnings("deprecation")
	public static void main(String[] args)
	{
		String[] a = StdIn.readStrings();
		int N = a.length;
		int W = a[0].length();
		for(int i = 0; i < N; i++)
		{
			assert a[i].length() == W;
		}
		for(int i = 0; i < 2; i++)
		{
			LSD.sort(a, W);
		}
		int[] b = new int[a.length];
		for(int i = 0; i < a.length; i++)
		{
			b[i] = Integer.parseInt(a[i]);
		}
		InsertionSort(b);
		for(int i = 0; i < b.length; i++)
		{
			System.out.println(b[i]);
		}
	}
}
