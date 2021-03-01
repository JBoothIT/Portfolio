public class MonthlyFeeManagement 
{
	private static final double monthlyFee = 25.0;

	private static boolean processMonthlyFee(Account account) throws Throwable 
	{
		if(account != null )
		{
			if(account.getBalance() <= monthlyFee)
			{
				throw new AccountBalanceLowException(account.getBalance());
			}
			account.deductAmount(monthlyFee);
			return true;
		}
		else
			throw new InvalidAccountException(account);
	}

	public static void processMonthlyFee(Student students[]) throws Throwable
	{

		for (int i = 0; i < students.length; i++) 
		{
			System.out.println("Processing monthly fee for " + students[i]);

			if (processMonthlyFee(students[i].getAccount()))
			{
				System.out.println(" succeeded");
			} 
		}
	}
}
