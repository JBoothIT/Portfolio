public class Sort extends Thread 
{
	private int data[];//creates int array data

	public Sort(int data[]) //constructor sets current iteration of data to the value of data.
	{
		this.data = data;
	}

	public void run() 
	{
		for (int i = 0; i < data.length; i++) 
		{
			for (int j = i + 1; j < data.length; j++) 
			{
				if (data[i] > data[j]) 
				{
					try 
					{
						Thread.sleep(data[i] - data[j]);
					}
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}

					int t = data[i];
					data[i] = data[j];
					data[j] = t;
				}
			}
		}
	}
}
