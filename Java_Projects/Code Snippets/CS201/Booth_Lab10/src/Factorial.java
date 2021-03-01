public class Factorial 
{
	public static int counter = 0;
	public static long factorial(int n) 
	{
		if(n == 0)
		{
			return 1;
		}
		else if(n > 0)
		{
			counter++;
			return n * factorial(n - 1);
		}
		return n;
	}

	public static void main(String args[]) 
	{
		for (int i = 1; i <= 20; i++) 
		{
			System.out.println("factorial (" + i + ") = " + factorial(i) + "  Counter: " + counter);
		}
	}
}
