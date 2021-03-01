package Lab02;

public class RandomGrid 
{
	private static int[] id;
	@SuppressWarnings("unused")
	private static int count;
	public RandomGrid(int N)
	{
		id = new int[N];
		for(int i = 0; i < N; i++)
		{
			id[i] = i;
		}
	}
	public /*static*/ void generate(int N, int r)
	{
		int p = 0;
		int q = 0;
		for(int i = 0; i < N; i++)
		{
			if(id[i] < N)
			{
				p = id[i];
				q = p + r;
			}
			if(q >= N)
			{
				return;
			}
			System.out.println(p + ", " + q);
		}
	}
	public static void main(String[] args)
	{
		int N = Integer.parseInt(args[0]);
		RandomGrid a = new RandomGrid(N*N);
		System.out.println("Size of grid: " + N*N);
		a.generate(N*N, N);
	}
}