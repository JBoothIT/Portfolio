public class SortDriver 
{
	public static void main(String args[]) 
	{
		int data[] = new int[16];

		for (int i = 0; i < data.length; i++) 
		{
			data[i] = (int) (Math.random() * 100);
		}

		Sort s = new Sort(data);
		s.start();
		try
		{
			s.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		for (int i = 0; i < data.length; i++) 
		{
			System.out.println(data[i]);
		}
	}
}
