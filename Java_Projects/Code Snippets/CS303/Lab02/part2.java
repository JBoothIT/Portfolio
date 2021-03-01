package Lab02;

public class part2 
{
	public static void print(int N)
	{
		for(int n = N; n> 0; n/=2)
		{
			for(int i = 0; i < n; i++)
			{
				System.out.println(i);
			}
		}
	}
	public static void main(String[] args)
	{
		int N = Integer.parseInt(args[0]);
		print(N);
		Integer a1 = 100;
		Integer a2 = 100;
		System.out.println(a1 == a2);
		
		Integer b1 = new Integer(100);
		Integer b2 = new Integer(100);
		System.out.println(b1 == b2);
	/*	int count = ;
		for(int i = 0; i < N; i++)
		{
			int 
			for(int n = N; n > 0; n/=2)
			{
				if()
				
			}
		}*/
	}
}
