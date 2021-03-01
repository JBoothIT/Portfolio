public class StatisticsLibrary
{
	/*Added <T extends Number> to one of the methods, set T as the type for array[] and deleted the remaining methods*/
	static public <T extends Number> int average(T array[])
	{
		if(array == null || array.length == 0)
		{
			return 0;
		}
		else
		{
			int sum = 0; 
			for(int i = 0; i < array.length; i++)
			{
				sum += array[i].intValue();
			}
			return sum/array.length;
		}
	}
}
