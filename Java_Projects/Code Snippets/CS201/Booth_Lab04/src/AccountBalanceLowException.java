public class AccountBalanceLowException extends Exception
{
	private double balance;
	public AccountBalanceLowException(double account)
	{
		balance = account;
	}
	public String getMessage()
	{
		return "AccountBalanceLowException: low account balance " + balance;
	}
}
