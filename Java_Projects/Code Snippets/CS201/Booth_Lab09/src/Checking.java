public class Checking extends Account
{
	private double interest = 0.03;
	private double balance;
	public Checking(int account, String n, double bal)
	{
		super(account, n);
		this.balance = bal;
	}
	public double balance() 
	{
		return balance;
	}
	public double interest() 
	{
		return interest;
	}

}
