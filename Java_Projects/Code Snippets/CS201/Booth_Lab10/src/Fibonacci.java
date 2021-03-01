public class Fibonacci 
{
	private static int counter = 0;
	public static long fibonacci(int n) 
	{
		if((n == 1)||(n == 2))
		{
			return 1;
		}
		else
		{
			counter++;
			return fibonacci(n - 1) + fibonacci(n - 2);
		}
	}

	public static void main(String args[]) 
	{
		for (int i = 1; i <= 20; i++) 
		{
			System.out.println("fibonacci (" + i + ") = " + fibonacci(i) + " Counter: " + counter);
		}
	}
}
