public class MonthlyFeeDriver 
{
	public static void main(String args[]) throws Throwable 
	{
		Student students[] = {
				new Student("Bob", new Address("1343 14th Ave S Apt 11",
						"Birmingham", "AL", 35205), new Account(70.0)),
				new Student("Sam", new Address("1914 13th Avenue South",
						"Birmingham", "AL", 35205), new Account(20.0)),
				new Student("Tom", new Address("2101 6th Ave N Apt 16",
						"Birmingham", "AL", 35203), null) 
							};
		try
		{
			MonthlyFeeManagement.processMonthlyFee(students);
		}
		catch(Exception e)
		{
			System.out.println(" failed");
			System.out.println(" due to " + e.getMessage());
		}
	}
}
