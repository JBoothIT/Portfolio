public class Account 
{
	private double balance;

	public Account(double balance)
	{
		this.balance = balance;
	}

	public double getBalance()
	{
		return balance;
	}

	public boolean deductAmount(double amount)
	{
		if (balance >= amount)
		{
			balance -= amount;
			return true;
		}
		else 
		{
			return false;
		}
	}

	public String toString() 
	{
		return "Account(balance: " + balance + ")";
	}
}
