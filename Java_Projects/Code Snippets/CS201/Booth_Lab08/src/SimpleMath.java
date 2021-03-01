public class SimpleMath 
{
	public synchronized static void sqrCube(int i) 
	{
		try 
		{
			Thread.sleep(200 + ((i + 1) % 2) * 200);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		System.out.println("the number: " + i);

		try 
		{
			Thread.sleep(100 + (i % 2) * 100);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		System.out.println("its square: " + i * i);

		try 
		{
			Thread.sleep(100 + ((i + 1) % 2) * 100);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		System.out.println("its cube: " + i * i * i);
	}
}
