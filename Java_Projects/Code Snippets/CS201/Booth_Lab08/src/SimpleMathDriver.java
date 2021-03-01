public class SimpleMathDriver 
{
	public static void main(String args[]) 
	{
		for (int i = 0; i < 5; i++) 
		{
			new Thread(new DoMath(i)).start();
		}

		ThreadGroup tg = Thread.currentThread().getThreadGroup();
		int tc;

		if ((tc = tg.activeCount()) > 1) 
		{
			Thread t[] = new Thread[tc];

			tc = tg.enumerate(t, false);

			for (int i = 0; i < tc; i++) 
			{
				if (!t[i].equals(Thread.currentThread())) 
				{
					try 
					{
						t[i].join();
					}
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					 }
				}
			}
		}
	}
}