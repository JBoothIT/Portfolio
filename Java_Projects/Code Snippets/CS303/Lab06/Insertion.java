package Lab06;
public class Insertion 
{
	public static int[] sort(int[] a)
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
}