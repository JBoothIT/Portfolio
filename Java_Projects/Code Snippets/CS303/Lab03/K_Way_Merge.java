package Lab03;

import java.util.PriorityQueue;

public class K_Way_Merge 
{
	@SuppressWarnings("rawtypes")
	private static PriorityQueue<Comparable> queue = new PriorityQueue<Comparable>();
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a)
	{
		sort(a,0,a.length-1);   // 
	}
	@SuppressWarnings("rawtypes")
	private static void sort(Comparable[] a, int lo, int hi)
	{
		if(hi <= lo)
		{
			return;
		}
		int mid = lo + (hi - lo)/2;
		sort(a, lo, mid);
		sort(a, mid+1, hi);
		merge(a,lo,mid, hi);
	}
	@SuppressWarnings("rawtypes")
	private static void merge(Comparable[] a, int lo, int mid, int hi)
	{
		for(int k = lo; k <= hi; k++)
		{
			queue.add(a[k]);
		}
		for(int k = lo; k <= hi; k++)
		{
			a[k] = queue.poll();
		}
	}
	@SuppressWarnings("rawtypes")
	public static void main(String[] args)
	{
		Comparable[] a = {1, 4, 5, 7, 3, 9, 0, 8, 2, 6, 100, 101, 132, 127, 110, 94, 29, 35, 1000};
		sort(a);
		for(int i = 0; i < a.length; i++)
		{
			System.out.println(a[i]);
		}
	}
}