package Lab03;
import Libs.*;

public class QuickSort 
{
	public static int M = 0;
    @SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a)
    {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }
    @SuppressWarnings("rawtypes")
    private static void sort(Comparable[] a, int lo, int hi) { 
        if (hi <= lo + M)
        {
        	insertion(a);
        	return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
        assert isSorted(a, lo, hi);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void insertion(Comparable[] a)
	{
    	for(int i = 1; i < a.length; i++)
    	{
    		for(int j = i; j > 0; j--)
    		{
    			if(a[j-1].compareTo(a[j]) > 0)
    			{
    				exch(a, j-1, j);
    			}
    			else break;
    		}
    	}
		
	}
    @SuppressWarnings("rawtypes")
	private static int partition(Comparable[] a, int lo, int hi)
{
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) 
        { 
            while (less(a[++i], v))
                if (i == hi) break;
            while (less(v, a[--j]))
                if (j == lo) break;
            if (i >= j) break;

            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static boolean less(Comparable v, Comparable w) 
    {
        return (v.compareTo(w) < 0);
    }
    @SuppressWarnings("rawtypes")
	private static void exch(Comparable[] a, int i, int j)
    {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    @SuppressWarnings("rawtypes")
	private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    @SuppressWarnings("deprecation")
	public static void main(String[] args) 
    {
        M = Integer.parseInt(args[0]);
        String[] a = StdIn.readStrings();
        QuickSort.sort(a);
        for(int i = 0; i < a.length; i++)
        {
        	System.out.println(a[i]);
        }
    }
}

