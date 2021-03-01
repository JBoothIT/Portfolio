package Lab04;

import java.util.Scanner;

public class SearchTester
{
	public static void STcheck(ST<Integer, Integer> a)
	{
		for(int i = 0; i < a.size(); i++)
		{
			if(a.get(i) == null)
			{
				a.delete(i);
			}
		}
	}
	public static void sort(ST<Integer, Integer> a)
	{
		for(int i = 0; i  < a.size(); i++)
		{
			for(int j = i; j > 0; j++)
			{
				int temp = a.get(j);
				a.put(j-1, a.get(j));
				a.put(j, temp);
			}
		}
	}
	public static void main(String[] args)
	{
		ST<Integer, Integer> a = new ST<Integer, Integer>();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a value for N: ");
		int N = scan.nextInt();
		while(a.size() < N)
		{
			System.out.println("Enter value: ");
			int key = (int)(Math.random()*9);
			key += 1;
			key *= N;
			a.put(key, scan.nextInt());
		}
		STcheck(a);
		sort(a);
		System.out.println("InterpolationSearch, Enter a value to search for: ");
		int b = IS.interpolationSearch(a, scan.nextInt());
		System.out.println("Value of searched for: " + b);
		System.out.println("Binary Search, Enter a value to search for: ");
		
	}
}
