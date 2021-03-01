import java.util.Date;
import java.util.Random;

public class RandomInit 
{
	static public void randInit(Short array[]) 
	{
		if (array != null && array.length != 0) 
		{
			Random random = new Random();
			random.setSeed(new Date().getTime());

			for (int i = 0; i < array.length; i++) 
			{
				array[i] = new Short((short) random.nextInt());
			}
		}
	}

	static public void randInit(Integer array[]) 
	{
		if (array != null && array.length != 0) 
		{
			Random random = new Random();
			random.setSeed(new Date().getTime());

			for (int i = 0; i < array.length; i++) 
			{
				array[i] = new Integer((int) random.nextInt());
			}
		}
	}

	static public void randInit(Long array[]) 
	{
		if (array != null && array.length != 0) 
		{
			Random random = new Random(new Date().getTime());

			for (int i = 0; i < array.length; i++) {
				array[i] = new Long((long) random.nextInt());
			}
		}
	}
}
