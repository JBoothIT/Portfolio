public class NestedTryCatch 
{
	private static java.util.Random rand;

	static 
	{
		rand = new java.util.Random(new java.util.Date().getTime());
	}

	public static int random() 
	{
		return rand.nextInt();
	}

	public static void method1() throws Exception1
	{
		if (random() % 3 == 0) 
		{
			throw new Exception1();
		}
	}

	public static void method2() throws Exception1, Exception2 
	{
		if (random() % 3 == 0)
		{
			throw new Exception2();
		}
		else 
		{
			method1();
		}
	}

	public static void method3() throws Exception1, Exception2, Exception3 
	{
		if (random() % 3 == 0) 
		{
			throw new Exception3();
		}
		else 
		{
			method2();
		}
	}

	public static void main(String args[])
	{
		try
		{
			method1();
			try
			{
				method2();
				try
				{
					method3();
				}
				catch (Exception3 e3) 
				{
					e3.printStackTrace();
				}	
			}
					catch (Exception2 e2) 
					{
						e2.printStackTrace();
					}
			System.out.println("no exception...!");
		}
		catch (Exception1 e1) 
		{
			e1.printStackTrace();
		}
	}
}
