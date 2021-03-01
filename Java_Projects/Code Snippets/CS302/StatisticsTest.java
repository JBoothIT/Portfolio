public class StatisticsTest 
{
	static public void main(String args[]) 
	{
		Short myShorts[] = new Short[5];
		Integer myInts[] = new Integer[10];
		Long myLongs[] = new Long[15];

		RandomInit.randInit(myShorts);
		RandomInit.randInit(myInts);
		RandomInit.randInit(myLongs);

		System.out.println("average of shorts: "
				+ StatisticsLibrary.average(myShorts));
		System.out.println("average of ints: "
				+ StatisticsLibrary.average(myInts));
		System.out.println("average of longs: "
				+ StatisticsLibrary.average(myLongs));
	}
}
