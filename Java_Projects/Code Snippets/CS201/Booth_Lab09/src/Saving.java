public class Saving extends Account
{
	private double minBalance = 150;
	private double interest = 0.05;
	private double balance;
	public Saving(int account, String n, int bal)
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
