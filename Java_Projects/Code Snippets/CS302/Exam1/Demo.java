public class Demo<AnyType extends Comparable<AnyType>>
{
	public static <AnyType> AnyType min(AnyType[] a)
	{
		int minIndex = 0;
		for(int i = 0; i < a.length; i++)
		{
			if(a.compareTo())
			{
				minIndex = i;
			}
			return a[minIndex];
		}
		return null;
	}
	public static void main(String[] args)
	{
		String[] str = {"Joe", "Bob", "Bill", "Zeke"};
		System.out.println(min(str));
	}
}
